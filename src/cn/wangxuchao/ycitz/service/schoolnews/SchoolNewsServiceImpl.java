package cn.wangxuchao.ycitz.service.schoolnews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.wangxuchao.ycitz.dao.schoolnews.SchoolNewsDao;
import cn.wangxuchao.ycitz.dao.schoolnews.SchoolNewsInfoDao;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNews;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNewsDetail;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNewsInfo;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNewsJson;
import cn.wangxuchao.ycitz.model.weixin.message.response.Article;
import cn.wangxuchao.ycitz.util.HttpClientUtil;
import cn.wangxuchao.ycitz.util.ValueUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
@Service
public class SchoolNewsServiceImpl implements SchoolNewsService {
	private static final Log logger = LogFactory
			.getLog(SchoolNewsServiceImpl.class);

	@Autowired
	private SchoolNewsDao schoolNewsDao;
	@Autowired
	private SchoolNewsInfoDao schoolNewsInfoDao;

	@Override
	public String getNewsList(int smallid) {
		String url = ValueUtil.YCIT_HOME_PAGE;

		Map<String, String> map = new HashMap<String, String>();
		map.put("sqlstring",
				"select id,Title,Author,com,AddTime,SmallID from Article where IsPass='1' and SmallID='"
						+ smallid + "' order by AddTime desc");
		try {
			String html = HttpClientUtil.httpPost(url + "s.do", map);
			if (html.startsWith("error")) {
				url = ValueUtil.YCIT_HOME_PAGE_BACKUP;
				html = HttpClientUtil.httpPost(url + "s.do", map);
				if (html.startsWith("error")) {
					logger.info("新闻列表获取失败");
					return html;
				}
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
						return "error:0x000000";
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
		String url = ValueUtil.YCIT_HOME_PAGE;
		try {
			String html = HttpClientUtil.httpGet(url + "ShowNews.jsp?id=" + id
					+ "&smallid=" + smallid);
			if (html.startsWith("error")) {
				url = ValueUtil.YCIT_HOME_PAGE_BACKUP;
				html = HttpClientUtil.httpGet(url + "ShowNews.jsp?id=" + id
						+ "&smallid=" + smallid);
				if (html.startsWith("error")) {
					logger.info("获取学校新闻id:" + id + ",smallid:" + smallid + "失败");
					return html;
				}
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
					.replaceAll("</a>", "</a>a_tag_temp_end")
					.replaceAll("</A>", "</A>a_tag_temp_end")
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
					.replaceAll("a_tag_temp_end", "</a> ")
					.replaceAll("src=\"/uploads",
							"src=\"" + ValueUtil.YCIT_HOME_PAGE + "uploads")
					.replaceAll("src=\"/admin",
							"src=\"" + ValueUtil.YCIT_HOME_PAGE + "admin")
					.replaceAll("href=\"/uploads",
							"href=\"" + ValueUtil.YCIT_HOME_PAGE + "uploads")
					.replaceAll("href=\"/admin",
							"href=\"" + ValueUtil.YCIT_HOME_PAGE + "admin")
					.replaceAll(
							"<img src=\"http://www.ycit.cn/admin/sysimage",
							"<img style=\"width:auto;margin-left:auto;\" src=\"http://www.ycit.cn/admin/sysimage");

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
	public SchoolNewsDetail getSchoolNewsDetail(int id, int smallid) {
		SchoolNews schoolNews = schoolNewsDao.findById(Long.valueOf(id));

		if (schoolNews == null) {
			getNewsList(smallid);
			schoolNews = schoolNewsDao.findById(id);
		}

		SchoolNewsInfo schoolNewsInfo = schoolNewsInfoDao.findById(id);
		if (schoolNewsInfo == null) {
			getNewsInfo(id, schoolNews.getSmallid());
			schoolNewsInfo = schoolNewsInfoDao.findById(Long.valueOf(id));
		}

		// 组装SchoolNewsDetail对象
		SchoolNewsDetail schoolNewsDetail = new SchoolNewsDetail();
		schoolNewsDetail.setSchoolNews(schoolNews);
		schoolNewsDetail.setSchoolNewsInfo(schoolNewsInfo);

		return schoolNewsDetail;
	}

	/**
	 * 6点到18点，每隔半个小时获取一次新闻列表
	 */
	@Override
	@Scheduled(cron = "10 0/50 6-18 * * ?")
	public void doNewsListTask() {
		logger.info("获取新闻列表smallid为28，名称为学校要闻的新闻列表。");
		getNewsList(28);

		logger.info("获取新闻列表smallid为30，名称为综合新闻的新闻列表。");
		getNewsList(30);

		logger.info("获取新闻列表smallid为35，名称为通知通告的新闻列表。");
		getNewsList(35);

		logger.info("获取新闻列表smallid为27，名称为校外媒体的新闻列表。");
		getNewsList(27);

		logger.info("获取新闻列表smallid为36，名称为高教动态的新闻列表。");
		getNewsList(36);
	}

	@Override
	public List<SchoolNews> getNewsList(int start, int max, int smallid) {
		return schoolNewsDao.getNewsList(start, max, smallid);
	}

	@Override
	public List<Article> makeArticleList(List<SchoolNews> schoolNewsList) {
		List<Article> articleList = new ArrayList<Article>();
		SimpleDateFormat newsDateFormat = new SimpleDateFormat("MM.dd");
		int count = 0;
		for (SchoolNews schoolNews : schoolNewsList) {
			Article article = new Article();
			article.setTitle(newsDateFormat.format(schoolNews.getAddtime())
					+ " " + schoolNews.getTitle());
			article.setUrl(ValueUtil.PROJECT_ROOT + "news?id="
					+ schoolNews.getId() + "&smallid="
					+ schoolNews.getSmallid());
			// 将首条图文的图片设置为大图
			if (count == 0) {
				article.setPicUrl(ValueUtil.PROJECT_ROOT
						+ "images/schoolnews.jpg");
			} else {
				article.setPicUrl(ValueUtil.PROJECT_ROOT + "images/next.jpg");
			}
			articleList.add(article);
			count++;
		}
		Article more = new Article();
		more.setTitle("查看更多");
		more.setUrl(ValueUtil.PROJECT_ROOT + "list?smallid="
				+ schoolNewsList.get(0).getSmallid());
		more.setPicUrl(ValueUtil.PROJECT_ROOT + "images/more.jpg");
		articleList.add(more);
		return articleList;
	}

}
