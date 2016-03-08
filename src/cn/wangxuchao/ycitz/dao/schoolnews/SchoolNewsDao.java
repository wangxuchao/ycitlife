package cn.wangxuchao.ycitz.dao.schoolnews;

import java.util.List;

import cn.wangxuchao.ycitz.dao.BaseDao;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNews;

public interface SchoolNewsDao extends BaseDao<SchoolNews> {
	List<SchoolNews> getNewsList(int start, int max, int smallid);
}
