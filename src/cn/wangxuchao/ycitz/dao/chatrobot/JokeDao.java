package cn.wangxuchao.ycitz.dao.chatrobot;

import cn.wangxuchao.ycitz.dao.BaseDao;
import cn.wangxuchao.ycitz.model.chatrobot.Joke;

public interface JokeDao extends BaseDao<Joke> {
	/**
	 * 随机获取一条笑话
	 * @return
	 */
	String getJoke();
}
