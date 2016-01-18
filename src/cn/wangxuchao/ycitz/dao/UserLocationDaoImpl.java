package cn.wangxuchao.ycitz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.wangxuchao.ycitz.model.UserLocation;

@Repository
public class UserLocationDaoImpl implements UserLocationDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void add(UserLocation userLocation) {
		sessionFactory.getCurrentSession().saveOrUpdate(userLocation);
	}

	@Override
	@Transactional
	public void edit(UserLocation userLocation) {
		sessionFactory.getCurrentSession().update(userLocation);
	}

	@Override
	@Transactional
	public void delete(int id) {
		sessionFactory.getCurrentSession().delete(getUserLocation(id));
	}

	@Override
	@Transactional
	public UserLocation getUserLocation(int id) {
		return (UserLocation) sessionFactory.getCurrentSession().get(
				UserLocation.class, id);
	}

	@Override
	@Transactional
	public List<UserLocation> findAll() {
		@SuppressWarnings("unchecked")
		List<UserLocation> userLocationList = sessionFactory.getCurrentSession().createCriteria(
				UserLocation.class).list();
		return userLocationList;
	}

	@Override
	@Transactional
	public UserLocation getLastLocation(String openId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserLocation.class);
		criteria.add(Restrictions.eq("openId", openId));
		criteria.addOrder(Order.desc("id"));
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<UserLocation> userLocationList = criteria.list();
		if (userLocationList.isEmpty()) {
			return null;
		} else {
			return userLocationList.get(0);
		}
	}

}
