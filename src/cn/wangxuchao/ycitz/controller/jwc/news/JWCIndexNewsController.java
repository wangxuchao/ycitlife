package cn.wangxuchao.ycitz.controller.jwc.news;

import java.util.HashMap;
import java.util.List;
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

import cn.wangxuchao.ycitz.model.jwc.news.JWCIndexNews;
import cn.wangxuchao.ycitz.service.jwc.news.JWCNewsService;

@Controller
public class JWCIndexNewsController {
	private static final Log logger = LogFactory
			.getLog(JWCIndexNewsController.class);

	@Autowired
	private JWCNewsService jwcNewsService;

	/**
	 * 访问校园新闻主页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/jwc", method = RequestMethod.GET)
	public ModelAndView getRoute() {
		logger.info("访问教务处主页");
		List<JWCIndexNews> indexNewsList = jwcNewsService.getIndexNews();
		if (indexNewsList.size() != 8) {
			Map<String, String> msgmap = new HashMap<String, String>();
			msgmap.put("msg_icon_class", "weui_icon_warn");
			msgmap.put("msg_title", "系统忙！");
			msgmap.put("msg_desc", "请您稍候再试");
			return new ModelAndView("error/error", msgmap);
		}
		return new ModelAndView("jwc/news/main", "indexNewsList", indexNewsList);
	}

	/**
	 * 手动更新学校教务处首页新闻列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getIndexNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolIndexNews() {
		return jwcNewsService.getNewsList();
	}

	@RequestMapping(value = "jwc/news", method = RequestMethod.GET)
	public ModelAndView getSchoolNewsDetail(Integer newsid, Integer classid) {
		if (newsid == null || classid == null) {
			Map<String, String> msgmap = new HashMap<String, String>();
			msgmap.put("msg_icon_class", "weui_icon_warn");
			msgmap.put("msg_title", "系统忙！");
			msgmap.put("msg_desc", "请您稍候再试");
			return new ModelAndView("error/error", msgmap);
		}
		return new ModelAndView("jwc/news/news", "jwcNewsInfo",
				jwcNewsService.getJWCNewsInfo(newsid, classid));
	}
}
