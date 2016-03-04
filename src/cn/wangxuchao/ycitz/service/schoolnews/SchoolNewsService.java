package cn.wangxuchao.ycitz.service.schoolnews;

import cn.wangxuchao.ycitz.model.schoolnews.SchoolNewsDetail;

public interface SchoolNewsService {

	String getNewsList(int smallid);

	String getNewsInfo(int id, int smallid);

	SchoolNewsDetail getSchoolNewsDetail(int id, int smallid);

	// 定时抓取学校新闻列表
	void doNewsListTask();
}