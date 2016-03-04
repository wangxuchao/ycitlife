package cn.wangxuchao.ycitz.controller.indexnews;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wangxuchao.ycitz.model.indexnews.IndexNews;
import cn.wangxuchao.ycitz.service.indexnews.IndexNewsService;

@Controller
public class IndexNewsController {
	private static final Log logger = LogFactory
			.getLog(IndexNewsController.class);

	@Autowired
	private IndexNewsService indexNewsService;

	/**
	 * 访问校园新闻主页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getRoute() {
		logger.info("访问主页");
		List<IndexNews> indexNewsList = indexNewsService.getIndexNews();
		if (indexNewsList.size() != 24) {
			return new ModelAndView("error/error");
		}
		return new ModelAndView("indexnews/main", "indexNewsList",
				indexNewsList);
	}

	/**
	 * 手动更新学校首页新闻列表
	 * @return
	 */
	@RequestMapping(value = "/getSchoolIndexNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolIndexNews() {
		logger.info("调用getSchoolIndexNews");
		return indexNewsService.getSchoolIndexNews();
	}
}
