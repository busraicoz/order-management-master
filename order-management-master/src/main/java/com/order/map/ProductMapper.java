package com.order.map;

import com.order.model.ProductDto;
import com.order.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Product Mapper Interface
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDTO(Product product);

    List<ProductDto> toProductDTOs(List<Product> products);

    Product toProduct(ProductDto productDTO);
}