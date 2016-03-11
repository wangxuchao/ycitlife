package cn.wangxuchao.ycitz.controller.comment;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.wangxuchao.ycitz.model.comment.Comment;
import cn.wangxuchao.ycitz.service.comment.CommentService;

@Controller
public class CommentController {
	private static final Log logger = LogFactory
			.getLog(CommentController.class);

	@Autowired
	private CommentService commnentService;

	@RequestMapping(value = "get_comment", method = RequestMethod.GET)
	public ModelAndView getNewsComment(Integer newsid) {
		List<Comment> commentList = commnentService.getNewsComment(Long
				.valueOf(newsid));
		if (commentList == null) {
			return new ModelAndView("error/nocomment");
		}
		return new ModelAndView("comment/getComment", "commentList",
				commentList);
	}

	@RequestMapping(value = "add_comment", method = RequestMethod.POST)
	public @ResponseBody String addComment(Integer newsid, String comment_text) {
		Comment comment = new Comment();
		comment.setNewsId(newsid);
		comment.setAddTime(new Date());
		comment.setOpenId("openidxxxxxxxxxxxxxxxxxxxxxx");
		comment.setCommentText(comment_text);
		comment.setParentId(0);

		commnentService.addComment(comment);

		return "success";
	}
}
