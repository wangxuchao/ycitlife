package cn.wangxuchao.ycitz.service.comment;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wangxuchao.ycitz.dao.comment.CommentDao;
import cn.wangxuchao.ycitz.model.comment.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	private static final Log logger = LogFactory
			.getLog(CommentServiceImpl.class);

	@Autowired
	private CommentDao commentDao;

	@Override
	public Comment getComment(long id) {
		return commentDao.findById(id);
	}

	@Override
	public void addComment(Comment comment) {
		commentDao.add(comment);
	}

	@Override
	public List<Comment> getNewsComment(long newsId) {
		return commentDao.getNewsComment(newsId);
	}

}
