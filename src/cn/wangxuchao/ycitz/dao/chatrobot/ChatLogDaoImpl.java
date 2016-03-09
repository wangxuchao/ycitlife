package cn.wangxuchao.ycitz.dao.chatrobot;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.wangxuchao.ycitz.dao.BaseDaoImpl;
import cn.wangxuchao.ycitz.model.chatrobot.ChatLog;

@Repository
public class ChatLogDaoImpl extends BaseDaoImpl<ChatLog> implements ChatLogDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public int getLastCategory(String openId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				ChatLog.class);
		criteria.add(Restrictions.eq("open_id", openId));
		criteria.addOrder(Order.desc("id"));
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<ChatLog> chatLogList = criteria.list();
		if (chatLogList.isEmpty()) {
			return -1;
		} else {
			return chatLogList.get(0).getChatCategory();
		}
	}

}
