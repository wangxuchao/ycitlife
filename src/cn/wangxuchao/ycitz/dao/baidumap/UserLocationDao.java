package cn.wangxuchao.ycitz.dao.baidumap;

import cn.wangxuchao.ycitz.dao.BaseDao;
import cn.wangxuchao.ycitz.model.baidumap.UserLocation;

public interface UserLocationDao extends BaseDao<UserLocation> {
	public UserLocation getLastLocation(String openId);
}
