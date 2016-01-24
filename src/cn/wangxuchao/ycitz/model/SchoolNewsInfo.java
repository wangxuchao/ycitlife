package cn.wangxuchao.ycitz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "schoolnewsinfo")
public class SchoolNewsInfo {
	@Id
	private long id;
	private int smallid;
	@Type(type = "text")
	private String newsinfo;
	@Column(length = 50)
	private String zeren;

	public SchoolNewsInfo() {
	}

	public SchoolNewsInfo(long id, int smallid, String newsinfo, String zeren) {
		super();
		this.id = id;
		this.smallid = smallid;
		this.newsinfo = newsinfo;
		this.zeren = zeren;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSmallid() {
		return smallid;
	}

	public void setSmallid(int smallid) {
		this.smallid = smallid;
	}

	public String getNewsinfo() {
		return newsinfo;
	}

	public void setNewsinfo(String newsinfo) {
		this.newsinfo = newsinfo;
	}

	public String getZeren() {
		return zeren;
	}

	public void setZeren(String zeren) {
		this.zeren = zeren;
	}

}
