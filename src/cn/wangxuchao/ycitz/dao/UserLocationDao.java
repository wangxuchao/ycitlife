package cn.wangxuchao.ycitz.dao;

import cn.wangxuchao.ycitz.model.UserLocation;

public interface UserLocationDao extends BaseDao<UserLocation> {
	public UserLocation getLastLocation(String openId);
}
