package cn.wangxuchao.ycitz.dao.comment;

import java.util.List;

import cn.wangxuchao.ycitz.dao.BaseDao;
import cn.wangxuchao.ycitz.model.comment.Comment;

public interface CommentDao extends BaseDao<Comment> {
	List<Comment> getNewsComment(long newsId);
}
