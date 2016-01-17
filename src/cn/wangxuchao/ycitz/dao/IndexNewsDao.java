package cn.wangxuchao.ycitz.dao;

import java.util.List;

import cn.wangxuchao.ycitz.model.IndexNews;

public interface IndexNewsDao {
	public void add(IndexNews indexNews);

	public void edit(IndexNews indexNews);

	public void delete(int newsId);

	public IndexNews getIndexNews(int newsId);

	public List<IndexNews> findAll();
}
