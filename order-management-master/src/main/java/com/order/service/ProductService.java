package com.order.service;

import com.order.entity.Product;
import com.order.exception.InvalidStockNumberException;
import com.order.exception.ProductNotFoundException;
import com.order.map.ProductMapper;
import com.order.model.ProductDto;
import com.order.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Product List
     *
     * @return List all products
     */
    public List<Product> showAllProducts() {

        return productRepository.findAll();
    }

    /**
     * Product with unique id
     *
     * @param id Product id
     * @return product with unique id
     */
    public Optional<Product> findProductById(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException("Invalid Product Id : " + id);
        }
        return productOptional;
    }

    /**
     * Saving Product
     *
     * @param product
     * @return product
     */
    public Product saveProduct(Product product) {
        if (product.getQuantity() < 0) {
            throw new InvalidStockNumberException("Stock number should be equals or greater than 0");
        } else
            return productRepository.save(product);
    }

    /**
     * Updating Product with unique id
     *
     * @param product
     * @param id       product id
     * @param stockNum product quantity
     * @return
     */
    public Product updateStock(ProductDto product, long id, long stockNum) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent())
            if (stockNum < 0)
                throw new InvalidStockNumberException("Stock number must be equal or greater than 0");
            else
                product.setQuantity(stockNum);
        return productRepository.save(productMapper.toProduct(product));

    }

    /**
     * Deleting product with unique id
     *
     * @param id Product id
     */
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
