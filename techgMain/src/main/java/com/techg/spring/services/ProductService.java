package com.techg.spring.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.techg.spring.model.Product;
import com.techg.spring.payload.ApiResponse;
import com.techg.spring.payload.PagnationResponse;
import com.techg.spring.payload.ProductDto;


@Service                 
public interface ProductService  {             
 
    Product createProduct(ProductDto productDto ,Integer cid ); 
	
    ProductDto updateProduct(ProductDto productDto ,Integer pid ,Integer cid);
    
    ProductDto getProductById(Integer postId) ;
	
	void deleteProduct(Integer pid);             
	
	ProductDto getCategory(Integer pid);      
	
	List<ProductDto> getProducts();
	
	ApiResponse addToCart( Integer uid, Integer pid);
	
	ApiResponse removeFromCart( Integer uid, Integer pid);
	
	ApiResponse incProdQty( Integer uid, Integer pid);
	
	ApiResponse decProdQty( Integer uid, Integer pid);
	
	ProductDto updateProductWithImageName(ProductDto productDto, Integer pid);

	PagnationResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
