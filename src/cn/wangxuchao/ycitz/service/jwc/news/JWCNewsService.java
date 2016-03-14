package cn.wangxuchao.ycitz.service.jwc.news;

import java.util.List;

import cn.wangxuchao.ycitz.model.jwc.news.JWCIndexNews;
import cn.wangxuchao.ycitz.model.jwc.news.JWCNewsInfo;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNews;
import cn.wangxuchao.ycitz.model.weixin.message.response.Article;

public interface JWCNewsService {
	/**
	 * 获取教务处首页新闻
	 * 
	 * @return
	 */
	String getNewsList();

	String getNewsInfo(int newsid, int classid);

	JWCNewsInfo getJWCNewsInfo(int newsid, int classid);

	List<JWCIndexNews> getIndexNews();

	void doIndexNewsListTask();

	/**
	 * 封装多图文消息
	 * 
	 * @param jwcIndexNews
	 * @return
	 */
	List<Article> makeArticleList(List<JWCIndexNews> jwcIndexNews);
}
