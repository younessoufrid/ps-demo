package com.demo.portailsaisie.backend.core.service.interfaces;

import com.demo.portailsaisie.backend.core.domain.Order;
import com.demo.portailsaisie.backend.core.dto.OrderDto;

import java.util.List;

public interface OrderService extends GenericService<Order>{

    OrderDto findById(Long id);
    OrderDto findByNumber(String number);
    List<OrderDto> findByClientId(Long id);
    List<OrderDto> findByAffairId(Long id);
    List<OrderDto> findByAffairIdAndClientId(Long affairId, Long clientId);
    List<OrderDto> findByClientResponsibilityCenterReference(String reference);
}
