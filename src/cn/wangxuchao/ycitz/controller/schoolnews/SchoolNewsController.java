package cn.wangxuchao.ycitz.controller.schoolnews;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wangxuchao.ycitz.service.schoolnews.SchoolNewsService;

@Controller
public class SchoolNewsController {
	private static final Log logger = LogFactory
			.getLog(SchoolNewsController.class);

	@Autowired
	private SchoolNewsService schoolNewsService;

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

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public ModelAndView getSchoolNewsDetail(@RequestParam int id,
			@RequestParam int smallid) {
		logger.info("获取新闻详情");
		return new ModelAndView("schoolnews/news", "schoolNewsDetail",
				schoolNewsService.getSchoolNewsDetail(id, smallid));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView getSchoolNewsList(Integer smallid) {
		if (smallid == null) {
			return new ModelAndView("error/error");
		}
		logger.info("获取新闻列表");
		String categoryTitle = "未知分类";
		if (smallid == 28) {
			categoryTitle = "学校要闻";
		} else if (smallid == 30) {
			categoryTitle = "综合新闻";
		} else if (smallid == 35) {
			categoryTitle = "通知通告";
		} else if (smallid == 27) {
			categoryTitle = "校外媒体";
		}else if (smallid == 36) {
			categoryTitle = "高教动态";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryTitle", categoryTitle);
		map.put("smallid", smallid);
		map.put("schoolNewsList",
				schoolNewsService.getNewsList(0, 5, smallid));
		return new ModelAndView("schoolnews/list", map);
	}
	
	@RequestMapping(value = "/getMoreNews", method = RequestMethod.GET)
	public ModelAndView getMoreNews(Integer start, Integer max,
			Integer smallid) {
		if (start == null || max == null || smallid == null) {
			return new ModelAndView("error/error");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("smallid", smallid);
		map.put("schoolNewsList",
				schoolNewsService.getNewsList(start, max, smallid));
		return new ModelAndView("schoolnews/morelist", map);
	}
}
