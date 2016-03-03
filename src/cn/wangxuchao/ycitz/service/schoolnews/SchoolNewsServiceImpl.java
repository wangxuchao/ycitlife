package cn.wangxuchao.ycitz.service.schoolnews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.wangxuchao.ycitz.dao.schoolnews.IndexNewsDao;
import cn.wangxuchao.ycitz.dao.schoolnews.SchoolNewsDao;
import cn.wangxuchao.ycitz.dao.schoolnews.SchoolNewsInfoDao;
import cn.wangxuchao.ycitz.model.schoolnews.IndexNews;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNews;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNewsInfo;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNewsJson;
import cn.wangxuchao.ycitz.util.HttpClientUtil;

@Service
public class SchoolNewsServiceImpl implements SchoolNewsService {
	private static final Log logger = LogFactory
			.getLog(SchoolNewsServiceImpl.class);
	// 学校首页链接
	private static final String indexUrl = "http://www.ycit.cn/";

	@Autowired
	private IndexNewsDao indexNewsDao;
	@Autowired
	private SchoolNewsDao schoolNewsDao;
	@Autowired
	private SchoolNewsInfoDao schoolNewsInfoDao;

	/**
	 * 获取学校官网首页新闻列表
	 * 
	 * @return String 新闻列表信息
	 */
	@Override
	public String getSchoolIndexNews() {
		try {
			String html = HttpClientUtil.httpGet(indexUrl);
			if (html.startsWith("error")) {
				logger.info("学校首页新闻获取失败");
				return html;
			}
			// 去除多余部分
			html = html
					.replaceAll(
							"<!DOCTYPE html[\\w\\W]*t=\"学校要闻\">|<div class=\"content_12\">[\\w\\W]*",
							"")
					.replaceAll("..:..:..\"><a href=\"", ">")
					.replaceAll(
							"\" target=\"_blank\"><div style=\"color:#000000\">",
							"\n").replaceAll("</?[a-zA-Z]+[^><]*>", "")
					.replaceAll("&nbsp;|\\.\\.\\.| |	", "")
					.replaceAll("&amp;smallid=", "&smallid=")
					.replaceAll("\\n(\\s*\\n)+", "\n")
					.replaceAll("(^\\s*)|(\\s*$)", "")
					.replaceAll("\\r\\n", "\n");

			String[] htmlArry = html.split("\\n");

			// 判断获取到的是否满足首页新闻列表数量要求
			if (htmlArry.length != 72) {
				logger.info("学校新闻首页新闻条数不对");
				return "error:学校新闻首页新闻条数不对";
			} else {
				int count = 1;
				for (int i = 0; i < 72; i++) {
					IndexNews indexNews = new IndexNews();
					indexNews.setNewsId(count);
					indexNews.setNewsUrl(htmlArry[i]);
					i++;
					indexNews.setNewsName(htmlArry[i]);
					i++;
					indexNews.setPubTime(htmlArry[i]);
					indexNewsDao.add(indexNews);
					count++;
				}

				return "success";
			}
		} catch (Exception e) {
			logger.error("捕捉到异常：" + e.getMessage());
			return "error:0x000000";
		}
	}

