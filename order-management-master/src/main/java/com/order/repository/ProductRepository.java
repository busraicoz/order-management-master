package com.order.repository;

import com.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Product table repository
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}