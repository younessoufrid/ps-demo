package com.demo.portailsaisie.backend.core.dto.search;

import com.demo.portailsaisie.backend.core.dto.*;
import com.demo.portailsaisie.backend.core.dto.summary.SummaryDto;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface FilterResponseDto {

    List<SalesSiteDto> getSaleSites();
    List<ResponsibilityCenterDto> getResponsibilityCenters();
    List<ClientDto> getClients();
    List<AffairDto> getAffairs();
    List<OrderDto> getOrders();
    List<AOrderArticleOccurrenceDto> getOrderArticleOccurrences();
    List<SummaryDto> getSummary();
}
