package cn.wangxuchao.ycitz.model.chatrobot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chat_log")
public class ChatLog {
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "open_id", length = 30, nullable = false)
	private String openId;
	@Column(name = "create_time", length = 20, nullable = false)
	private String createTime;
	@Column(name = "req_msg", length = 2000, nullable = false)
	private String reqMsg;
	@Column(name = "resp_msg", length = 2000, nullable = false)
	private String respMsg;
	@Column(name = "chat_category")
	private int chatCategory;

	public ChatLog() {
	}

	public ChatLog(long id, String openId, String createTime, String reqMsg,
			String respMsg, int chatCategory) {
		super();
		this.id = id;
		this.openId = openId;
		this.createTime = createTime;
		this.reqMsg = reqMsg;
		this.respMsg = respMsg;
		this.chatCategory = chatCategory;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReqMsg() {
		return reqMsg;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public int getChatCategory() {
		return chatCategory;
	}

	public void setChatCategory(int chatCategory) {
		this.chatCategory = chatCategory;
	}

}
