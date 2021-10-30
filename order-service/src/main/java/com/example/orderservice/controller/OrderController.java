package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.utils.OrderMapper;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final Environment env;
    private final OrderMapper mapper;
    private final OrderService orderService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It`s working in Order Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@RequestBody RequestOrder requestOrder,
                                                     @PathVariable String userId) {
        OrderDto orderDetails = mapper.mapRequestToDto(requestOrder, userId);
        ResponseOrder response = mapper.mapDtoToResponse(orderService.createOrder(orderDetails));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrdersByUserId(@PathVariable String userId) {
        List<ResponseOrder> response = orderService.getOrdersByUserId(userId).stream()
                .map(mapper::mapEntityToResponse)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseOrder> selectUserById(@PathVariable String orderId) {
        ResponseOrder response = mapper.mapDtoToResponse(orderService.getOrderByOrderId(orderId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
