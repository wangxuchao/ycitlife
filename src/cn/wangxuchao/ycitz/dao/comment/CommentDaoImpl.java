package cn.wangxuchao.ycitz.dao.comment;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.wangxuchao.ycitz.dao.BaseDaoImpl;
import cn.wangxuchao.ycitz.model.comment.Comment;

@Repository
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements CommentDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<Comment> getNewsComment(long newsId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Comment.class);
		criteria.add(Restrictions.eq("newsId", newsId));
		criteria.addOrder(Order.desc("addTime"));
		@SuppressWarnings("unchecked")
		List<Comment> commentList = criteria.list();
		if (commentList.isEmpty()) {
			return null;
		} else {
			return commentList;
		}
	}

}
