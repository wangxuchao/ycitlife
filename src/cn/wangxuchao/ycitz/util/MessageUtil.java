package cn.wangxuchao.ycitz.util;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.wangxuchao.ycitz.message.response.Article;
import cn.wangxuchao.ycitz.message.response.ImageMessage;
import cn.wangxuchao.ycitz.message.response.MusicMessage;
import cn.wangxuchao.ycitz.message.response.NewsMessage;
import cn.wangxuchao.ycitz.message.response.TextMessage;
import cn.wangxuchao.ycitz.message.response.VideoMessage;
import cn.wangxuchao.ycitz.message.response.VoiceMessage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtil {
	// 请求消息
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	// 响应消息
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	// 事件响应
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	// 将请求中的xml转成map
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

	private static void recursiveParseXML(Element root,
			HashMap<String, String> map) {
		// 得到根节点的子节点列表
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
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

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
