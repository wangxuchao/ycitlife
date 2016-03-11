package cn.wangxuchao.ycitz.dao.chatrobot;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.wangxuchao.ycitz.dao.BaseDaoImpl;
import cn.wangxuchao.ycitz.model.chatrobot.Joke;

@Repository
public class JokeDaoImpl extends BaseDaoImpl<Joke> implements JokeDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public String getJoke() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Joke.class);
		criteria.add(Restrictions.sqlRestriction(" 1=1 order by rand() "));
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		@SuppressWarnings("unchecked")
		List<Joke> jokeList = criteria.list();
		if (jokeList.isEmpty()) {
			return null;
		} else {
			return jokeList.get(0).getJokeContent();
		}
	}

}
