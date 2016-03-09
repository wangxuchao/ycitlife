package cn.wangxuchao.ycitz.dao.chatrobot;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.wangxuchao.ycitz.dao.BaseDaoImpl;
import cn.wangxuchao.ycitz.model.chatrobot.KnowledgeSub;

@Repository
public class KnowledgeSubDaoImpl extends BaseDaoImpl<KnowledgeSub> implements
		KnowledgeSubDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public String getKnowledgeSub(int knowledgeId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				KnowledgeSub.class);
		criteria.add(Restrictions.eq("pid", knowledgeId));
		criteria.add(Restrictions.sqlRestriction("order by rand()"));
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<KnowledgeSub> knowledgeSubList = criteria.list();
		if (knowledgeSubList.isEmpty()) {
			return null;
		} else {
			return knowledgeSubList.get(0).getAnswer();
		}
	}

}
