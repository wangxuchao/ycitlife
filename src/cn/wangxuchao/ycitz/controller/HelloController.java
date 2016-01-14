package cn.wangxuchao.ycitz.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloController {
	// 处理GET请求
	@RequestMapping(value = "hello", method = RequestMethod.GET)
	public @ResponseBody String doGet(HttpServletRequest request) {
		return "hello";
	}
	
	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("home","home","aaa");
	}
}
