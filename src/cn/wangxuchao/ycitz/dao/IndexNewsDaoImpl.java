package cn.wangxuchao.ycitz.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.wangxuchao.ycitz.model.IndexNews;

@Repository
public class IndexNewsDaoImpl implements IndexNewsDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public void add(IndexNews indexNews) {
		sessionFactory.getCurrentSession().saveOrUpdate(indexNews);
	}

	@Override
	@Transactional
	public void edit(IndexNews indexNews) {
		sessionFactory.getCurrentSession().update(indexNews);
	}

	@Override
	@Transactional
	public void delete(int newsId) {
		sessionFactory.getCurrentSession().delete(getIndexNews(newsId));
	}

	@Override
	@Transactional
	public IndexNews getIndexNews(int newsId) {
		return (IndexNews) sessionFactory.getCurrentSession().get(
				IndexNews.class, newsId);
	}

	@Override
	@Transactional
	public List<IndexNews> findAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<IndexNews> indexNewsList = session.createCriteria(IndexNews.class).list();
		return indexNewsList;
	}

}
