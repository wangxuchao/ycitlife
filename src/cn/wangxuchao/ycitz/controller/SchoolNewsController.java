package cn.wangxuchao.ycitz.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.wangxuchao.ycitz.model.IndexNews;
import cn.wangxuchao.ycitz.service.SchoolNewsService;

@Controller
public class SchoolNewsController {
	private static final Log logger = LogFactory
			.getLog(SchoolNewsController.class);

	@Autowired
	private SchoolNewsService schoolNewsService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getRoute() {
		logger.info("访问主页");
		List<IndexNews> indexNewsList = schoolNewsService.getIndexNewsFromDb();
		return new ModelAndView("main", "indexNewsList", indexNewsList);
	}
}
