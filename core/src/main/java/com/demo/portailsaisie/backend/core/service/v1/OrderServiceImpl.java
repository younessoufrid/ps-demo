package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.Order;
import com.demo.portailsaisie.backend.core.dto.OrderDto;
import com.demo.portailsaisie.backend.core.mapping.OrderMapper;
import com.demo.portailsaisie.backend.core.repository.OrderRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;


    @Override
    public OrderDto findById(Long id) {
        return mapper.map(
                repository.findById(id)
                        .orElseThrow()
        );
    }

    @Override
    public OrderDto findByNumber(String number) {
        return mapper.map(
                repository.findByNumber(number)
                        .orElseThrow()
        );
    }

    @Override
    public List<OrderDto> findByClientId(Long id) {
        return repository.findByClient_id(id)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<OrderDto> findByAffairId(Long id) {
        return repository.findByAffair_id(id)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<OrderDto> findByAffairIdAndClientId(Long affairId, Long clientId) {
        return repository.findByAffair_idAndClient_id(affairId, clientId)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public List<OrderDto> findByClientResponsibilityCenterReference(String reference) {
        return repository.findByClient_ResponsibilityCenters_reference(reference)
                .stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Order saveIfNotExist(Order order) {
        if (order.getNumber() != null) {
            Optional<Order> existingOrder = repository.findByNumber(order.getNumber());
            return existingOrder.orElseGet(() -> repository.save(order));
        }
        return save(order);
    }
}