	@Override
	public String getNewsList(int smallid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sqlstring",
				"select id,Title,Author,com,AddTime,SmallID from Article where IsPass='1' and SmallID='"
						+ smallid + "' order by AddTime desc");
		try {
			String html = HttpClientUtil.httpPost("http://www.ycit.cn/s.do",
					map);
			if (html.startsWith("error")) {
				logger.info("新闻列表获取失败");
				return html;
			}
			// 去除多余部分
			html = html.replaceAll("\\{adminTab:\\{records:", "").replaceAll(
					"\\}\\}", "");

			// 判断获取到的是否满足首页新闻列表数量要求
			if (!html.startsWith("[{")) {
				return "error:0x000002";
			} else {
				Gson gson = new Gson();
				List<SchoolNewsJson> list = gson.fromJson(html,
						new TypeToken<List<SchoolNewsJson>>() {
						}.getType());
				for (SchoolNewsJson schoolNewsJson : list) {
					SchoolNews schoolNews = new SchoolNews();
					schoolNews.setId(schoolNewsJson.getId());
					schoolNews.setTitle(schoolNewsJson.getTitle());
					schoolNews.setCom(schoolNewsJson.getCom());
					schoolNews.setAuthor(schoolNewsJson.getAuthor());
					try {
						schoolNews.setAddtime(new SimpleDateFormat(
								"yyyy-MM-dd hh:mm:ss").parse(schoolNewsJson
								.getAddTime()));
					} catch (ParseException e) {
						logger.error("时间格式转换错误");
						return "error:0x000000}";
					}
					schoolNews.setSmallid(schoolNewsJson.getSmallID());
					schoolNewsDao.add(schoolNews);
				}

				return "success";
			}
		} catch (Exception e) {
			logger.error("获取新闻列表时捕捉到异常：" + e.getMessage());
			return "error:0x000000";
		}
	}

	@Override
	public String getNewsInfo(int id, int smallid) {
		try {
			String html = HttpClientUtil.httpGet(indexUrl + "ShowNews.jsp?id="
					+ id + "&smallid=" + smallid);
			if (html.startsWith("error")) {
				logger.info("获取学校新闻id:" + id + ",smallid:" + smallid + "失败");
				return html;
			}
			// 去除多余部分
			html = html
					.replaceAll(
							"<!DOCTYPE html[\\w\\W]*valign=\"top\">|<div class=\"page04_bottom_left\">[\\w\\W]*",
							"")
					.replaceAll("<p", "p_tag_temp_start<P")
					.replaceAll("</p>", "</p>p_tag_temp_end")
					.replaceAll("<P", "p_tag_temp_start<P")
					.replaceAll("</P>", "</P>p_tag_temp_end")
					.replaceAll("src=\"", ">img_tag_temp_start src=\"")
					.replaceAll("href=\"", ">a_tag_temp_start href=\"")
					.replaceAll("</?[a-zA-Z]+[^><]*>", "")
					.replaceAll("&nbsp;|\\.\\.\\.| |	", "")
					.replaceAll("\\n(\\s*\\n)+", "\n")
					.replaceAll("(^\\s*)|(\\s*$)", "")
					.replaceAll("\\r\\n", "\n")
					.replaceAll("p_tag_temp_start", "<p>")
					.replaceAll("p_tag_temp_end", "</p>")
					.replaceAll("img_tag_temp_start", "<img ")
					.replaceAll("a_tag_temp_start", "<a ")
					.replaceAll("src=\"/uploads",
							"src=\"" + indexUrl + "uploads")
					.replaceAll("src=\"/admin", "src=\"" + indexUrl + "admin")
					.replaceAll("href=\"/uploads",
							"href=\"" + indexUrl + "uploads")
					.replaceAll("href=\"/admin", "href=\"" + indexUrl + "admin");

			String[] htmlArry = html.split("\\n");

			// 判断获取是否成功
			if (htmlArry[0].startsWith("{") || htmlArry[0].startsWith("error")) {
				return "error:0x000002";
			} else {
				int count = htmlArry.length;
				SchoolNewsInfo schoolNewsInfo = new SchoolNewsInfo();
				schoolNewsInfo.setId(id);
				schoolNewsInfo.setSmallid(smallid);
				String newsinfo = "";
				for (int i = 0; i < count - 1; i++) {
					newsinfo = newsinfo + htmlArry[i];
				}
				schoolNewsInfo.setNewsinfo(newsinfo);
				schoolNewsInfo.setZeren(htmlArry[count - 1].replaceAll("责任编辑：",
						""));
				schoolNewsInfoDao.add(schoolNewsInfo);

				return "success";
			}
		} catch (Exception e) {
			logger.error("获取新闻内容时捕捉到异常：" + e.getMessage());
			return "error:0x000000";
		}
	}

	@Override
	public List<IndexNews> getIndexNewsFromDb() {
		return indexNewsDao.findAll();
	}
}
