package cn.wangxuchao.ycitz.dao;

import java.util.List;

public interface BaseDao<T> {
	void add(T t);

	void edit(T t);

	void delete(int id);

	T getIndexNews(int id);

	List<T> findAll();
}
