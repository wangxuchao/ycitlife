package cn.wangxuchao.ycitz.service;

public interface FaceService {
	/**
	 * 提供给外部调用的人脸检测方法
	 *
	 * @param picUrl
	 *            待检测图片的访问地址
	 * @return String
	 */
	public String detect(String picUrl);
}
