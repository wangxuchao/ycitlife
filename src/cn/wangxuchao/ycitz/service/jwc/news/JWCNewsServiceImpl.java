package cn.wangxuchao.ycitz.service.jwc.news;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.wangxuchao.ycitz.dao.jwc.news.JWCIndexNewsDao;
import cn.wangxuchao.ycitz.dao.jwc.news.JWCNewsInfoDao;
import cn.wangxuchao.ycitz.model.indexnews.IndexNews;
import cn.wangxuchao.ycitz.model.jwc.news.JWCIndexNews;
import cn.wangxuchao.ycitz.model.jwc.news.JWCNewsInfo;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNews;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNewsInfo;
import cn.wangxuchao.ycitz.model.weixin.message.response.Article;
import cn.wangxuchao.ycitz.util.ChangeCharsetUtil;
import cn.wangxuchao.ycitz.util.HttpClientUtil;
import cn.wangxuchao.ycitz.util.ValueUtil;

@Service
public class JWCNewsServiceImpl implements JWCNewsService {
	private static final Log logger = LogFactory
			.getLog(JWCNewsServiceImpl.class);

	@Autowired
	private JWCIndexNewsDao jwcIndexNewsDao;
	@Autowired
	private JWCNewsInfoDao jwcNewsInfoDao;

	@Override
	public String getNewsList() {
		String url = ValueUtil.YCIT_JWC_HOME_PAGE;
		try {
			String html = HttpClientUtil.httpGet(url, ChangeCharsetUtil.GBK);
			if (html.startsWith("error")) {
				logger.error("获取教务处首页新闻列表失败");
				return html;
			}
			html = html
					.replaceAll(
							"<!DOCTYPE[\\w\\W]*class=\"inform_bottom\">|<div class=\"banner_tu\">[\\w\\W]*",
							"")
					.replaceAll("<td width=\"85%\"><a href=\"", "\n")
					.replaceAll("\"  target=\"_blank\">", "\n")
					.replaceAll("</?[a-zA-Z]+[^><]*>", "")
					.replaceAll("&nbsp;|\\.\\.\\.| |	", "")
					.replaceAll("\\n(\\s*\\n)+", "\n")
					.replaceAll("(^\\s*)|(\\s*$)", "").replaceAll("&amp;", "&");

			String[] htmlArry = html.split("\\n");

			// 判断获取到的是否满足首页新闻列表数量要求
			if (htmlArry.length != 24) {
				logger.info("学校教务处新闻首页新闻条数不对");
				return "error:学校教务处新闻首页新闻条数不对";
			} else {
				int count = 1;
				for (int i = 0; i < 24; i++) {
					JWCIndexNews jwcIndexNews = new JWCIndexNews();
					jwcIndexNews.setNewsId(count);
					jwcIndexNews.setNewsUrl(htmlArry[i]);
					i++;
					jwcIndexNews.setNewsName(htmlArry[i]);
					i++;
					jwcIndexNews.setPubTime(htmlArry[i]);
					jwcIndexNewsDao.add(jwcIndexNews);
					count++;
				}

				return "success";
			}
		} catch (Exception e) {
			logger.error("获取教务处首页新闻列表失败");
			return "error:0x000000";
		}

	}

	@Override
	public List<JWCIndexNews> getIndexNews() {
		List<JWCIndexNews> jwcIndexNewsList = jwcIndexNewsDao.findAll();
		// 如果取到的数据条数不对，重新从学校官网抓取
		if (jwcIndexNewsList.size() != 8) {
			getNewsList();
			jwcIndexNewsList = jwcIndexNewsDao.findAll();
		}

		for (JWCIndexNews jwcin : jwcIndexNewsList) {
			if (!jwcin.getNewsUrl().startsWith("http://")) {
				jwcin.setNewsUrl(jwcin.getNewsUrl().replaceAll("[\\w\\W]*asp",
						ValueUtil.PROJECT_ROOT + "jwc/news"));
			}
		}
		return jwcIndexNewsList;
	}

	@Override
	@Scheduled(cron = "10 15/40 6-18 * * ?")
	public void doIndexNewsListTask() {
		logger.info("通过task获取学校教务处首页新闻列表");
		getNewsList();
	}

