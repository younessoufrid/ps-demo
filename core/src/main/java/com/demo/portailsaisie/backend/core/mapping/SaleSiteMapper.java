package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.domain.SalesSite;
import com.demo.portailsaisie.backend.core.dto.SalesSiteDto;
import org.mapstruct.Mapper;

@Mapper
public interface SaleSiteMapper {
    SalesSiteDto map(SalesSite salesSite);
}
