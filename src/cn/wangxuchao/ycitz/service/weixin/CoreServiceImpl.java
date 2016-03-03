package cn.wangxuchao.ycitz.service.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wangxuchao.ycitz.dao.baidumap.UserLocationDao;
import cn.wangxuchao.ycitz.model.baidumap.BaiduPlace;
import cn.wangxuchao.ycitz.model.baidumap.UserLocation;
import cn.wangxuchao.ycitz.model.weixin.message.response.Article;
import cn.wangxuchao.ycitz.model.weixin.message.response.Music;
import cn.wangxuchao.ycitz.model.weixin.message.response.MusicMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.NewsMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.TextMessage;
import cn.wangxuchao.ycitz.service.baidumap.BaiduMapService;
import cn.wangxuchao.ycitz.service.baidumusic.BaiduMusicService;
import cn.wangxuchao.ycitz.service.face.FaceService;
import cn.wangxuchao.ycitz.service.todayinhistory.TodayInHistoryService;
import cn.wangxuchao.ycitz.util.MessageUtil;

@Service
public class CoreServiceImpl implements CoreService {
	private static final Log logger = LogFactory.getLog(CoreServiceImpl.class);
	@Autowired
	private TodayInHistoryService todayInHistoryService;
	@Autowired
	private BaiduMusicService baiduMusicService;
	@Autowired
	private FaceService faceService;
	@Autowired
	private UserLocationDao userLocationDao;
	@Autowired
	private BaiduMapService baiduMapService;

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
					logger.info("调用历史上的今天");
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
						logger.info("搜索歌曲：" + musicTitle + ":" + musicAuthor);
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
				} else if (content.equals("附近")) {
					respContent = "请输入附近+地点来搜索";
				}// 周边搜索
				else if (content.startsWith("附近")) {
					String keyWord = content.replaceAll("附近", "").trim();
					logger.info("附近搜索：" + keyWord);
					// 获取用户最后一次发送的地理位置
					UserLocation userLocation = userLocationDao
							.getLastLocation(fromUserName);
					// 未获取到
					if (null == userLocation) {
						logger.info("附近搜索未搜索到：" + keyWord);
						respContent = getUsageLocation();
					} else {
						// 根据转换后（纠偏）的坐标搜索周边POI
						List<BaiduPlace> placeList = baiduMapService
								.searchPlace(keyWord,
										userLocation.getBd09Lng(),
										userLocation.getBd09Lat());
						// 未搜索到POI
						if (null == placeList || 0 == placeList.size()) {
							respContent = String.format(
									"/难过，您发送的位置附近未搜索到“%s”信息！", keyWord);
						} else {
							List<Article> articleList = baiduMapService
									.makeArticleList(placeList,
											userLocation.getBd09Lng(),
											userLocation.getBd09Lat());
							// 回复图文消息
							NewsMessage newsMessage = new NewsMessage();
							newsMessage.setToUserName(fromUserName);
							newsMessage.setFromUserName(toUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage
									.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setArticles(articleList);
							newsMessage.setArticleCount(articleList.size());
							respXML = MessageUtil.messageToXML(newsMessage);
						}
					}
				} else {
					respContent = "您发送的是:" + content;
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				// 取得图片地址
				String picUrl = requestMap.get("PicUrl");
				// 人脸检测
				respContent = faceService.detect(picUrl);
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				logger.info("获取用户发过来的位置");
				// 用户发送的经纬度
				String lng = requestMap.get("Location_Y");
				String lat = requestMap.get("Location_X");
				// 坐标转换后的经纬度
				String bd09Lng = null;
				String bd09Lat = null;
				// 调用接口转换坐标
				UserLocation userLocation = baiduMapService.convertCoord(lng,
						lat);
				if (null != userLocation) {
					bd09Lng = userLocation.getBd09Lng();
					bd09Lat = userLocation.getBd09Lat();
				}
				// 保存用户地理位置
				UserLocation ul = new UserLocation();
				ul.setOpenId(fromUserName);
				ul.setLng(lng);
				ul.setLat(lat);
				ul.setBd09Lng(bd09Lng);
				ul.setBd09Lat(bd09Lat);
				userLocationDao.add(ul);

				StringBuilder buffer = new StringBuilder();
				buffer.append("[愉快]").append("成功接收您的位置！").append("\n\n");
				buffer.append("您可以输入搜索关键词获取周边信息了，例如：").append("\n");
				buffer.append("        附近ATM").append("\n");
				buffer.append("        附近KTV").append("\n");
				buffer.append("        附近厕所").append("\n");
				buffer.append("必须以“附近”两个字开头！");
				respContent = buffer.toString();
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

	/**
	 * 周边搜索使用说明
	 *
	 * @return
	 */
	private static String getUsageLocation() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("周边搜索使用说明").append("\n\n");
		buffer.append("1）发送地理位置").append("\n");
		buffer.append("点击窗口底部的“+”按钮，选择“位置”，点“发送”").append("\n\n");
		buffer.append("2）指定关键词搜索").append("\n");
		buffer.append("格式：附近+关键词\n例如：附近ATM、附近KTV、附近厕所");
		return buffer.toString();
	}
}
