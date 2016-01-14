package cn.wangxuchao.ycitz.service;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wangxuchao.ycitz.message.response.Music;
import cn.wangxuchao.ycitz.message.response.MusicMessage;
import cn.wangxuchao.ycitz.message.response.TextMessage;
import cn.wangxuchao.ycitz.util.MessageUtil;

@Service
public class CoreServiceImpl implements CoreService {

	@Autowired
	private TodayInHistoryService todayInHistoryService;
	@Autowired
	private BaiduMusicService baiduMusicService;

	@Override
	public String process(HashMap<String, String> requestMap) {

		String respXML = null;
		// 默认返回的文本消息内容
		String respContent = "";
		TextMessage tm = new TextMessage();

		// 解析微信服务器发送的请求
		try {
			// 用户的openID
			String fromUserName = requestMap.get("FromUserName");
			// 公众号的原始ID
			String toUserName = requestMap.get("ToUserName");
			// 请求消息类型
			String msgType = requestMap.get("MsgType");

			tm.setFromUserName(toUserName);
			tm.setToUserName(fromUserName);
			tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			tm.setCreateTime(new Date().getTime());

			// 判断：对不同的消息请求回复不同的内容
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");
				if (content.equals("历史上的今天") || content.equals("2")) {
					String url = "http://www.rijiben.com/";
					respContent = todayInHistoryService
							.getTodayInHistoryInfo(url);
				} else if (content.startsWith("歌曲")) {
					// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉
					String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?",
							"");
					// 如果歌曲名称为空
					if ("".equals(keyWord)) {
						respContent = baiduMusicService.getUsageMusic();
					} else {
						String[] kwArr = keyWord.split("@");
						// 歌曲名称
						String musicTitle = kwArr[0];
						// 演唱者默认为空
						String musicAuthor = "";
						if (2 == kwArr.length) {
							musicAuthor = kwArr[1];
						}
						// 搜索音乐
						Music music = baiduMusicService.searchMusic(musicTitle,
								musicAuthor);
						// 未搜索到音乐
						if (null == music) {
							respContent = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";
						} else {
							// 音乐消息
							MusicMessage musicMessage = new MusicMessage();
							musicMessage.setToUserName(fromUserName);
							musicMessage.setFromUserName(toUserName);
							musicMessage.setCreateTime(new Date().getTime());
							musicMessage
									.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
							musicMessage.setMusic(music);
							respXML = MessageUtil.messageToXML(musicMessage);
						}
					}
				} else {
					respContent = "您发送的是:" + content;
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				respContent = "您发送的是视频消息！";
			}
			// 小视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
				respContent = "您发送的是小视频消息！";
			}
			// 语音消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "您发送的是语音消息！";
			}
			// 事件
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");

				// 关注事件
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "欢迎关注Spring MVC！";
				}
				// 取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

				}
			}
		} catch (Exception e) {

		}
		if (respXML == null) {
			tm.setContent(respContent);
			respXML = MessageUtil.messageToXML(tm);
		}

		return respXML;
	}
}
