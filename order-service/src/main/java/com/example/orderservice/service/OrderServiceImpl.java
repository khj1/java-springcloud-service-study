package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.utils.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDetails) {
        orderDetails.setOrderId(UUID.randomUUID().toString());

        OrderEntity orderEntity = mapper.mapDtoToEntity(orderDetails);
        OrderEntity savedEntity = orderRepository.save(orderEntity);

        return mapper.mapEntityToDto(savedEntity);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity findEntity = orderRepository.findByOrderId(orderId);

        return mapper.mapEntityToDto(findEntity);
    }

    @Override
    public List<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
