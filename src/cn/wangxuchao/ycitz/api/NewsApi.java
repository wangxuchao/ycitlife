package cn.wangxuchao.ycitz.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wangxuchao.ycitz.dao.indexnews.IndexNewsDao;
import cn.wangxuchao.ycitz.dao.schoolnews.SchoolNewsDao;
import cn.wangxuchao.ycitz.dao.schoolnews.SchoolNewsInfoDao;
import cn.wangxuchao.ycitz.model.indexnews.IndexNews;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNews;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNewsInfo;

import com.google.gson.Gson;

/**
 * 新闻调用api
 * 
 * 1.学校首页新闻列表 /api/getSchoolIndexNews
 * 
 * 2.学校新闻列表 /api/getSchoolNews
 * 
 * 3.学校新闻内容 /api/getSchoolNewsInfo
 * 
 * @author Chao
 *
 */
@Controller
@RequestMapping("/api")
public class NewsApi {
	private static final Log logger = LogFactory.getLog(NewsApi.class);

	@Autowired
	private IndexNewsDao indexNewsDao;
	@Autowired
	private SchoolNewsDao schoolNewsDao;
	@Autowired
	private SchoolNewsInfoDao schoolNewsInfoDao;

	@RequestMapping(value = "/getSchoolIndexNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolIndexNews() {

		List<IndexNews> indexNewsList = new ArrayList<IndexNews>();
		indexNewsList = indexNewsDao.findAll();

		if (indexNewsList.size() != 24) {
			logger.error("学校首页新闻查询数据库出错");
			return "error";
		}

		logger.info("调用学校首页新闻api");
		// 将Java对象转换成JSON字符串
		Gson gson = new Gson();
		return gson.toJson(indexNewsList);
	}

	@RequestMapping(value = "/getSchoolNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolNews() {

		List<SchoolNews> schoolNewsList = new ArrayList<SchoolNews>();
		schoolNewsList = schoolNewsDao.findAll();

		logger.info("调用学校新闻列表api");
		// 将Java对象转换成JSON字符串
		Gson gson = new Gson();
		return gson.toJson(schoolNewsList);
	}

	@RequestMapping(value = "/getSchoolNewsInfo", method = RequestMethod.GET)
	public @ResponseBody String getSchoolNewsInfo() {

		List<SchoolNewsInfo> schoolNewsInfoList = new ArrayList<SchoolNewsInfo>();
		schoolNewsInfoList = schoolNewsInfoDao.findAll();

		logger.info("调用学校新闻详情api");
		// 将Java对象转换成JSON字符串
		Gson gson = new Gson();
		return gson.toJson(schoolNewsInfoList);
	}
}
