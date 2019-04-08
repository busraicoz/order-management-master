package com.order.controller;

import com.order.entity.Product;
import com.order.map.ProductMapper;
import com.order.model.ProductDto;
import com.order.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    /**
     * @param id Product id
     * @return response status for show product with unique id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findProductByID(@PathVariable long id) {
        Optional<Product> productOptional = productService.findProductById(id);
        if (!productOptional.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productMapper.toProductDTO(productOptional.get()));
    }

    /**
     * Listing all products in Product Repository(Cacheable annotation used for performance improvement)
     *
     * @return response for listing products status
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> showAllProducts() {

        return ResponseEntity.ok(productMapper.toProductDTOs(productService.showAllProducts()));
    }

    /**
     * Creating new product
     *
     * @param product
     * @return response for creating product status
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) {
        productService.saveProduct(productMapper.toProduct(product));

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    /**
     * Updating product with product id
     *
     * @param id      Product id
     * @param product
     * @return response for listing updating status
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable long id, @RequestBody ProductDto product) {
        Optional<Product> productOptional = productService.findProductById(id);
        if (!productOptional.isPresent()) {
            ResponseEntity.notFound().build();
        }
        product.setId(id);
        productService.saveProduct(productMapper.toProduct(product));
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);
    }

    /**
     * Deleting product with unique id
     *
     * @param id Product id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findProductById(id);
        if (!productOptional.isPresent()) {
            ResponseEntity.notFound().build();
        }
        productService.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}