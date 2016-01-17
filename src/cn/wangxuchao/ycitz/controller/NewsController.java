package cn.wangxuchao.ycitz.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wangxuchao.ycitz.dao.IndexNewsDao;
import cn.wangxuchao.ycitz.model.IndexNews;
import cn.wangxuchao.ycitz.service.SchoolNewsService;

@Controller
public class NewsController {
	private static final Log logger = LogFactory.getLog(NewsController.class);

	@Autowired
	private SchoolNewsService schoolNewsService;
	@Autowired
	private IndexNewsDao indexNewsDao;

	@RequestMapping(value = "/getSchoolIndexNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolNews() {

		String[] htmlArry = schoolNewsService.getIndexHtml().split("\\n");

		// 判断获取到的是否满足首页新闻列表数量要求
		if (htmlArry.length != 72) {
			logger.info("学校新闻首页新闻条数不对");
			return "{\"error\":\"code:0x000002\"}";
		} else {
			int count = 1;
			for (int i = 0; i < 72; i++) {
				IndexNews indexNews = new IndexNews();
				indexNews.setNewsId(count);
				indexNews.setNewsUrl(htmlArry[i]);
				i++;
				indexNews.setNewsName(htmlArry[i]);
				i++;
				indexNews.setPubTime(htmlArry[i]);
				indexNewsDao.add(indexNews);
				count++;
			}

			return "{\"result\":\"success\"}";
		}
	}
}
