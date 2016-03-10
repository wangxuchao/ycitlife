package cn.wangxuchao.ycitz.menu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.wangxuchao.ycitz.model.weixin.Token;
import cn.wangxuchao.ycitz.util.CommonUtil;
import cn.wangxuchao.ycitz.util.MenuUtil;
import cn.wangxuchao.ycitz.util.ValueUtil;

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
		btn11.setName("学校要闻");
		btn11.setType("click");
		btn11.setKey("xxyw");

		ClickButton btn12 = new ClickButton();
		btn12.setName("通知通告");
		btn12.setType("click");
		btn12.setKey("tztg");

		ClickButton btn13 = new ClickButton();
		btn13.setName("高教动态");
		btn13.setType("click");
		btn13.setKey("gjdt");

		ClickButton btn14 = new ClickButton();
		btn14.setName("综合新闻");
		btn14.setType("click");
		btn14.setKey("zhxw");

		ClickButton btn15 = new ClickButton();
		btn15.setName("校外媒体");
		btn15.setType("click");
		btn15.setKey("xwmt");

		ViewButton btn21 = new ViewButton();
		btn21.setName("学校官网");
		btn21.setType("view");
		btn21.setUrl(ValueUtil.PROJECT_ROOT);

		ViewButton btn31 = new ViewButton();
		btn31.setName("社区交流");
		btn31.setType("view");
		btn31.setUrl("http://m.wsq.qq.com/251265174");

		ClickButton btn32 = new ClickButton();
		btn32.setName("其他功能");
		btn32.setType("click");
		btn32.setKey("function");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("盐工动态");
		mainBtn1.setSub_button(new BaseButton[] { btn11, btn12, btn13, btn14,
				btn15 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("移动校园");
		mainBtn2.setSub_button(new BaseButton[] { btn21 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("其他");
		mainBtn3.setSub_button(new BaseButton[] { btn32, btn31 });

		Menu menu = new Menu();
		menu.setButton(new BaseButton[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = ValueUtil.WEIXIN_APPID;
		// 第三方用户唯一凭证密钥
		String appSecret = ValueUtil.WEIXIN_APPSECRET;

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
