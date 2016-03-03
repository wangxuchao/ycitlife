package cn.wangxuchao.ycitz.model.schoolnews;

public class SchoolNewsJson {
	private long id;
	private String Title;
	private String Author;
	private String com;
	private String AddTime;
	private int SmallID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public String getAddTime() {
		return AddTime;
	}

	public void setAddTime(String addTime) {
		AddTime = addTime;
	}

	public int getSmallID() {
		return SmallID;
	}

	public void setSmallID(int smallID) {
		SmallID = smallID;
	}

}
