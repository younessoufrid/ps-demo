package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.dto.*;
import com.demo.portailsaisie.backend.core.service.interfaces.*;
import com.demo.portailsaisie.backend.core.dto.*;
import com.demo.portailsaisie.backend.core.dto.search.FilterRequestDto;
import com.demo.portailsaisie.backend.core.dto.search.FilterResponseDto;
import com.demo.portailsaisie.backend.core.dto.summary.SummaryDto;
import com.demo.portailsaisie.backend.core.enums.Action;
import com.demo.portailsaisie.backend.core.service.interfaces.*;
import com.demo.portailsaisie.backend.core.dto.search.ImmutableFilterResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {

    private final SalesSiteService salesSiteService;
    private final ResponsibilityCenterService responsibilityCenterService;
    private final ClientService clientService;
    private final AffairService affairService;
    private final OrderService orderService;
    private final ArticleOccurrenceService articleOccurrenceService;

    @Override
    public FilterResponseDto filter(FilterRequestDto dto) {
        ImmutableFilterResponseDto.Builder builder = ImmutableFilterResponseDto.builder();
        List<SalesSiteDto> salesSites = new ArrayList<>();
        List<ResponsibilityCenterDto> responsibilityCenters = new ArrayList<>();
        List<ClientDto> clients = new ArrayList<>();
        List<AffairDto> affairs = new ArrayList<>();
        List<OrderDto> orders = new ArrayList<>();
        List<AOrderArticleOccurrenceDto> orderArticleOccurrences = new ArrayList<>();
        List<SummaryDto> orderArticleOccurrencesSummary = new ArrayList<>();

        if (dto.getSaleSiteRef() == null) {
            salesSites = salesSiteService.findAll();
        } else {
            salesSiteService.findByReference(dto.getSaleSiteRef()); // pour valider l'existence de site de vente avant de poursuivre
            salesSites = salesSiteService.findAll();
            responsibilityCenters = responsibilityCenterService.findBySaleSiteRef(dto.getSaleSiteRef());
        }

        if (dto.getResponsibilityCenterRef() != null) {
            clients = clientService.findByResponsibilityCenterReference(dto.getResponsibilityCenterRef());
            affairs = affairService.findByClientResponsibilityCenterReference(dto.getResponsibilityCenterRef());
            orders = orderService.findByClientResponsibilityCenterReference(dto.getResponsibilityCenterRef());
        }

        if (dto.getClientId() != null && dto.getAffairId() != null && dto.getOrderId() != null) {
            clients = Collections.singletonList(clientService.findById(dto.getClientId()));
            affairs = Collections.singletonList(affairService.findById(dto.getAffairId()));
            orders = Collections.singletonList(orderService.findById(dto.getOrderId()));
        } else if (dto.getClientId() != null && dto.getAffairId() != null) {
            clients = Collections.singletonList(clientService.findById(dto.getClientId()));
            affairs = Collections.singletonList(affairService.findById(dto.getAffairId()));
            orders = orderService.findByAffairIdAndClientId(dto.getAffairId(), dto.getClientId());
        } else if (dto.getClientId() != null && dto.getOrderId() != null) {
            clients = Collections.singletonList(clientService.findById(dto.getClientId()));
            orders = Collections.singletonList(orderService.findById(dto.getOrderId()));
            affairs = affairService.findByOrderId(dto.getOrderId());
        } else if (dto.getAffairId() != null && dto.getOrderId() != null) {
            affairs = Collections.singletonList(affairService.findById(dto.getAffairId()));
            orders = Collections.singletonList(orderService.findById(dto.getOrderId()));
            clients = clientService.findByOrderId(dto.getOrderId());
        } else if (dto.getClientId() != null) {
            clients = Collections.singletonList(clientService.findById(dto.getClientId()));
            affairs = affairService.findByClientId(dto.getClientId());
            List<OrderDto> finalOrders = new ArrayList<>();
            affairs.forEach(affair -> {
                finalOrders.addAll(orderService.findByAffairIdAndClientId(affair.getId(), dto.getClientId()));
            });
            orders = finalOrders;
        } else if (dto.getAffairId() != null) {
            affairs = Collections.singletonList(affairService.findById(dto.getAffairId()));
            clients = clientService.findByAffairId(dto.getAffairId());

            List<OrderDto> finalOrders = new ArrayList<>();
            clients.forEach(client -> {
                finalOrders.addAll(orderService.findByAffairIdAndClientId(dto.getAffairId(), client.getId()));
            });
            orders = finalOrders;
        } else if (dto.getOrderId() != null) {
            orders = Collections.singletonList(orderService.findById(dto.getOrderId()));
            clients = clientService.findByOrderId(dto.getOrderId());
            affairs = affairService.findByOrderId(dto.getOrderId());
        }

        if (orders.size() == 1) {
            if (dto.getAction() == Action.SAISIE) {
                orderArticleOccurrences = this.articleOccurrenceService.getOrDuplicateInitialLinesThenGetAOrderArticleOccurrenceDto(orders.get(0).getId(), dto.getDate());
            } else if (dto.getAction() == Action.SUMMARY){
                orderArticleOccurrencesSummary = this.articleOccurrenceService.getSummaryOrder(orders.get(0).getId(), dto.getDate());
            }
        }

        return builder.saleSites(salesSites)
                .responsibilityCenters(responsibilityCenters)
                .clients(clients)
                .affairs(affairs)
                .orders(orders)
                .orderArticleOccurrences(orderArticleOccurrences)
                .summary(orderArticleOccurrencesSummary)
                .build();
    }
}
