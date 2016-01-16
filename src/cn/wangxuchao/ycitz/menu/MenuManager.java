package cn.wangxuchao.ycitz.menu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wangxuchao.ycitz.model.Token;
import cn.wangxuchao.ycitz.util.CommonUtil;
import cn.wangxuchao.ycitz.util.MenuUtil;

/**
 * 菜单管理器类
 *
 * @author 王绪超
 */
public class MenuManager {

	private static final Log logger = LogFactory.getLog(MenuManager.class);

	/**
	 * 定义菜单结构
	 *
	 * @return
	 */
	private static Menu getMenu() {

		// Click Button 示例
		ClickButton btn11 = new ClickButton();
		btn11.setName("xxxx");
		btn11.setType("click");
		btn11.setKey("key1");

		// View Button 示例
		ViewButton btn21 = new ViewButton();
		btn21.setName("xxxx");
		btn21.setType("view");
		btn21.setUrl("http://www.example.com");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("xxxx");
		mainBtn1.setSub_button(new BaseButton[] { btn11 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("xxxx");
		mainBtn2.setSub_button(new BaseButton[] { btn21 });

		// ComplexButton mainBtn3 = new ComplexButton();
		// mainBtn3.setName("xxxx");
		// mainBtn3.setSub_button(new BaseButton[] { btn31, btn32, btn33, btn34,
		// btn35 });

		Menu menu = new Menu();
		menu.setButton(new BaseButton[] { mainBtn1, mainBtn2 });

		return menu;
	}

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "xxxxxxxxxxxxxxxxxx";
		// 第三方用户唯一凭证密钥
		String appSecret = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";

		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);

		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(),
					token.getAccessToken());
			// 查看菜单
			// boolean result = true;
			// System.out.println(MenuUtil.getMenu(token.getAccessToken()));
			// 删除菜单
			// boolean result = MenuUtil.deleteMenu(token.getAccessToken());

			// 判断菜单创建结果
			if (result) {
				logger.info("菜单创建成功！");
			} else {
				logger.info("菜单创建失败！");
			}
		}
	}
}
