package com.demo.portailsaisie.backend.core.service.interfaces;

import com.demo.portailsaisie.backend.core.domain.Client;
import com.demo.portailsaisie.backend.core.domain.ResponsibilityCenter;
import com.demo.portailsaisie.backend.core.domain.SalesSite;
import com.demo.portailsaisie.backend.core.dto.ResponsibilityCenterDto;

import java.util.List;

public interface ResponsibilityCenterService extends GenericService<ResponsibilityCenter> {

    ResponsibilityCenterDto findById(Long id);

    ResponsibilityCenterDto findByReference(String reference);

    ResponsibilityCenter addSalesSite(ResponsibilityCenter responsibilityCenter, SalesSite salesSite);

    ResponsibilityCenter addClient(ResponsibilityCenter responsibilityCenter, Client client);
    List<ResponsibilityCenterDto> findAll();
    List<ResponsibilityCenterDto> findBySaleSiteRef(String saleSiteRef);
}
