package cn.wangxuchao.ycitz.controller.upload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {
	private static final Log logger = LogFactory
			.getLog(UploadController.class);
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView getUpload() {
		return new ModelAndView("upload/upload");
	}
}
