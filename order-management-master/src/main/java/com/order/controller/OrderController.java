package com.order.controller;

import com.order.entity.Product;
import com.order.entity.ProductOrder;
import com.order.exception.NotEnoughStockException;
import com.order.map.OrderMapper;
import com.order.map.ProductMapper;
import com.order.model.OrderDto;
import com.order.service.OrderService;
import com.order.service.PaymentService;
import com.order.service.ProductService;
import com.iyzipay.model.Payment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Order Controller class which include show orders' list function,
 * create order function and update order function
 */
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    private final ProductService productRepository;

    private final PaymentService paymentService;

    private final OrderMapper orderMapper;

    private final ProductMapper productMapper;

    public OrderController(OrderService orderService, ProductService productRepository, PaymentService paymentService, OrderMapper orderMapper, ProductMapper productMapper1) {
        this.orderService = orderService;
        this.productRepository = productRepository;
        this.paymentService = paymentService;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper1;
    }

    /**
     * Listing all orders in Order Repository
     *
     * @return response for listing status
     */
    @GetMapping
    public ResponseEntity<List<OrderDto>> showAllOrders() {
        return ResponseEntity.ok(orderService.showAllOrders());
    }

    /**
     * Order creater from OrderDTO
     *
     * @param orderDTO order's document type object
     * @return response for creating status
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDTO) {
        Optional<Product> optionalProduct = productRepository.findProductById(orderDTO.getProductId());
        if (!optionalProduct.isPresent())
            return ResponseEntity.notFound().build();
        Product product = optionalProduct.get();
        long productID = orderDTO.getProductId();
        long stock = product.getQuantity();
        BigDecimal unitPrice = product.getPrice();
        long orderAmount = orderDTO.getOrderAmount();
        long sellAmount = stock - orderAmount;
        BigDecimal orderPrice = unitPrice.multiply(BigDecimal.valueOf(orderAmount));
        if (orderAmount > stock) {
            throw new NotEnoughStockException("Stock not enough for " + orderAmount);
        }
        Payment payment = paymentService.createPaymentRequest(orderDTO, orderPrice);
        if (payment.getStatus().equals("success")) {
            productRepository.updateStock(productMapper.toProductDTO(product), productID, sellAmount);
             orderDTO.setPaymentId(payment.getPaymentId());
             orderService.createOrder(orderDTO);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Updating Order parameters
     *
     * @param id       Order payment id
     * @param orderDto
     * @return response for updating status
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable String id, @RequestBody OrderDto orderDto) {
        Optional<Product> optionalProduct = productRepository.findProductById(orderDto.getProductId());
        if (!optionalProduct.isPresent())
            return ResponseEntity.notFound().build();
        ProductOrder order = orderMapper.toOrder(orderDto);
        order.setPaymentId(id);
        orderService.createOrder(orderMapper.toOrderDTO(order));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
