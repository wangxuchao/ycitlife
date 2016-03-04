package cn.wangxuchao.ycitz.service.schoolnews;

import java.util.List;

import cn.wangxuchao.ycitz.model.schoolnews.SchoolNewsDetail;

public interface SchoolNewsService {
	
	String getNewsList(int smallid);

	String getNewsInfo(int id, int smallid);

	List<SchoolNewsDetail> getSchoolNewsDetail(int id, int smallid);
}
