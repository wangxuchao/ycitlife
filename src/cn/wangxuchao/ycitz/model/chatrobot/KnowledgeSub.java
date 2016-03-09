package cn.wangxuchao.ycitz.model.chatrobot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "knowledge_sub")
public class KnowledgeSub {
	@Id
	@GeneratedValue
	private long id;
	@Column(nullable = false)
	private int pid;
	@Column(length = 8000, nullable = false)
	private String answer;

	public KnowledgeSub() {
	}

	public KnowledgeSub(long id, int pid, String answer) {
		super();
		this.id = id;
		this.pid = pid;
		this.answer = answer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
