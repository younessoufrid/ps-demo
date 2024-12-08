package com.demo.portailsaisie.backend.core.service.interfaces;

import com.demo.portailsaisie.backend.core.dto.search.FilterRequestDto;
import com.demo.portailsaisie.backend.core.dto.search.FilterResponseDto;

public interface FilterService {
    FilterResponseDto filter(FilterRequestDto dto);
}