	@Override
	public String getNewsInfo(int newsid, int classid) {
		String url = ValueUtil.YCIT_JWC_HOME_PAGE + "news.asp?newsid=" + newsid
				+ "&classid=" + classid;
		try {
			String html = HttpClientUtil.httpGet(url, ChangeCharsetUtil.GBK);
			if (html.startsWith("error")) {
				logger.error("获取教务处首页新闻列表失败");
				return html;
			}
			html = html
					.replaceAll(
							"<!DOCTYPE[\\w\\W]*<!--插入文章内容-->|上一新闻：[\\w\\W]*",
							"")
					.replaceAll("<img border=\"0\" src=\"", "temp")
					.replaceAll("<IMG border=0 src=\"", "temp")
					.replaceAll("<a href=\"mailto:", "email")
					.replaceAll("\\.\\./upfiles", "upfiles")
					.replaceAll(
							"<a href=\"upfiles|<A href=\"upfiles|<A target=_blank href=\"upfiles|<a target=_blank href=\"upfiles",
							"fileurl")
					.replaceAll(
							"<a href=\"|<A href=\"|<A target=_blank href=\"|<a target=_blank href=\"",
							"url")
					.replaceAll("</a>", "lianjie")
					.replaceAll("</A>", "lianjie")
					.replaceAll("</?[a-zA-Z]+[^><]*>", "")
					.replaceAll(
							"temp",
							"\n<img border=\"0\" src=\""
									+ ValueUtil.YCIT_JWC_HOME_PAGE)
					.replaceAll(
							"fileurl",
							"<a href=\"" + ValueUtil.YCIT_JWC_HOME_PAGE
									+ "upfiles")
					.replaceAll("url", "<a href=\"")

					.replaceAll("email", "<a href=\"mailto:")
					.replaceAll("lianjie", "</a>")
					.replaceAll("&nbsp;", "\n")
					.replaceAll("        ", "")
					.replaceAll("(^\\s*)|(\\s*$)", "")
					.replaceAll("\\n(\\s*\\n)+", "\n")
					.replaceAll("/adminnews/sysimage", "adminnews/sysimage")
					.replaceAll(
							" src=\"http://jwc.ycit.cn/adminnews/sysimage",
							" style=\"width:auto;margin-left:auto;\" src=\"http://jwc.ycit.cn/adminnews/sysimage")
					.replaceAll(
							" src=\"http://jwc.ycit.cn/editor/sysimage",
							" style=\"width:auto;margin-left:auto;\" src=\"http://jwc.ycit.cn/editor/sysimage");
			String[] htmlArry = html.split("\\n");

			JWCNewsInfo jwcNewsInfo = new JWCNewsInfo();
			jwcNewsInfo.setId(newsid);
			jwcNewsInfo.setClassid(classid);
			jwcNewsInfo.setTitle(htmlArry[0]);
			jwcNewsInfo.setAuthor(htmlArry[1].replaceAll("作者：", ""));
			jwcNewsInfo.setCom(htmlArry[2].replaceAll("文章来源：", ""));
			try {
				jwcNewsInfo.setAddtime(new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss").parse(htmlArry[4].replaceAll(
						"发布时间：", "")));
			} catch (ParseException e) {
				logger.error("时间格式转换错误");
				return "error:0x000000";
			}
			String newsinfo = "";
			for (int i = 5; i < htmlArry.length; i++) {
				newsinfo = newsinfo + "<p>" + htmlArry[i] + "</p>";
			}
			jwcNewsInfo.setArticle(newsinfo);
			jwcNewsInfoDao.add(jwcNewsInfo);

			return "success";
		} catch (Exception e) {
			logger.error("获取教务处首页新闻列表失败");
			return "error:0x000000";
		}
	}

	@Override
	public JWCNewsInfo getJWCNewsInfo(int newsid, int classid) {
		JWCNewsInfo jwcNewsInfo = jwcNewsInfoDao.findById(newsid);
		if (jwcNewsInfo == null) {
			getNewsInfo(newsid, classid);
			jwcNewsInfo = jwcNewsInfoDao.findById(newsid);
		}
		return jwcNewsInfo;
	}

	@Override
	public List<Article> makeArticleList(List<JWCIndexNews> jwcIndexNews) {
		List<Article> articleList = new ArrayList<Article>();
		int count = 0;
		for (JWCIndexNews j : jwcIndexNews) {
			Article article = new Article();
			article.setTitle(j.getPubTime() + " " + j.getNewsName());
			if (!j.getNewsUrl().startsWith("http://")) {
				j.setNewsUrl(j.getNewsUrl().replaceAll("[\\w\\W]*asp",
						ValueUtil.PROJECT_ROOT + "jwc/news"));
			}
			article.setUrl(j.getNewsUrl());
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
		return articleList;
	}

}
