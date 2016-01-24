package cn.wangxuchao.ycitz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.wangxuchao.ycitz.dao.IndexNewsDao;
import cn.wangxuchao.ycitz.dao.SchoolNewsDao;
import cn.wangxuchao.ycitz.dao.SchoolNewsInfoDao;
import cn.wangxuchao.ycitz.model.IndexNews;
import cn.wangxuchao.ycitz.model.SchoolNews;
import cn.wangxuchao.ycitz.model.SchoolNewsInfo;
import cn.wangxuchao.ycitz.model.SchoolNewsJson;
import cn.wangxuchao.ycitz.service.SchoolNewsService;

@Controller
public class NewsController {
	private static final Log logger = LogFactory.getLog(NewsController.class);

	@Autowired
	private SchoolNewsService schoolNewsService;
	@Autowired
	private IndexNewsDao indexNewsDao;
	@Autowired
	private SchoolNewsDao schoolNewsDao;
	@Autowired
	private SchoolNewsInfoDao schoolNewsInfoDao;

	@RequestMapping(value = "/getSchoolIndexNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolIndexNews() {

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

	@RequestMapping(value = "/getSchoolNews", method = RequestMethod.GET)
	public @ResponseBody String getSchoolNews(@RequestParam int smallid) {

		String json = schoolNewsService.getNewsList(smallid);

		// 判断获取到的是否满足首页新闻列表数量要求
		if (!json.startsWith("[{")) {
			return "{\"error\":\"code:0x000002\"}";
		} else {
			Gson gson = new Gson();
			List<SchoolNewsJson> list = gson.fromJson(json,
					new TypeToken<List<SchoolNewsJson>>() {
					}.getType());
			for (SchoolNewsJson schoolNewsJson : list) {
				SchoolNews schoolNews = new SchoolNews();
				schoolNews.setId(schoolNewsJson.getId());
				schoolNews.setTitle(schoolNewsJson.getTitle());
				schoolNews.setCom(schoolNewsJson.getCom());
				schoolNews.setAuthor(schoolNewsJson.getAuthor());
				try {
					schoolNews.setAddtime(new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss").parse(schoolNewsJson
							.getAddTime()));
				} catch (ParseException e) {
				}
				schoolNews.setSmallid(schoolNewsJson.getSmallID());
				schoolNewsDao.add(schoolNews);
			}

			return "{\"result\":\"success\"}";
		}
	}

	@RequestMapping(value = "/getSchoolNewsInfo", method = RequestMethod.GET)
	public @ResponseBody String getSchoolNewsInfo(@RequestParam int id,
			@RequestParam int smallid) {

		String[] htmlArry = schoolNewsService.getNewsInfo(id, smallid).split(
				"\\n");

		// 判断获取是否成功
		if (htmlArry[0].startsWith("{") || htmlArry[0].startsWith("error")) {
			return "{\"error\":\"code:0x000002\"}";
		} else {
			int count = htmlArry.length;
			SchoolNewsInfo schoolNewsInfo = new SchoolNewsInfo();
			schoolNewsInfo.setId(id);
			schoolNewsInfo.setSmallid(smallid);
			String newsinfo = "";
			for (int i = 0; i < count - 1; i++) {
				newsinfo = newsinfo + htmlArry[i];
			}
			schoolNewsInfo.setNewsinfo(newsinfo);
			schoolNewsInfo
					.setZeren(htmlArry[count - 1].replaceAll("责任编辑：", ""));
			schoolNewsInfoDao.add(schoolNewsInfo);
			
			return "{\"result\":\"success\"}";
		}
	}
}
