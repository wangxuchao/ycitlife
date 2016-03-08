package cn.wangxuchao.ycitz.dao.schoolnews;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.wangxuchao.ycitz.dao.BaseDaoImpl;
import cn.wangxuchao.ycitz.model.schoolnews.SchoolNews;

@Repository
public class SchoolNewsDaoImpl extends BaseDaoImpl<SchoolNews> implements
		SchoolNewsDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<SchoolNews> getNewsList(int start, int max, int smallid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				SchoolNews.class);
		criteria.add(Restrictions.eq("smallid", smallid));
		criteria.addOrder(Order.desc("id"));
		criteria.setFirstResult(start);
		criteria.setMaxResults(max);
		@SuppressWarnings("unchecked")
		List<SchoolNews> schoolNewsList = criteria.list();
		if (schoolNewsList.isEmpty()) {
			return null;
		} else {
			return schoolNewsList;
		}
	}

}
