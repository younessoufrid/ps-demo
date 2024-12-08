package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.domain.ResponsibilityCenter;
import com.demo.portailsaisie.backend.core.dto.ResponsibilityCenterDto;
import org.mapstruct.Mapper;

@Mapper
public interface ResponsibilityCenterMapper {

    ResponsibilityCenterDto map(ResponsibilityCenter responsibilityCenter);
}
