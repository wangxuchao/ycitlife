package cn.wangxuchao.ycitz.dao;

import java.util.List;

import cn.wangxuchao.ycitz.model.UserLocation;

public interface UserLocationDao {
	public void add(UserLocation userLocation);

	public void edit(UserLocation userLocation);

	public void delete(int id);

	public UserLocation getUserLocation(int id);

	public List<UserLocation> findAll();

	public UserLocation getLastLocation(String openId);
}
