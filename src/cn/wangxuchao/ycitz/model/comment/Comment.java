package cn.wangxuchao.ycitz.model.comment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "news_id", nullable = false)
	private long newsId;
	@Column(name = "open_id", length = 30, nullable = false)
	private String openId;
	@Column(name = "add_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date addTime;
	@Column(name = "comment_text", length = 200, nullable = false)
	private String commentText;
	@Column(name = "parent_id")
	private long parentId;

	public Comment() {
	}

	public Comment(long id, long newsId, String openId, Date addTime,
			String commentText, long parentId) {
		super();
		this.id = id;
		this.newsId = newsId;
		this.openId = openId;
		this.addTime = addTime;
		this.commentText = commentText;
		this.parentId = parentId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

}
