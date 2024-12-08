package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.domain.Order;
import com.demo.portailsaisie.backend.core.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    OrderDto map(Order order);
    Order map(OrderDto  orderDto);
}
