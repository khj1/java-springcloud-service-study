package com.example.orderservice.utils;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper mapper;

    public ResponseOrder mapEntityToResponse(OrderEntity entity) {
        return mapper.map(entity, ResponseOrder.class);
    }

    public OrderEntity mapDtoToEntity(OrderDto dto) {
        return mapper.map(dto, OrderEntity.class);
    }

    public OrderDto mapEntityToDto(OrderEntity entity) {
        return mapper.map(entity, OrderDto.class);
    }

    public OrderDto mapRequestToDto(RequestOrder request, String userId) {
        OrderDto orderDto = mapper.map(request, OrderDto.class);
        orderDto.setUserId(userId);
        orderDto.setTotalPrice(orderDto.getUnitPrice() * orderDto.getQty());

        return orderDto;
    }

    public ResponseOrder mapDtoToResponse(OrderDto dto) {
        return mapper.map(dto, ResponseOrder.class);
    }
}
