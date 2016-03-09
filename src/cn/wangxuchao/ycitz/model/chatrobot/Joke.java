package cn.wangxuchao.ycitz.model.chatrobot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "joke")
public class Joke {
	@Id
	@GeneratedValue
	@Column(name = "joke_id")
	private long jokeId;
	@Column(name = "joke_content", length = 8000)
	private String jokeContent;

	public Joke() {
	}

	public Joke(long jokeId, String jokeContent) {
		super();
		this.jokeId = jokeId;
		this.jokeContent = jokeContent;
	}

	public long getJokeId() {
		return jokeId;
	}

	public void setJokeId(long jokeId) {
		this.jokeId = jokeId;
	}

	public String getJokeContent() {
		return jokeContent;
	}

	public void setJokeContent(String jokeContent) {
		this.jokeContent = jokeContent;
	}

}
