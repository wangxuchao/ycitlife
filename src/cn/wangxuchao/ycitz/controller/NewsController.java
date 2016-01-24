package cn.wangxuchao.ycitz.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wangxuchao.ycitz.service.SchoolNewsService;

@Controller
@RequestMapping("/news")
public class NewsController {
	private static final Log logger = LogFactory.getLog(NewsController.class);

	@Autowired
	private SchoolNewsService schoolNewsService;

	@RequestMapping(value = "/getSchoolIndexNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolIndexNews() {
		logger.info("调用getSchoolIndexNews");
		return schoolNewsService.getIndexNews();
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
