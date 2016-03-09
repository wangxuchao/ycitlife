package cn.wangxuchao.ycitz.dao.chatrobot;

import cn.wangxuchao.ycitz.dao.BaseDao;
import cn.wangxuchao.ycitz.model.chatrobot.ChatLog;

public interface ChatLogDao extends BaseDao<ChatLog> {
	
	int getLastCategory(String openId);
}
