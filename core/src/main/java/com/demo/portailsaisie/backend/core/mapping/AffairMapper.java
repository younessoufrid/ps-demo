package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.domain.Affair;
import com.demo.portailsaisie.backend.core.dto.AffairDto;
import org.mapstruct.Mapper;

@Mapper
public interface AffairMapper {

    AffairDto map(Affair affair);
}
