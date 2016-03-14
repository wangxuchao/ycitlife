package cn.wangxuchao.ycitz.model.jwc.news;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "jwcnewsinfo")
public class JWCNewsInfo {
	// newsid
	@Id
	private long id;
	// classid
	private int classid;
	// 标题
	private String title;
	// 作者
	private String author;
	// 来自
	private String com;
	// 添加时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date addtime;
	// 正文
	@Type(type = "text")
	private String article;

	public JWCNewsInfo() {

	}

	public JWCNewsInfo(long id, int classid, String title, String author,
			String com, Date addtime, String article) {
		super();
		this.id = id;
		this.classid = classid;
		this.title = title;
		this.author = author;
		this.com = com;
		this.addtime = addtime;
		this.article = article;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
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

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

}
