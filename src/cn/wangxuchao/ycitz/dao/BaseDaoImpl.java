package cn.wangxuchao.ycitz.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		@SuppressWarnings("rawtypes")
		Class clazz = getClass();

		while (clazz != Object.class) {
			Type t = clazz.getGenericSuperclass();
			if (t instanceof ParameterizedType) {
				Type[] args = ((ParameterizedType) t).getActualTypeArguments();
				if (args[0] instanceof Class) {
					this.clazz = (Class<T>) args[0];
					break;
				}
			}
			clazz = clazz.getSuperclass();
		}
	}

	@Override
	@Transactional
	public void add(T t) {
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	@Transactional
	public void edit(T t) {
		sessionFactory.getCurrentSession().update(t);
	}

	@Override
	@Transactional
	public void delete(long id) {
		sessionFactory.getCurrentSession().delete(findById(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public T findById(long id) {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

	@Override
	@Transactional
	public List<T> findAll() {
		@SuppressWarnings("unchecked")
		List<T> tList = sessionFactory.getCurrentSession()
				.createCriteria(clazz).list();
		return tList;
	}

}
