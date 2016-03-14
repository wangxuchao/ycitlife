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
import cn.wangxuchao.ycitz.model.jwc.news.JWCIndexNews;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNews;
import cn.wangxuchao.ycitz.model.weixin.message.response.Article;
import cn.wangxuchao.ycitz.model.weixin.message.response.Music;
import cn.wangxuchao.ycitz.model.weixin.message.response.MusicMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.NewsMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.TextMessage;
import cn.wangxuchao.ycitz.service.baidumap.BaiduMapService;
import cn.wangxuchao.ycitz.service.baidumusic.BaiduMusicService;
import cn.wangxuchao.ycitz.service.chatrobot.ChatService;
import cn.wangxuchao.ycitz.service.face.FaceService;
import cn.wangxuchao.ycitz.service.jwc.news.JWCNewsService;
import cn.wangxuchao.ycitz.service.schoolnews.SchoolNewsService;
import cn.wangxuchao.ycitz.service.todayinhistory.TodayInHistoryService;
import cn.wangxuchao.ycitz.util.GetMessageUtil;
import cn.wangxuchao.ycitz.util.MessageUtil;
import cn.wangxuchao.ycitz.util.ValueUtil;

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
	@Autowired
	private ChatService chatService;
	@Autowired
	private SchoolNewsService schoolNewsService;
	@Autowired
	private JWCNewsService jwcNewsService;

	@Override
	public String process(HashMap<String, String> requestMap) {

		String respXML = null;
		// 默认返回的文本消息内容
		String respContent = "恩";
		TextMessage tm = new TextMessage();

		// 解析微信服务器发送的请求
		try {
			// 用户的openID
			String fromUserName = requestMap.get("FromUserName");
			// 公众号的原始ID
			String toUserName = requestMap.get("ToUserName");
			// 请求消息类型
			String msgType = requestMap.get("MsgType");
			// 消息创建时间
			String createTime = requestMap.get("CreateTime");

			tm.setFromUserName(toUserName);
			tm.setToUserName(fromUserName);
			tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			tm.setCreateTime(new Date().getTime());

			// 判断：对不同的消息请求回复不同的内容
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				String content = requestMap.get("Content");
				if (content.startsWith("/::")) {
					respContent = "么么哒";
				} else if (content.equals("功能")) {
					respContent = getFunction();
				} else if (content.equals("看新闻") || content.equals("1")) {
					logger.info("调用看新闻");
					respContent = "<a href=\"" + ValueUtil.PROJECT_ROOT
							+ "\">点击查看</a>";
				} else if (content.equals("历史上的今天") || content.equals("2")) {
					logger.info("调用历史上的今天");
					String url = "http://www.rijiben.com/";
					respContent = todayInHistoryService
							.getTodayInHistoryInfo(url);
				} else if (content.startsWith("歌曲") || content.equals("3")) {
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
							MusicMessage musicMessage = GetMessageUtil
									.getMusicMessage(music, fromUserName,
											toUserName);
							respXML = MessageUtil.messageToXML(musicMessage);
						}
					}
				} else if (content.equals("综合新闻") || content.equals("通知通告")
						|| content.equals("学校要闻") || content.equals("校外媒体")
						|| content.equals("高教动态")) {
					int smallid = 28;
					if (content.equals("综合新闻")) {
						smallid = 30;
					} else if (content.equals("通知通告")) {
						smallid = 35;
					} else if (content.equals("校外媒体")) {
						smallid = 27;
					} else if (content.equals("高教动态")) {
						smallid = 36;
					}
					List<SchoolNews> schoolNewsList = schoolNewsService
							.getNewsList(0, 5, smallid);
					List<Article> articleList = schoolNewsService
							.makeArticleList(schoolNewsList);
					NewsMessage newsMessage = GetMessageUtil.getNewsMessage(
							articleList, fromUserName, toUserName);
					respXML = MessageUtil.messageToXML(newsMessage);
					if (respXML == null) {
						respContent = "Sorry,系统忙！";
					}
				} else if (content.equals("教务通知")) {
					List<JWCIndexNews> jwcIndexNewsList = jwcNewsService
							.getIndexNews();
					List<Article> articleList = jwcNewsService
							.makeArticleList(jwcIndexNewsList);
					NewsMessage newsMessage = GetMessageUtil.getNewsMessage(
							articleList, fromUserName, toUserName);
					respXML = MessageUtil.messageToXML(newsMessage);
					if (respXML == null) {
						respContent = "Sorry,系统忙！";
					}
				} else if (content.equals("附近") || content.equals("4")) {
					respContent = "请输入附近+地点来搜索";
				} else if (content.startsWith("附近")) {
					// 周边搜索
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
							NewsMessage newsMessage = GetMessageUtil
									.getNewsMessage(articleList, fromUserName,
											toUserName);
							respXML = MessageUtil.messageToXML(newsMessage);
						}
					}
				} else {
					respContent = chatService.chat(fromUserName, createTime,
							content);
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
				respContent = "这是什么链接？";
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
				respContent = "这是什么视频？";
			}
			// 小视频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
				respContent = "这是什么小视频？";
			}
			// 语音消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "你在说啥？";
			}
			// 事件
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");

				// 关注事件
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "欢迎关注生活在盐工，回复“功能”来查看最新功能吧！";
				}
				// 取消关注
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {

				}// 扫描带参数二维码
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {

				} // 上报地理位置
				else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {

				} // 自定义菜单
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建菜单时的key值对应
					String eventKey = requestMap.get("EventKey");
					// 根据key值判断用户点击的按钮
					if (eventKey.equals("xxyw")) {
						List<SchoolNews> schoolNewsList = schoolNewsService
								.getNewsList(0, 5, 28);
						List<Article> articleList = schoolNewsService
								.makeArticleList(schoolNewsList);
						// 回复图文消息
						NewsMessage newsMessage = GetMessageUtil
								.getNewsMessage(articleList, fromUserName,
										toUserName);
						respXML = MessageUtil.messageToXML(newsMessage);
						if (respXML == null) {
							respContent = "Sorry,系统忙！";
						}
					} else if (eventKey.equals("zhxw")) {
						List<SchoolNews> schoolNewsList = schoolNewsService
								.getNewsList(0, 5, 30);
						List<Article> articleList = schoolNewsService
								.makeArticleList(schoolNewsList);
						// 回复图文消息
						NewsMessage newsMessage = GetMessageUtil
								.getNewsMessage(articleList, fromUserName,
										toUserName);
						respXML = MessageUtil.messageToXML(newsMessage);
						if (respXML == null) {
							respContent = "Sorry,系统忙！";
						}
					} else if (eventKey.equals("tztg")) {
						List<SchoolNews> schoolNewsList = schoolNewsService
								.getNewsList(0, 5, 35);
						List<Article> articleList = schoolNewsService
								.makeArticleList(schoolNewsList);
						// 回复图文消息
						NewsMessage newsMessage = GetMessageUtil
								.getNewsMessage(articleList, fromUserName,
										toUserName);
						respXML = MessageUtil.messageToXML(newsMessage);
						if (respXML == null) {
							respContent = "Sorry,系统忙！";
						}
					} else if (eventKey.equals("xwmt")) {
						List<SchoolNews> schoolNewsList = schoolNewsService
								.getNewsList(0, 5, 27);
						List<Article> articleList = schoolNewsService
								.makeArticleList(schoolNewsList);
						// 回复图文消息
						NewsMessage newsMessage = GetMessageUtil
								.getNewsMessage(articleList, fromUserName,
										toUserName);
						respXML = MessageUtil.messageToXML(newsMessage);
						if (respXML == null) {
							respContent = "Sorry,系统忙！";
						}
					} else if (eventKey.equals("gjdt")) {
						List<SchoolNews> schoolNewsList = schoolNewsService
								.getNewsList(0, 5, 36);
						List<Article> articleList = schoolNewsService
								.makeArticleList(schoolNewsList);
						// 回复图文消息
						NewsMessage newsMessage = GetMessageUtil
								.getNewsMessage(articleList, fromUserName,
										toUserName);
						respXML = MessageUtil.messageToXML(newsMessage);
						if (respXML == null) {
							respContent = "Sorry,系统忙！";
						}
					} else if (eventKey.equals("jwtz")) {
						List<JWCIndexNews> jwcIndexNewsList = jwcNewsService.getIndexNews();
						List<Article> articleList = jwcNewsService
								.makeArticleList(jwcIndexNewsList);
						NewsMessage newsMessage = GetMessageUtil
								.getNewsMessage(articleList, fromUserName,
										toUserName);
						respXML = MessageUtil.messageToXML(newsMessage);
						if (respXML == null) {
							respContent = "Sorry,系统忙！";
						}
					} else if (eventKey.equals("function")) {
						respContent = getFunction();
					} else {
						respContent = "您暂时还不能使用此功能！";
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (respXML == null) {
			tm.setContent("小盐：" + respContent);
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

	/**
	 * 以实现功能说明
	 *
	 * @return
	 */
	private static String getFunction() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("功能列表：").append("\n\n");
		buffer.append("1)看新闻").append("\n");
		buffer.append("2)历史上的今天").append("\n");
		buffer.append("3)歌曲").append("\n");
		buffer.append("4)附近").append("\n");
		buffer.append("回复功能名称或前面的序号使用").append("\n\n");
		buffer.append("下一个可能会被添加的功能：").append("\n");
		buffer.append("成绩查询");
		return buffer.toString();
	}
}
