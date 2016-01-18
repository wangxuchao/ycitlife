package cn.wangxuchao.ycitz.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户地理位置model
 *
 * @author 王绪超
 */
@Entity
@Table(name = "userlocation")
public class UserLocation {

	@Id
	@GeneratedValue
	private long id;
	@Column(name = "openId", length = 50, nullable = false)
	private String openId;
	@Column(name = "lng", length = 50, nullable = false)
	private String lng;
	@Column(name = "lat", length = 50, nullable = false)
	private String lat;
	@Column(name = "bd09Lng", length = 30, nullable = true)
	private String bd09Lng;
	@Column(name = "bd09Lat", length = 30, nullable = true)
	private String bd09Lat;

	public UserLocation() {
	}

	public UserLocation(long id, String openId, String lng, String lat,
			String bd09Lng, String bd09Lat) {
		super();
		this.id = id;
		this.openId = openId;
		this.lng = lng;
		this.lat = lat;
		this.bd09Lng = bd09Lng;
		this.bd09Lat = bd09Lat;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getBd09Lng() {
		return bd09Lng;
	}

	public void setBd09Lng(String bd09Lng) {
		this.bd09Lng = bd09Lng;
	}

	public String getBd09Lat() {
		return bd09Lat;
	}

	public void setBd09Lat(String bd09Lat) {
		this.bd09Lat = bd09Lat;
	}

}
