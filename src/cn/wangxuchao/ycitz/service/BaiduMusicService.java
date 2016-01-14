package cn.wangxuchao.ycitz.service;

import cn.wangxuchao.ycitz.message.response.Music;

public interface BaiduMusicService {

	/**
	 * 歌曲点播使用指南
	 *
	 * @return
	 */
	String getUsageMusic();

	/**
	 * 根据名称和作者搜索音乐
	 *
	 * @param musicTitle
	 *            音乐名称
	 * @param musicAuthor
	 *            音乐作者
	 * @return Music
	 */
	Music searchMusic(String musicTitle, String musicAuthor);
}
