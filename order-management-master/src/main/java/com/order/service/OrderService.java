package com.order.service;

import com.order.repository.OrderRepository;
import com.order.map.OrderMapper;
import com.order.model.OrderDto;
import com.order.entity.ProductOrder;
import com.order.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private OrderDto orderDto;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, ProductService productService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    /**
     * @return All orders List
     */

    public List<OrderDto> showAllOrders() {
        return orderMapper.toOrderDTOs(orderRepository.findAll());
    }


    /**
     * @param orderDto
     * @return Creating new Order
     * @throws ProductNotFoundException
     */
    public OrderDto createOrder(OrderDto orderDto) throws ProductNotFoundException {
        ProductOrder order = new ProductOrder();
        order.setPaymentId(orderDto.getPaymentId());
        order.setOrderAmount(orderDto.getOrderAmount());
        order.setProductId(orderDto.getProductId());
        orderRepository.save(order);
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

}
