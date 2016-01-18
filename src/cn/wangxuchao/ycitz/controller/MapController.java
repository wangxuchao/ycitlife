package cn.wangxuchao.ycitz.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("map")
public class MapController {
	private static final Log logger = LogFactory.getLog(MapController.class);

	@RequestMapping(value = "/route", method = RequestMethod.GET)
	public ModelAndView getRoute(@RequestParam String p1,
			@RequestParam String p2) {
		logger.info("显示路径");
		Map<String, String> map = new HashMap<String, String>();
		map.put("p1", p1);
		map.put("p2", p2);
		return new ModelAndView("route", map);
	}
}
