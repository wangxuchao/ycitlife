package cn.wangxuchao.ycitz.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.wangxuchao.ycitz.model.weixin.message.response.Article;
import cn.wangxuchao.ycitz.model.weixin.message.response.ImageMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.MusicMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.NewsMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.TextMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.VideoMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.VoiceMessage;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {
	// 请求消息类型：文本
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    // 请求消息类型：图片
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    // 请求消息类型：语音
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    // 请求消息类型：视频
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
    // 请求消息类型：地理位置
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    // 请求消息类型：链接
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
 // 请求消息类型：小视频
	public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";

	// 响应消息类型：文本
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    // 响应消息类型：图片
    public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
    // 响应消息类型：语音
    public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
    // 响应消息类型：视频
    public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
    // 响应消息类型：音乐
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
    // 响应消息类型：图文
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	
	// 请求消息类型：事件推送
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    // 事件类型：subscribe(订阅)
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    // 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    // 事件类型：scan(用户已关注时的扫描带参数二维码)
    public static final String EVENT_TYPE_SCAN = "scan";
    // 事件类型：LOCATION(上报地理位置)
    public static final String EVENT_TYPE_LOCATION = "LOCATION";
    // 事件类型：CLICK(自定义菜单)
    public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 获取微信加密实例
	 * 
	 * @return
	 */
	public static WXBizMsgCrypt getWxCrypt() {
		WXBizMsgCrypt pc = null;
		try {
			pc = new WXBizMsgCrypt(ValueUtil.WEIXIN_TOKEN,
					ValueUtil.WEIXIN_ENCODINGAESKEY, ValueUtil.WEIXIN_APPID);
		} catch (AesException e) {
		}
		return pc;
	}

	/**
	 * 明文模式解析请求参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> parseXML(HttpServletRequest request)
			throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();

		// 通过IO获得Document
		SAXReader reader = new SAXReader();
		Document doc = reader.read(request.getInputStream());

		// 得到XML根节点
		Element root = doc.getRootElement();

		recursiveParseXML(root, map);

		return map;
	}

	/**
	 * 加密或兼容模式解析请求参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> parseXMLCrypt(
			HttpServletRequest request) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();

		/**
		 * 第1步：从InputStream中获取XML文本
		 */
		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// 每次读取的内容
		String line = null;
		// 最终读取的内容
		StringBuffer buffer = new StringBuffer();

		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}

		br.close();
		is.close();

		/**
		 * 第2步：解密
		 */
		String msgSignature = request.getParameter("msg_signature");
		String timeStamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		WXBizMsgCrypt wxCrypt = MessageUtil.getWxCrypt();
		String formXML = wxCrypt.decryptMsg(msgSignature, timeStamp, nonce,
				buffer.toString());

		/**
		 * 第3步：解析XML，获取请求参数
		 */
		Document doc = DocumentHelper.parseText(formXML);

		// 得到XML根节点
		Element root = doc.getRootElement();

		recursiveParseXML(root, map);

		return map;
	}

	private static void recursiveParseXML(Element root,
			HashMap<String, String> map) {
		// 得到根节点的子节点列表
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();

		// 判断有没有子元素列表
		if (elementList.size() == 0) {
			// 已经是子节点了，直接输出
			map.put(root.getName(), root.getTextTrim());
		} else {
			// 遍历
			for (Element e : elementList) {
				recursiveParseXML(e, map);
			}
		}
	}

	private static XStream xstream = new XStream(new XppDriver() {
		@Override
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点都增加CDATA标记
				boolean cdata = true;

				@Override
				public void startNode(String name, @SuppressWarnings("rawtypes") Class clazz) {
					super.startNode(name, clazz);
				}

				@Override
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	// 文本消息转xml
	public static String messageToXML(TextMessage textMessage) {
		xstream.alias("xml", TextMessage.class);
		return xstream.toXML(textMessage);
	}

	// 图片消息转xml
	public static String messageToXML(ImageMessage imageMessage) {
		xstream.alias("xml", ImageMessage.class);
		return xstream.toXML(imageMessage);
	}

	// 音乐消息转xml
	public static String messageToXML(MusicMessage musicMessage) {
		xstream.alias("xml", MusicMessage.class);
		return xstream.toXML(musicMessage);
	}

	// 图文消息转xml
	public static String messageToXML(NewsMessage newsMessage) {
		xstream.alias("xml", NewsMessage.class);
		xstream.alias("item", Article.class);
		return xstream.toXML(newsMessage);
	}

	// 视频消息转xml
	public static String messageToXML(VideoMessage videoMessage) {
		xstream.alias("xml", VideoMessage.class);
		return xstream.toXML(videoMessage);
	}

	// 语音消息转xml
	public static String messageToXML(VoiceMessage voiceMessage) {
		xstream.alias("xml", VoiceMessage.class);
		return xstream.toXML(voiceMessage);
	}
}
