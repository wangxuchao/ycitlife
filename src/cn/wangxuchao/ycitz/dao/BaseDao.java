package cn.wangxuchao.ycitz.dao;

import java.util.List;

public interface BaseDao<T> {
	void add(T t);

	void edit(T t);

	void delete(long id);

	T findById(long id);

	List<T> findAll();
}
