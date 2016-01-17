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

import com.google.gson.Gson;

import cn.wangxuchao.ycitz.controller.NewsController;
import cn.wangxuchao.ycitz.dao.IndexNewsDao;
import cn.wangxuchao.ycitz.model.IndexNews;

/**
 * 新闻调用api
 * 1.学校首页新闻列表
 *   /api/getSchoolIndexNews
 * 2...
 * 
 * @author Chao
 *
 */
@Controller
@RequestMapping("/api")
public class NewsApi {
	private static final Log logger = LogFactory.getLog(NewsController.class);

	@Autowired
	private IndexNewsDao indexNewsDao;

	@RequestMapping(value = "/getSchoolIndexNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolNews() {

		List<IndexNews> indexNewsList = new ArrayList<IndexNews>();
		indexNewsList = indexNewsDao.findAll();
		
		if(indexNewsList.size()!= 24){
			logger.error("学校首页新闻查询数据库出错");
			return "error";
		}
		
		logger.info("调用学校首页新闻api");
		// 将Java对象转换成JSON字符串
		Gson gson = new Gson();
		return gson.toJson(indexNewsList);
	}
}
