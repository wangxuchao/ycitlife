package cn.wangxuchao.ycitz.menu;

/**
 * view类型的按钮
 *
 * @author 王绪超
 */
public class ViewButton extends BaseButton {

	private String type;
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
