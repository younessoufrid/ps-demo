package com.demo.portailsaisie.backend.core.service.interfaces;

import com.demo.portailsaisie.backend.core.domain.SalesSite;
import com.demo.portailsaisie.backend.core.dto.SalesSiteDto;

import java.util.List;

public interface SalesSiteService extends GenericService<SalesSite> {

    SalesSiteDto findById(Long id);
    SalesSiteDto findByReference(String reference);
    List<SalesSiteDto> findAll();
}