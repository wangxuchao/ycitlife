package cn.wangxuchao.ycitz.controller.schoolnews;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wangxuchao.ycitz.model.schoolnews.IndexNews;
import cn.wangxuchao.ycitz.service.schoolnews.SchoolNewsService;

@Controller
public class SchoolNewsController {
	private static final Log logger = LogFactory
			.getLog(SchoolNewsController.class);

	@Autowired
	private SchoolNewsService schoolNewsService;
	
	/**
	 * 访问校园新闻主页
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getRoute() {
		logger.info("访问主页");
		List<IndexNews> indexNewsList = schoolNewsService.getIndexNewsFromDb();
		return new ModelAndView("schoolnews/main", "indexNewsList", indexNewsList);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSchoolIndexNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolIndexNews() {
		logger.info("调用getSchoolIndexNews");
		return schoolNewsService.getSchoolIndexNews();
	}

	@RequestMapping(value = "/getSchoolNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolNews(@RequestParam int smallid) {
		logger.info("调用getSchoolNews");
		return schoolNewsService.getNewsList(smallid);
	}

	@RequestMapping(value = "/getSchoolNewsInfo", method = RequestMethod.GET)
	public @ResponseBody String getSchoolNewsInfo(@RequestParam int id,
			@RequestParam int smallid) {
		logger.info("调用getSchoolNewsInfo");
		return schoolNewsService.getNewsInfo(id, smallid);
	}
}
