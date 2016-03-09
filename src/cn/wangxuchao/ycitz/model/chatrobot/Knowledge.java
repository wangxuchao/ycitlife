package cn.wangxuchao.ycitz.model.chatrobot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "knowledge")
public class Knowledge {
	@Id
	private long id;
	@Column(length = 2000, nullable = false)
	private String question;
	@Column(length = 8000, nullable = false)
	private String answer;
	@Column(nullable = false)
	private int category;

	public Knowledge() {
	}

	public Knowledge(long id, String question, String answer, int category) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
