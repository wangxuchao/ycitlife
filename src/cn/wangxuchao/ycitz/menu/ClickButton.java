package cn.wangxuchao.ycitz.menu;

/**
 * click类型的按钮
 *
 * @author 王绪超
 */
public class ClickButton extends BaseButton {

	private String type;
	private String key;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}