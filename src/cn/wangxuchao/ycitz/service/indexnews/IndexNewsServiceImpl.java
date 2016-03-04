package cn.wangxuchao.ycitz.service.indexnews;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wangxuchao.ycitz.dao.indexnews.IndexNewsDao;
import cn.wangxuchao.ycitz.model.indexnews.IndexNews;
import cn.wangxuchao.ycitz.util.HttpClientUtil;
import cn.wangxuchao.ycitz.util.ValueUtil;

@Service
public class IndexNewsServiceImpl implements IndexNewsService {
	private static final Log logger = LogFactory
			.getLog(IndexNewsServiceImpl.class);

	@Autowired
	private IndexNewsDao indexNewsDao;

	/**
	 * 获取学校官网首页新闻列表
	 * 
	 * @return String 新闻列表信息
	 */
	@Override
	public String getSchoolIndexNews() {
		String url = ValueUtil.YCIT_HOME_PAGE;
		try {
			// get请求获取学校首页html
			String html = HttpClientUtil.httpGet(url);

			if (html.startsWith("error")) {
				// 改用备用地址
				url = ValueUtil.YCIT_HOME_PAGE_BACKUP;
				// get请求获取学校首页html
				html = HttpClientUtil.httpGet(url);
				if (html.startsWith("error")) {
					logger.info("学校首页新闻获取失败");
					return html;
				}
			}
			// 去除多余部分
			html = html
					.replaceAll(
							"<!DOCTYPE html[\\w\\W]*t=\"学校要闻\">|<div class=\"content_12\">[\\w\\W]*",
							"")
					.replaceAll("..:..:..\"><a href=\"", ">")
					.replaceAll(
							"\" target=\"_blank\"><div style=\"color:#000000\">",
							"\n").replaceAll("</?[a-zA-Z]+[^><]*>", "")
					.replaceAll("&nbsp;|\\.\\.\\.| |	", "")
					.replaceAll("&amp;smallid=", "&smallid=")
					.replaceAll("\\n(\\s*\\n)+", "\n")
					.replaceAll("(^\\s*)|(\\s*$)", "")
					.replaceAll("\\r\\n", "\n");

			String[] htmlArry = html.split("\\n");

			// 判断获取到的是否满足首页新闻列表数量要求
			if (htmlArry.length != 72) {
				logger.info("学校新闻首页新闻条数不对");
				return "error:学校新闻首页新闻条数不对";
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

				return "success";
			}
		} catch (Exception e) {
			logger.error("捕捉到异常：" + e.getMessage());
			return "error:0x000000";
		}
	}
	
	/**
	 * 获得首页新闻列表
	 */
	@Override
	public List<IndexNews> getIndexNews() {
		List<IndexNews> indexNewsList = indexNewsDao.findAll();
		//如果取到的数据条数不对，重新从学校官网抓取
		if (indexNewsList.size() != 24) {
			getSchoolIndexNews();
			indexNewsList = indexNewsDao.findAll();
		}
		return indexNewsList;
	}

}
