package com.order.map;

import com.order.entity.ProductOrder;
import com.order.model.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Order Mapper interface
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toOrderDTO(ProductOrder order);

    List<OrderDto> toOrderDTOs(List<ProductOrder> orders);

    ProductOrder toOrder(OrderDto orderDTO);
}
