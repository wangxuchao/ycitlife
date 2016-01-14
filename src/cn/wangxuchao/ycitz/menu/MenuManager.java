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

		ClickButton btn11 = new ClickButton();
		btn11.setName("免费加油");
		btn11.setType("click");
		btn11.setKey("jiayou");

		ViewButton btn21 = new ViewButton();
		btn21.setName("爆款返场");
		btn21.setType("view");
		btn21.setUrl("http://112.124.24.100/liantong/phone.html");

		ViewButton btn22 = new ViewButton();
		btn22.setName("号卡专区");
		btn22.setType("view");
		btn22.setUrl("http://112.124.24.100/liantong/talkcard.html");

		ViewButton btn23 = new ViewButton();
		btn23.setName("特惠机型");
		btn23.setType("view");
		btn23.setUrl("http://112.124.24.100/liantong/store.html");

		ViewButton btn24 = new ViewButton();
		btn24.setName("4G上网");
		btn24.setType("view");
		btn24.setUrl("http://112.124.24.100/liantong/datecard.html");

		ViewButton btn31 = new ViewButton();
		btn31.setName("我要查询");
		btn31.setType("view");
		btn31.setUrl("http://wap.10010.com");

		ClickButton btn33 = new ClickButton();
		btn33.setName("在线咨询");
		btn33.setType("click");
		btn33.setKey("zixun");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("最新活动");
		mainBtn1.setSub_button(new BaseButton[] { btn11 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("联通商城");
		mainBtn2.setSub_button(new BaseButton[] { btn21, btn22, btn24, btn23 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("我的服务");
		mainBtn3.setSub_button(new BaseButton[] { btn31, btn33 });

		Menu menu = new Menu();
		menu.setButton(new BaseButton[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wx5d2524ead18e4d17";
		// 第三方用户唯一凭证密钥
		String appSecret = "b0b51ec9b1e4bbc682cdf129ba96f67f ";

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
