package com.techg.spring.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.techg.spring.config.AppConstants;
import com.techg.spring.model.Category;
import com.techg.spring.model.Product;
import com.techg.spring.payload.ApiResponse;
import com.techg.spring.payload.CategoryDto;
import com.techg.spring.payload.OptionValue;
import com.techg.spring.payload.PagnationResponse;
import com.techg.spring.payload.ProductDto;
import com.techg.spring.services.CategoryService;
import com.techg.spring.services.FileService;
import com.techg.spring.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RequestMapping("techg/api/")
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@GetMapping("/auth/getAllProduct")
	public ResponseEntity<Map<?, ?>> getAllCertificates(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

		PagnationResponse posts = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
		Map<String, Object> map = new HashMap<>();
		map.put("page", posts);
		List<CategoryDto> categories = categoryService.getCategories();
		List<OptionValue> optionValues = categories.stream()
				.map(category -> new OptionValue(category.getCategoryTitle(), category.getCategoryId()))
				.collect(Collectors.toList());
		map.put("cat", optionValues);
		return new ResponseEntity<Map<?, ?>>(map, HttpStatus.OK);
	}

	@PostMapping("admin/createProduct")
	public ResponseEntity<Map<String, Integer>> cretaeProduct(@RequestBody Map<String, Object> inputData) {

		ProductDto dto = objectMapper.convertValue(inputData.get("dto"), ProductDto.class);
		Integer cid = (Integer) inputData.get("cid");
		
		Product productDto2 = productService.createProduct(dto, cid);
		System.out.println("--------done-----");
		Map<String, Integer> resp = new HashMap<>();
		resp.put("status", 1);
		resp.put("pid", productDto2.getId());
		return new ResponseEntity<Map<String, Integer>>(resp, HttpStatus.CREATED);

	}

//      post image upload
//		@PostMapping("admin/uploadProdImage")
//		public ResponseEntity<ProductDto> uploadImage(@RequestBody Map<String, Object> inputData ) throws IOException{
//		Integer pid = (Integer) inputData.get("pid");
//		Object image = inputData.get("image");
//		System.out.println("------inside------");
//		ProductDto productDto = productService.getProductById(pid);
//		String fileName = fileService.uploadImage(path, (MultipartFile) image); 
//		productDto.setImageName(fileName);
//		ProductDto updatePost = productService.updateProductWithImageName(productDto, pid);
//		return new ResponseEntity<ProductDto>(updatePost,HttpStatus.OK);
//		}

	@PostMapping("admin/uploadProdImage")
	public ResponseEntity<ProductDto> uploadImage(@RequestParam("pid") Integer pid,
			@RequestPart("image") MultipartFile image) throws IOException {

		System.out.println("------inside------");
		ProductDto productDto = productService.getProductById(pid);
		String fileName = fileService.uploadImage(path, image);
		System.out.println("------inside2------" + fileName);
		productDto.setImageName(fileName);
		ProductDto updatePost = productService.updateProductWithImageName(productDto, pid);
		System.out.println("------inside3------");
		return new ResponseEntity<ProductDto>(updatePost, HttpStatus.OK);
	}

	@PostMapping("admin/updateProduct")
	public ResponseEntity<ApiResponse> updateProduct(@Valid @RequestBody ProductDto productDto,
			@RequestParam(name = "pid") Integer pid, @RequestParam(name = "cid") Integer cid) {

		ProductDto productDto2 = productService.updateProduct(productDto, pid, cid);

		return new ResponseEntity<ApiResponse>(new ApiResponse("user updated succesfully", 1), HttpStatus.CREATED);
	}

	@PostMapping("/admin/deleteProduct")
	public ResponseEntity<ApiResponse> deleteCertificateById(@RequestBody Map<String, Object> inputData) {
		Integer pid = (Integer) inputData.get("id");
		System.out.println("aaaaa" + pid);
		productService.deleteProduct(pid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Product deleted successfully", 1), HttpStatus.CREATED);
	}

	@PostMapping("post/addtocart")
	public ResponseEntity<ApiResponse> addToCart(@RequestParam(name = "uid") Integer uid,
			@RequestParam(name = "pid") Integer pid) {

		ApiResponse productDto2 = productService.addToCart(uid, pid);
		return new ResponseEntity<ApiResponse>(productDto2, HttpStatus.CREATED);

	}

	@PostMapping("post/removefromcart")
	public ResponseEntity<ApiResponse> removeFromCart(@RequestParam(name = "uid") Integer uid,
			@RequestParam(name = "pid") Integer pid) {

		ApiResponse productDto2 = productService.removeFromCart(uid, pid);
		return new ResponseEntity<ApiResponse>(productDto2, HttpStatus.CREATED);

	}

	@PostMapping("post/incprodqty")
	public ResponseEntity<ApiResponse> incProdQty(@RequestParam(name = "uid") Integer uid,
			@RequestParam(name = "pid") Integer pid) {

		ApiResponse productDto2 = productService.incProdQty(uid, pid);
		return new ResponseEntity<ApiResponse>(productDto2, HttpStatus.CREATED);

	}

	@PostMapping("post/decprodqty")
	public ResponseEntity<ApiResponse> decProdQty(@RequestParam(name = "uid") Integer uid,
			@RequestParam(name = "pid") Integer pid) {

		ApiResponse productDto2 = productService.decProdQty(uid, pid);
		return new ResponseEntity<ApiResponse>(productDto2, HttpStatus.CREATED);

	}

	@PostMapping(value = "auth/getproductImage", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<Map<String, Object>> downloadImage(@RequestBody Map<String, Object> inputData, HttpServletResponse response)
			throws IOException {
		String imageName = (String) inputData.get("image");
        InputStream resource = fileService.getResource(path, imageName);

        // Convert InputStream to byte array
        byte[] imageBytes = IOUtils.toByteArray(resource);

        // Convert byte array to Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Create a response map
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("image", base64Image);

        // Set response headers and status
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(responseMap, headers, HttpStatus.OK);
	}

//	@GetMapping(value = "auth/getproductImage", produces = MediaType.IMAGE_JPEG_VALUE)
//	public void downloadImage(@RequestParam("imageName") String imageName ,HttpServletResponse response) throws IOException {
//		InputStream resource = fileService.getResource(path, imageName);
//		response.setContentType(MediaType.IMAGE_JPEG_VALUE);	
//		StreamUtils.copy(resource ,response.getOutputStream());
//	}

}
