package cn.wangxuchao.ycitz.service.baidumap;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.wangxuchao.ycitz.model.baidumap.BaiduPlace;
import cn.wangxuchao.ycitz.model.baidumap.UserLocation;
import cn.wangxuchao.ycitz.model.weixin.message.response.Article;

public interface BaiduMapService {
	/**
	 * 圆形区域检索
	 *
	 * @param query
	 *            检索关键词
	 * @param lng
	 *            经度
	 * @param lat
	 *            纬度
	 * @return List<BaiduPlace>
	 * @throws UnsupportedEncodingException
	 */
	public List<BaiduPlace> searchPlace(String query, String lng, String lat)
			throws Exception;

	/**
	 * 根据百度地图返回的流解析出地址信息
	 *
	 * @param inputStream
	 *            输入流
	 * @return List<BaiduPlace>
	 */
	public List<BaiduPlace> parsePlaceXml(String xml);

	/**
	 * 根据Place组装图文列表
	 *
	 * @param placeList
	 * @param bd09Lng
	 *            经度
	 * @param bd09Lat
	 *            纬度
	 * @return List<Article>
	 */
	public List<Article> makeArticleList(List<BaiduPlace> placeList,
			String bd09Lng, String bd09Lat);

	/**
	 * 将微信定位的坐标转换成百度坐标（GCJ-02 -> Baidu）
	 *
	 * @param lng
	 *            经度
	 * @param lat
	 *            纬度
	 * @return UserLocation
	 */
	public UserLocation convertCoord(String lng, String lat);
}
