package cn.wangxuchao.ycitz.service.indexnews;

import java.util.List;

import cn.wangxuchao.ycitz.model.indexnews.IndexNews;

public interface IndexNewsService {
	/**
	 * 获取学校官网首页新闻列表
	 * 
	 * @return String 新闻列表信息
	 */
	String getSchoolIndexNews();
	
	List<IndexNews> getIndexNews();
	
	void doIndexNewsListTask();
}
