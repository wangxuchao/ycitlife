package cn.wangxuchao.ycitz.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import cn.wangxuchao.ycitz.util.HttpClientUtil;

@Service
public class SchoolNewsServiceImpl implements SchoolNewsService {
	private static final Log logger = LogFactory
			.getLog(SchoolNewsServiceImpl.class);
	// 学校首页链接
	private static final String indexUrl = "http://www.ycit.cn/";

	/**
	 * 获取学校官网首页新闻列表
	 * 
	 * @return String 新闻列表信息
	 */
	@Override
	public String getIndexHtml() {
		try {
			String html = HttpClientUtil.httpGet(indexUrl);
			if (html.startsWith("error")) {
				logger.info("学校首页新闻获取失败");
				return html;
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

			return html;
		} catch (Exception e) {
			logger.error("捕捉到异常：" + e.getMessage());
			return "{\"error\":\"code:0x000000\"}";
		}
	}
}
