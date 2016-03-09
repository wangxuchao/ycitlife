package cn.wangxuchao.ycitz.dao.chatrobot;

import cn.wangxuchao.ycitz.dao.BaseDao;
import cn.wangxuchao.ycitz.model.chatrobot.KnowledgeSub;

public interface KnowledgeSubDao extends BaseDao<KnowledgeSub> {
	String getKnowledgeSub(int knowledgeId);
}
