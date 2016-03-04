package cn.wangxuchao.ycitz.service.baidumap;

import it.sauronsoftware.base64.Base64;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import cn.wangxuchao.ycitz.model.baidumap.BaiduPlace;
import cn.wangxuchao.ycitz.model.baidumap.UserLocation;
import cn.wangxuchao.ycitz.model.weixin.message.response.Article;
import cn.wangxuchao.ycitz.util.HttpClientUtil;
import cn.wangxuchao.ycitz.util.ValueUtil;

@Service
public class BaiduMapServiceImpl implements BaiduMapService {
	private static final Log logger = LogFactory
			.getLog(BaiduMapServiceImpl.class);

	@Override
	public List<BaiduPlace> searchPlace(String query, String lng, String lat)
			throws Exception {
		// 拼装请求地址
		String requestUrl = "http://api.map.baidu.com/place/v2/search?&query=QUERY&location=LAT,LNG&radius=2000&output=xml&scope=2&page_size=10&page_num=0&ak=AK";
		requestUrl = requestUrl.replace("QUERY",
				URLEncoder.encode(query, "UTF-8"));
		requestUrl = requestUrl.replace("LAT", lat);
		requestUrl = requestUrl.replace("LNG", lng);
		requestUrl = requestUrl
				.replace("AK", ValueUtil.BAIDU_MAP_API_SERVER_AK);
		logger.info("调用Place API圆形区域检索");
		// 调用Place API圆形区域检索
		String respXml = HttpClientUtil.httpGet(requestUrl);
		// 解析返回的xml
		List<BaiduPlace> placeList = parsePlaceXml(respXml);
		return placeList;
	}

	@Override
	public List<BaiduPlace> parsePlaceXml(String xml) {
		List<BaiduPlace> placeList = null;
		try {
			Document document = DocumentHelper.parseText(xml);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 从根元素获取<results>
			Element resultsElement = root.element("results");
			// 从<results>中获取<result>集合
			@SuppressWarnings("unchecked")
			List<Element> resultElementList = resultsElement.elements("result");
			// 判断<result>集合的大小
			if (resultElementList.size() > 0) {
				placeList = new ArrayList<BaiduPlace>();
				// POI名称
				Element nameElement = null;
				// POI地址信息
				Element addressElement = null;
				// POI经纬度坐标
				Element locationElement = null;
				// POI电话信息
				Element telephoneElement = null;
				// POI扩展信息
				Element detailInfoElement = null;
				// 距离中心点的距离
				Element distanceElement = null;
				// 遍历<result>集合
				for (Element resultElement : resultElementList) {
					nameElement = resultElement.element("name");
					addressElement = resultElement.element("address");
					locationElement = resultElement.element("location");
					telephoneElement = resultElement.element("telephone");
					detailInfoElement = resultElement.element("detail_info");

					BaiduPlace place = new BaiduPlace();
					place.setName(nameElement.getText());
					place.setAddress(addressElement.getText());
					place.setLng(locationElement.element("lng").getText());
					place.setLat(locationElement.element("lat").getText());
					// 当<telephone>元素存在时获取电话号码
					if (null != telephoneElement) {
						place.setTelephone(telephoneElement.getText());
					}
					// 当<detail_info>元素存在时获取<distance>
					if (null != detailInfoElement) {
						distanceElement = detailInfoElement.element("distance");
						if (null != distanceElement) {
							place.setDistance(Integer.parseInt(distanceElement
									.getText()));
						}
					}
					placeList.add(place);
				}
				// 按距离由近及远排序
				Collections.sort(placeList);
			}
		} catch (Exception e) {
			logger.info("转换时出现异常：" + e.getLocalizedMessage());
		}
		return placeList;
	}

	@Override
	public List<Article> makeArticleList(List<BaiduPlace> placeList,
			String bd09Lng, String bd09Lat) {
		// 项目的根路径
		String basePath = "http://120.26.114.9/ycitz/";
		List<Article> list = new ArrayList<Article>();
		BaiduPlace place = null;
		logger.info("组装地点的图文消息");
		for (int i = 0; i < placeList.size(); i++) {
			place = placeList.get(i);
			Article article = new Article();
			article.setTitle(place.getName() + "\n距离约" + place.getDistance()
					+ "米");
			// P1表示用户发送的位置（坐标转换后），p2表示当前POI所在位置
			article.setUrl(String.format(basePath
					+ "map/route?p1=%s,%s&p2=%s,%s", bd09Lng, bd09Lat,
					place.getLng(), place.getLat()));
			// 将首条图文的图片设置为大图
			if (i == 0) {
				article.setPicUrl(basePath + "images/map_head.jpg");
			} else {
				article.setPicUrl(basePath + "images/map_point.png");
			}
			list.add(article);
		}
		return list;
	}

	@Override
	public UserLocation convertCoord(String lng, String lat) {
		// 百度坐标转换接口
		String convertUrl = "http://api.map.baidu.com/ag/coord/convert?from=2&to=4&x={x}&y={y}";
		convertUrl = convertUrl.replace("{x}", lng);
		convertUrl = convertUrl.replace("{y}", lat);

		UserLocation location = new UserLocation();
		try {
			String jsonCoord = HttpClientUtil.httpGet(convertUrl);
			JSONObject jsonObject = JSONObject.fromObject(jsonCoord);
			// 对转换后的坐标进行Base64解码
			location.setBd09Lng(Base64.decode(jsonObject.getString("x"),
					"UTF-8"));
			location.setBd09Lat(Base64.decode(jsonObject.getString("y"),
					"UTF-8"));
		} catch (Exception e) {
			location = null;
			logger.info("坐标转换失败");
		}
		return location;
	}

}
