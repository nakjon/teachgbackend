package com.techg.spring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.techg.spring.model.CartProducts;


public interface CartProdRepo extends JpaRepository<CartProducts, Integer>{

	@Query("SELECT cp FROM CartProducts cp WHERE cp.cart.id = :cartId AND cp.product.id = :productId")
    Optional<CartProducts> findCartProductByCartIdAndProductId(Integer cartId,  Integer productId);
	
	@Query("SELECT cp FROM CartProducts cp WHERE cp.cart.id = :cartId")
	List<CartProducts> findCartProductByCartId(Integer cartId);
}
