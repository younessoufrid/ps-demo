package com.demo.portailsaisie.backend.web.dto.mapping;

import com.demo.portailsaisie.backend.core.dto.saisie.SaisieDto;
import com.demo.portailsaisie.backend.web.dto.saisie.SaisieWo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SaisieWoMapper {
    SaisieDto map(SaisieWo saisieWo);

    SaisieWo map(SaisieDto saisieDto);
    List<SaisieWo> map(List<SaisieDto> saisieDto);
}
