package cn.wangxuchao.ycitz.menu;

/**
 * 复合类型的按钮
 *
 * @author 王绪超
 */
public class ComplexButton extends BaseButton {

	private BaseButton[] sub_button;

	public BaseButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(BaseButton[] sub_button) {
		this.sub_button = sub_button;
	}
}
