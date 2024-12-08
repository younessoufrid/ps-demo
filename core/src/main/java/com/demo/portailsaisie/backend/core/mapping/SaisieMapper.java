package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.domain.AOrderArticleOccurrence;
import com.demo.portailsaisie.backend.core.dto.saisie.SaisieDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface SaisieMapper {
    @Mapping(target = "idOrder", source = "order.id")
    SaisieDto map(AOrderArticleOccurrence aOrderArticleOccurrence);
    List<SaisieDto> map(List<AOrderArticleOccurrence> aOrderArticleOccurrence);
}
