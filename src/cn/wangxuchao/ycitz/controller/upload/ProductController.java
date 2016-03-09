package cn.wangxuchao.ycitz.controller.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.wangxuchao.ycitz.model.upload.Product;

@Controller
public class ProductController {

	private static final Log logger = LogFactory
			.getLog(ProductController.class);
	
	@RequestMapping(value="/product_input")
	public ModelAndView inputProduct(){
		return new ModelAndView("upload/ProductForm","product",new Product());
	}
	
	@RequestMapping(value="/product_save")
	public ModelAndView saveProduct(HttpServletRequest servletRequest,@ModelAttribute Product product){
		List<MultipartFile> files = product.getImages();

        List<String> fileNames = new ArrayList<String>();

        if (null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {

                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);

                File imageFile = new File(servletRequest.getServletContext()
                        .getRealPath("/upload"), fileName);
                try {
                    multipartFile.transferTo(imageFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

		return new ModelAndView("upload/ProductDetails","product",product);
	}
}
