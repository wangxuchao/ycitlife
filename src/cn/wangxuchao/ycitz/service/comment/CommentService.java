package cn.wangxuchao.ycitz.service.comment;

import java.util.List;

import cn.wangxuchao.ycitz.model.comment.Comment;

public interface CommentService {
	Comment getComment(long id);

	void addComment(Comment comment);

	List<Comment> getNewsComment(long newsId);
}
