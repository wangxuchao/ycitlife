package cn.wangxuchao.ycitz.service;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import cn.wangxuchao.ycitz.message.response.TextMessage;
import cn.wangxuchao.ycitz.util.MessageUtil;

public class CoreService {
	public static String processRequest(HttpServletRequest request) {

		String respXML = null;
		TextMessage tm = new TextMessage();

		// 解析微信服务器发送的请求
		try {
			HashMap<String, String> requestMap = MessageUtil.parseXML(request);
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
				tm.setContent("您发送的是:" + content);
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				tm.setContent("您发送的是图片消息！");
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				tm.setContent("您发送的是链接消息！");
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				tm.setContent("您发送的是地理位置消息！");
			}
			// 视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
				tm.setContent("您发送的是视频消息！");
			}
			// 小视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
				tm.setContent("您发送的是小视频消息！");
			}
			// 语音消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				tm.setContent("您发送的是语音消息！");
			}
			// 事件
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");

				// 关注事件
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					tm.setContent("欢迎关注Spring MVC！");
				}
				// 取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

				}
			}
		} catch (Exception e) {

		}

		return MessageUtil.messageToXML(tm);
	}
}
