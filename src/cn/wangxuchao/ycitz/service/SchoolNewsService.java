package cn.wangxuchao.ycitz.service;

public interface SchoolNewsService {
	/**
	 * 获取学校官网首页新闻列表
	 * 
	 * @return String 新闻列表信息
	 */
	public String getIndexHtml();
	
	public String getNewsList(int smallid);
}
