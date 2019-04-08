package com.order.map;

import com.order.entity.ProductOrder;
import com.order.model.OrderDto;
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
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toOrderDTO(ProductOrder order) {
        if ( order == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        orderDto.setPaymentId( order.getPaymentId() );
        orderDto.setOrderAmount( order.getOrderAmount() );
        orderDto.setProductId( order.getProductId() );

        return orderDto;
    }

    @Override
    public List<OrderDto> toOrderDTOs(List<ProductOrder> orders) {
        if ( orders == null ) {
            return null;
        }

        List<OrderDto> list = new ArrayList<OrderDto>( orders.size() );
        for ( ProductOrder productOrder : orders ) {
            list.add( toOrderDTO( productOrder ) );
        }

        return list;
    }

    @Override
    public ProductOrder toOrder(OrderDto orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        ProductOrder productOrder = new ProductOrder();

        productOrder.setPaymentId( orderDTO.getPaymentId() );
        productOrder.setOrderAmount( orderDTO.getOrderAmount() );
        productOrder.setProductId( orderDTO.getProductId() );

        return productOrder;
    }
}
