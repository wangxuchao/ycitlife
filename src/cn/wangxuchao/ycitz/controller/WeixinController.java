package cn.wangxuchao.ycitz.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wangxuchao.ycitz.service.CoreService;
import cn.wangxuchao.ycitz.util.SignUtil;

@Controller
public class WeixinController {
	// 处理GET请求
	@RequestMapping(value = "weixin", method = RequestMethod.GET)
	public @ResponseBody String doGet(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		// 如果校验成功
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}

		return "";
	}

	// 处理POST请求
	@RequestMapping(value = "weixin", method = RequestMethod.POST)
	public @ResponseBody String doPost(HttpServletRequest request) {

		// 请求校验
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");

		// 如果校验成功
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			String respXML = CoreService.processRequest(request);
			return respXML;
		}
		return "";
	}
}
