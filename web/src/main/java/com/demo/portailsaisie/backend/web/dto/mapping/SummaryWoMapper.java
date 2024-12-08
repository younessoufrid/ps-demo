package com.demo.portailsaisie.backend.web.dto.mapping;

import com.demo.portailsaisie.backend.core.dto.summary.SummaryDto;
import com.demo.portailsaisie.backend.web.dto.sammury.SummaryWo;

import org.mapstruct.Mapper;

@Mapper(uses = SummaryItemWoMapper.class)
public interface SummaryWoMapper {
    SummaryWo map(SummaryDto summaryDto);
}
