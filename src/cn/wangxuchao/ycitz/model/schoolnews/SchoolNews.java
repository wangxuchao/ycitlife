package cn.wangxuchao.ycitz.model.schoolnews;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "schoolnews")
public class SchoolNews {

	@Id
	private long id;
	@Column(length = 200)
	private String title;
	@Column(length = 50)
	private String author;
	@Column(length = 50)
	private String com;
	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;
	private int smallid;

	public SchoolNews() {
	}

	public SchoolNews(long id, String title, String author, String com,
			Date addtime, int smallid) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.com = com;
		this.addtime = addtime;
		this.smallid = smallid;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public int getSmallid() {
		return smallid;
	}

	public void setSmallid(int smallid) {
		this.smallid = smallid;
	}

}
