package cn.wangxuchao.ycitz.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wangxuchao.ycitz.service.CoreService;
import cn.wangxuchao.ycitz.util.SignUtil;

@Controller
public class WeixinController {
	
	private static final Log logger = LogFactory
			.getLog(WeixinController.class);
	@Autowired
	private CoreService coreService;

	// 处理GET请求
	@RequestMapping(value = "weixin", method = RequestMethod.GET)
	public @ResponseBody String doGet(HttpServletRequest request) {
		logger.info("--开始GET请求校验");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		// 如果校验成功
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		logger.error("--GET请求校验失败");
		return "";
	}

	// 处理POST请求
	@RequestMapping(value = "weixin", method = RequestMethod.POST)
	public @ResponseBody String doPost(HttpServletRequest request) {
		logger.info("--开始post请求校验");
		// 请求校验
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		// 如果校验成功
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			String respXML = coreService.processRequest(request);
			return respXML;
		}
		logger.error("--POST请求校验失败");
		return "";
	}
}
