package com.controller;

import com.order.controller.ProductController;
import com.order.exception.InvalidStockNumberException;
import com.order.exception.ProductNotFoundException;
import com.order.model.ProductDto;
import com.order.repository.ProductRepository;
import com.order.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAsync
@Transactional
public class ProductControllerTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductController productController;
    @Autowired
    ProductService productService;

    @Test
    public void check_create_product_function() {
        ProductDto productDto = new ProductDto();
        productDto.setId(3L);
        productDto.setQuantity(10);
        productDto.setPrice(BigDecimal.valueOf(200));
        productDto.setDescription("film");
        productDto.setName("Interstellar");

        ResponseEntity<?> responseEntity = productController.createProduct(productDto);
        Assert.assertEquals(responseEntity.getStatusCode(), (HttpStatus.CREATED));
    }

    @Test(expected = InvalidStockNumberException.class)
    public void check_create_product_function_with_invalid_quantity_number() {
        ProductDto productDto = new ProductDto();
        productDto.setId(2L);
        productDto.setQuantity(-10);
        productDto.setPrice(BigDecimal.valueOf(20));
        productDto.setDescription("book");
        productDto.setName("Sherlock");

        ResponseEntity<?> responseEntity = productController.createProduct(productDto);
        Assert.assertEquals(responseEntity, is(HttpStatus.OK));
    }

    @Test
    public void check_create_product_function_with_valid_quantity_number() {
        ProductDto productDto = new ProductDto();
        productDto.setId(3L);
        productDto.setQuantity(10);
        productDto.setPrice(BigDecimal.valueOf(200));
        productDto.setDescription("film");
        productDto.setName("Interstellar");

        ResponseEntity<?> responseEntity = productController.createProduct(productDto);
        Assert.assertEquals(responseEntity.getStatusCode(), (HttpStatus.CREATED));
    }

    @Test
    public void check_delete_product_function_with_valid_product_id() {
        ResponseEntity<?> responseEntity = productController.deleteProduct(Long.valueOf(1002));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.ACCEPTED));
    }

    @Test(expected = ProductNotFoundException.class)
    public void check_delete_product_function_with_invalid_product_id() {
        ResponseEntity<?> responseEntity = productController.deleteProduct(Long.valueOf(8L));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void check_find_all_product_function() {
        ResponseEntity<?> responseEntity = productController.showAllProducts();
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void check_product_find_by_valid_id() {
        ResponseEntity<?> responseEntity = productController.findProductByID(Long.valueOf(1001));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }

    @Test(expected = ProductNotFoundException.class)
    public void check_product_find_by_invalid_id() {
        ResponseEntity<?> responseEntity = productController.findProductByID(Long.valueOf(8L));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test
    public void check_update_product_function_with_valid_id() {
        ProductDto productDto = new ProductDto();
        productDto.setId(10L);
        productDto.setQuantity(10);
        productDto.setPrice(BigDecimal.valueOf(20));
        productDto.setDescription("book");
        productDto.setName("Sherlock Holmes");

        ResponseEntity<?> responseEntity = productController.updateProduct(1003, productDto);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.ACCEPTED));
    }

    @Test(expected = ProductNotFoundException.class)
    public void check_update_product_function_with_invalid_id() {
        ProductDto productDto = new ProductDto();
        productDto.setId(10L);
        productDto.setQuantity(10);
        productDto.setPrice(BigDecimal.valueOf(20));
        productDto.setDescription("book");
        productDto.setName("Sherlock Holmes");

        ResponseEntity<?> responseEntity = productController.updateProduct(8L, productDto);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Test(expected = InvalidStockNumberException.class)
    public void check_product_should_not_update_for_invalid_stock_number() {
        ProductDto productDto = new ProductDto();
        productDto.setId(2L);
        productDto.setQuantity(-3);
        productDto.setPrice(BigDecimal.valueOf(20));
        productDto.setDescription("book");
        productDto.setName("Sherlock Holmes");

        ResponseEntity<?> responseEntity = productController.updateProduct(1001, productDto);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }


}