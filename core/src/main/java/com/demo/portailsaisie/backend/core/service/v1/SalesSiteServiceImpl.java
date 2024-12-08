package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.SalesSite;
import com.demo.portailsaisie.backend.core.dto.SalesSiteDto;
import com.demo.portailsaisie.backend.core.mapping.SaleSiteMapper;
import com.demo.portailsaisie.backend.core.repository.SalesSiteRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.SalesSiteService;
import com.demo.portailsaisie.backend.utils.exception.NotFoundException;
import com.demo.portailsaisie.backend.utils.exception.ProcessErrorEnum;
import com.demo.portailsaisie.backend.utils.exception.ProcessException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SalesSiteServiceImpl implements SalesSiteService {

    private final SalesSiteRepository repository;
    private final SaleSiteMapper mapper;

    @Override
    public SalesSite save(SalesSite salesSite) {
        return repository.save(salesSite);
    }

    @Override
    public SalesSite saveIfNotExist(SalesSite salesSite) {
        if (salesSite.getReference() != null) {
            Optional<SalesSite> existingSalesSite = repository.findByReference(salesSite.getReference());
            return existingSalesSite.orElseGet(() -> repository.save(salesSite));
        }
        return save(salesSite);
    }

    @Override
    public SalesSiteDto findById(Long id) {
        return mapper.map(
            repository.findById(id)
                .orElseThrow(() ->  new NotFoundException(
                    ProcessException.getMessage(
                            ProcessErrorEnum.SALE_SITE_NOT_FOUND.getKey(),
                            LocaleContextHolder.getLocale(),
                            String.valueOf(id)
                    )
                ))
        );
    }

    @Override
    public SalesSiteDto findByReference(String reference) {
        return mapper.map(
            repository.findByReference(reference)
                .orElseThrow(() ->  new NotFoundException(
                    ProcessException.getMessage(
                            ProcessErrorEnum.SALE_SITE_NOT_FOUND.getKey(),
                            LocaleContextHolder.getLocale(),
                            reference
                    )
                ))
        );
    }

    @Override
    public List<SalesSiteDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::map)
                .toList();
    }
}
