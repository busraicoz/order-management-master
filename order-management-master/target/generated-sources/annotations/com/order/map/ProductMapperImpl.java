package com.order.map;

import com.order.entity.Product;
import com.order.model.ProductDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-04-08T22:39:49+0300",
    comments = "version: 1.3.0.Beta2, compiler: javac, environment: Java 1.8.0_112 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toProductDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId( product.getId() );
        productDto.setPrice( product.getPrice() );
        productDto.setName( product.getName() );
        productDto.setDescription( product.getDescription() );
        productDto.setQuantity( product.getQuantity() );

        return productDto;
    }

    @Override
    public List<ProductDto> toProductDTOs(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( products.size() );
        for ( Product product : products ) {
            list.add( toProductDTO( product ) );
        }

        return list;
    }

    @Override
    public Product toProduct(ProductDto productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productDTO.getId() );
        product.setDescription( productDTO.getDescription() );
        product.setName( productDTO.getName() );
        product.setPrice( productDTO.getPrice() );
        product.setQuantity( productDTO.getQuantity() );

        return product;
    }
}
