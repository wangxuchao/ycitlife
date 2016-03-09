package cn.wangxuchao.ycitz.service.chatrobot;

import cn.wangxuchao.ycitz.model.chatrobot.Knowledge;

public interface ChatService {
	/**
	 * 得到索引存储目录
	 *
	 * @return WEB-INF/classes/index/
	 */
	String getIndexDir();

	/**
	 * 创建索引
	 */
	void createIndex();

	/**
	 * 从索引文件中根据问题检索答案
	 *
	 * @param content
	 * @return Knowledge
	 */
	Knowledge searchIndex(String content);

	/**
	 * 聊天方法（根据question返回answer）
	 *
	 * @param openId
	 *            用户的OpenID
	 * @param createTime
	 *            消息创建时间
	 * @param question
	 *            用户上行的问题
	 * @return answer
	 */
	String chat(String openId, String createTime, String question);

	/**
	 * 随机获取一个默认的答案
	 *
	 * @return
	 */
	String getDefaultAnswer();
}
