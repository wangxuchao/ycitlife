package cn.wangxuchao.ycitz.model.upload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Product {

	private String name;
	private String description;
	private Float price;
	private List<MultipartFile> images;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public List<MultipartFile> getImages() {
		return images;
	}

	public void setImages(List<MultipartFile> images) {
		this.images = images;
	}

}
