package cn.wangxuchao.ycitz.model.schoolnews;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "indexnews")
public class IndexNews {
	@Id
	private long newsId;
	private String newsName;
	private String pubTime;
	private String newsUrl;

	public IndexNews() {
	}

	public IndexNews(long newsId, String newsName, String pubTime,
			String newsUrl) {
		super();
		this.newsId = newsId;
		this.newsName = newsName;
		this.pubTime = pubTime;
		this.newsUrl = newsUrl;
	}

	public long getNewsId() {
		return newsId;
	}

	public void setNewsId(long newsId) {
		this.newsId = newsId;
	}

	public String getNewsName() {
		return newsName;
	}

	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

}
