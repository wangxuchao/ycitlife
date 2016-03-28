package cn.wangxuchao.ycitz.controller.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.wangxuchao.ycitz.model.weixin.oauth.SNSUserInfo;
import cn.wangxuchao.ycitz.model.weixin.oauth.WeixinOauth2Token;
import cn.wangxuchao.ycitz.util.AdvancedUtil;
import cn.wangxuchao.ycitz.util.ValueUtil;

@Controller
@RequestMapping("oauth")
public class OAuthController {
	@RequestMapping(value = "/oauth", method = RequestMethod.GET)
	public String getOAuth(String code, RedirectAttributes redir) {
		// 用户同意授权
		if (code == null || !"authdeny".equals(code)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil
					.getOauth2AccessToken(ValueUtil.WEIXIN_APPID,
							ValueUtil.WEIXIN_APPSECRET, code);
			// 网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			// access_token接口调用凭证超时时间，单位（秒）
			int expiresIn = weixinOauth2Token.getExpiresIn();
			// 用户标识
			String openId = weixinOauth2Token.getOpenId();
			// 获取用户信息
			SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken,
					openId);

			// 设置要传递的参数
			redir.addFlashAttribute("snsUserInfo", snsUserInfo);
			redir.addFlashAttribute("expiresIn", expiresIn);

			return "redirect:/oauth/userinfo";
		} else {
			redir.addFlashAttribute("msg_icon_class", "weui_icon_warn");
			redir.addFlashAttribute("msg_title", "必须登录才行！");
			redir.addFlashAttribute("msg_desc", "");
			return "redirect:/error";
		}
	}

	@RequestMapping(value = "/userinfo", method = RequestMethod.GET)
	public String getUserinfo() {
		return "oauth/snsuserinfo";
	}
}
