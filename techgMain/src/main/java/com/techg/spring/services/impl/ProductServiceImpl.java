package com.techg.spring.services.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techg.spring.exceptions.ResourceNotFoundException;
import com.techg.spring.model.Cart;
import com.techg.spring.model.CartProducts;
import com.techg.spring.model.Category;
import com.techg.spring.model.Certificate;
import com.techg.spring.model.Product;
import com.techg.spring.model.User;
import com.techg.spring.payload.ApiResponse;
import com.techg.spring.payload.CertificateDto;
import com.techg.spring.payload.PagnationResponse;
import com.techg.spring.payload.ProductDto;
import com.techg.spring.repositories.CartProdRepo;
import com.techg.spring.repositories.CartRepo;
import com.techg.spring.repositories.CategoryRepo;
import com.techg.spring.repositories.ProductRepo;
import com.techg.spring.repositories.UserRepo;
import com.techg.spring.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo ;
	
	@Autowired
	private CartRepo cartRepo ;
	
	@Autowired
	private CartProdRepo cartProdRepo ;
	
	@Autowired
	private CategoryRepo categoryRepo ;
	
	@Autowired
	private UserRepo userRepo ;
	
	
	@Autowired
	private ModelMapper mapper ; 
	@Override
	public Product createProduct(ProductDto productDto,Integer cid ) {
		  Product prod = mapper.map(productDto, Product.class); 
		  Category category = categoryRepo.findById(cid).orElseThrow(()-> new ResourceNotFoundException("category", "id" ,cid));
		  prod.setImageName("default.png");
		  prod.setCategory(category);
//		  prod.setQty(productDto.getQty());
		  Product save = productRepo.save(prod);
		  return save; 
	}                     

	@Override
	public ProductDto updateProduct(ProductDto productDto, Integer pid ,Integer cid) {
		
		Product product = productRepo.findById(pid).orElseThrow(()-> new ResourceNotFoundException("Product"," Id ",pid));
		Category category = categoryRepo.findById(cid).orElseThrow(()-> new ResourceNotFoundException("category", "id" ,cid));
		product.setDescription(productDto.getDescription());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());  
		product.setQty(productDto.getQty());
		product.setCategory(category);
		productRepo.save(product);
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public void deleteProduct(Integer pid) {
		Product cer = productRepo.findById(pid)
				.orElseThrow(()-> new ResourceNotFoundException("Product", "Product id", pid));
		productRepo.delete(cer);
	}

	@Override
	public ProductDto getCategory(Integer pid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ProductDto> getProducts() {
		// TODO Auto-generated method stub
		return null;
	}
		

	@Override
	public ApiResponse addToCart(Integer uid, Integer pid) {
		User user = userRepo.findById(uid).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",uid));
		Product product = productRepo.findById(pid).orElseThrow(()-> new ResourceNotFoundException("Product"," Id ",pid));
		Cart cartnew = null ;
		CartProducts cartProducts =null ;
		Integer price = product.getPrice();
		Optional<Cart> cart = cartRepo.findByUserId(uid);    
		if(cart.isPresent()) {    //if user is adding first time
			
			cartnew = cart.get();
			
			System.out.println("----aa----");
			Optional<CartProducts> cartProductOld = cartProdRepo.findCartProductByCartIdAndProductId(cartnew.getId(), pid);
			if(cartProductOld.isPresent()) {
			  System.out.println("----00----"+cartProductOld.get().getId());
			  return new ApiResponse("product is already in cart", false);
			}
			cartnew.setUser(user);
			cartnew.setTotalPrice(product.getPrice()+cartnew.getTotalPrice());
            Cart cartData = cartRepo.save(cartnew);
			System.out.println("----22----"+product.getId());
			
			cartProducts = new CartProducts();
			System.out.println("----bb----");
			cartProducts.setCart(cartData);
			System.out.println("----cc----");
			cartProducts.setProduct(product);
			System.out.println("----dd----");
			cartProducts.setStatus(1);
			cartProducts.setQty(1);
			cartProdRepo.save(cartProducts);
			
		}else {  //from second time
			
			cartnew = new Cart(); 
			cartnew.setUser(user);
			cartnew.setTotalPrice(product.getPrice());
			Cart cartData = cartRepo.save(cartnew);
			
			System.out.println("----11----"+product.getId());
			cartProducts = new CartProducts();
			cartProducts.setCart(cartData);
			cartProducts.setProduct(product);
			cartProducts.setStatus(1);
			cartProducts.setQty(1);
			cartProdRepo.save(cartProducts);
			
		}
		
		System.out.println("----66----");
		
		return new ApiResponse("product added to cart",true);
		
	}
	

	@Override
	public ProductDto updateProductWithImageName(ProductDto productDto, Integer pid) {
		Product product = productRepo.findById(pid).orElseThrow(()-> new ResourceNotFoundException("Product"," Id",pid));
		product.setImageName(productDto.getImageName());
		product.setDescription(productDto.getDescription());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());       
		Product newProd = productRepo.save(product) ;
		return mapper.map(newProd, ProductDto.class); 
	}

	@Override
	public ProductDto getProductById(Integer pId) {
		Product product = productRepo.findById(pId).orElseThrow(()-> new ResourceNotFoundException("Product"," Id",pId));
		return mapper.map(product, ProductDto.class);
	}

	
	@Override
	public ApiResponse removeFromCart(Integer uid, Integer pid) {
		
		User user = userRepo.findById(uid).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",uid));
		Product product = productRepo.findById(pid).orElseThrow(()-> new ResourceNotFoundException("Product"," Id ",pid));
		
		Optional<Cart> cart = cartRepo.findByUserId(uid);
		Cart cartnew = cart.get();
		
		Optional<CartProducts> cartProductOld = cartProdRepo.findCartProductByCartIdAndProductId(cartnew.getId(), pid);
		if(cartProductOld.isPresent()) {
		  System.out.println("----00----"+cartProductOld.get().getQty());
		  cartProdRepo.delete(cartProductOld.get());
		  List<CartProducts> cartProList = cartProdRepo.findCartProductByCartId(cartnew.getId());
		  System.out.println("-------q------");
		  if(cartProList.size()==0) {
			  System.out.println("-------r------");
			  cartRepo.delete(cartnew);
			  System.out.println("-------s------");
			  return new ApiResponse("product remove from cart",true);
		  }
		  cartnew.setTotalPrice(cartnew.getTotalPrice()-product.getPrice());
		  cartRepo.save(cartnew);
		  System.out.println("----11----"+cartnew.getTotalPrice()+product.getPrice());
		  return new ApiResponse("product remove from cart",true);
		}
		return new ApiResponse("product not removed from cart",false);
	}

	@Override
	public ApiResponse incProdQty(Integer uid, Integer pid) {
		
		User user = userRepo.findById(uid).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",uid));
		Product product = productRepo.findById(pid).orElseThrow(()-> new ResourceNotFoundException("Product"," Id ",pid));
		
		Optional<Cart> cart = cartRepo.findByUserId(uid);
		
		if(cart.isPresent()) {
		Cart cartnew = cart.get();
		Optional<CartProducts> cartProductOld = cartProdRepo.findCartProductByCartIdAndProductId(cartnew.getId(), pid);
		
		if(cartProductOld.isPresent()) {
			  System.out.println("----00----"+cartProductOld.get().getQty());
			  CartProducts cartProd = cartProductOld.get();
			  cartProd.setQty(cartProd.getQty()+1);
			  cartProdRepo.save(cartProd);
			  cartnew.setTotalPrice(cartnew.getTotalPrice()+product.getPrice());
			  cartRepo.save(cartnew);
			  System.out.println("----11----"+cartnew.getTotalPrice()+product.getPrice());
			  return new ApiResponse("qty inc by 1",true);
			}
		}
		return new ApiResponse("product not removed from cart",false);
	}

	@Override
	public ApiResponse decProdQty(Integer uid, Integer pid) {

		User user = userRepo.findById(uid).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",uid));
		Product product = productRepo.findById(pid).orElseThrow(()-> new ResourceNotFoundException("Product"," Id ",pid));
		
		Optional<Cart> cart = cartRepo.findByUserId(uid);
		if(cart.isPresent()) {
		Cart cartnew = cart.get();
		Optional<CartProducts> cartProductOld = cartProdRepo.findCartProductByCartIdAndProductId(cartnew.getId(), pid);
		
		if(cartProductOld.isPresent()) {
			  System.out.println("----00----"+cartProductOld.get().getQty());
			  CartProducts cartProd = cartProductOld.get();
			  
			  if(cartProd.getQty()==1) {
				  cartProdRepo.delete(cartProductOld.get());

				  System.out.println("-------p------");
				  List<CartProducts> cartProList = cartProdRepo.findCartProductByCartId(cartnew.getId());
				  System.out.println("-------q------");
				  if(cartProList.size()==0) {
					  System.out.println("-------r------");
					  cartRepo.delete(cartnew);
					  System.out.println("-------s------");
					  return new ApiResponse("product remove from cart",true);
				  }
				  cartnew.setTotalPrice(cartnew.getTotalPrice()-product.getPrice());
				  cartRepo.save(cartnew);
				  System.out.println("----11----"+cartnew.getTotalPrice()+product.getPrice());
				  return new ApiResponse("product remove from cart",true);
			  }
			  
			  cartProd.setQty(cartProd.getQty()-1);
			  cartProdRepo.save(cartProd);
			  cartnew.setTotalPrice(cartnew.getTotalPrice()-product.getPrice());
			  cartRepo.save(cartnew);
			  System.out.println("----0000----"+cartnew.getTotalPrice()+product.getPrice());
			  return new ApiResponse("qty dec by 1",true);
			}
		}
		return new ApiResponse("some error in cart",false);
	}

	@Override
	public PagnationResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		Sort sort=null;
		sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p = PageRequest.of(pageNumber, pageSize) ;
//		System.out.println("4555555555555555555555555555555555555555555555555544444444444");
		Page<Product> pageCer = productRepo.findAll(p);
//		System.out.println("4444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444");
		List<Product> productss = pageCer.getContent();
		List<ProductDto> prodDtos = productss.stream().map((item)-> mapper.map(item, ProductDto.class)).collect(Collectors.toList());
//		System.out.println("aaaaaaaaa5555");
		PagnationResponse paginationResponse = new PagnationResponse();
		paginationResponse.setContent(prodDtos);
		paginationResponse.setPageNumber(pageCer.getNumber());
		paginationResponse.setPageSize(pageCer.getSize());
		paginationResponse.setTotalElements(pageCer.getTotalElements());
		paginationResponse.setTotalPages(pageCer.getTotalPages());
		paginationResponse.setLastPage(pageCer.isLast());
		return paginationResponse;
	}

}
