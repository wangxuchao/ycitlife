package cn.wangxuchao.ycitz.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	@RequestMapping("/error")
	public String getError() {
		return "error/error";
	}
}
