package cn.wangxuchao.ycitz.util;

import java.util.Date;
import java.util.List;

import cn.wangxuchao.ycitz.model.weixin.message.response.Article;
import cn.wangxuchao.ycitz.model.weixin.message.response.Music;
import cn.wangxuchao.ycitz.model.weixin.message.response.MusicMessage;
import cn.wangxuchao.ycitz.model.weixin.message.response.NewsMessage;

public class GetMessageUtil {
	/**
	 * 获取一个多图文消息
	 * 
	 * @return
	 */
	public static NewsMessage getNewsMessage(List<Article> articleList,
			String toUserName, String fromUserName) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(toUserName);
		newsMessage.setFromUserName(fromUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setArticles(articleList);
		newsMessage.setArticleCount(articleList.size());
		return newsMessage;
	}

	/**
	 * 获取一个音乐消息
	 * 
	 * @return
	 */
	public static MusicMessage getMusicMessage(Music music, String toUserName,
			String fromUserName) {
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setToUserName(fromUserName);
		musicMessage.setFromUserName(toUserName);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);
		musicMessage.setMusic(music);
		return musicMessage;
	}
}
