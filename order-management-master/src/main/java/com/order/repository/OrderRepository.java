package com.order.repository;

import com.order.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Product_Order Table Repository
 */
public interface OrderRepository extends JpaRepository<ProductOrder, String> {

}
