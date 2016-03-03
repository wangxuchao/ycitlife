package cn.wangxuchao.ycitz.service.schoolnews;

import java.util.List;

import cn.wangxuchao.ycitz.model.schoolnews.IndexNews;

public interface SchoolNewsService {
	/**
	 * 获取学校官网首页新闻列表
	 * 
	 * @return String 新闻列表信息
	 */
	String getSchoolIndexNews();

	String getNewsList(int smallid);

	String getNewsInfo(int id, int smallid);
	
	List<IndexNews> getIndexNewsFromDb();
}
