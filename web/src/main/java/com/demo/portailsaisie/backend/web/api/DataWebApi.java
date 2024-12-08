package com.demo.portailsaisie.backend.web.api;

import com.demo.portailsaisie.backend.core.service.interfaces.FilterService;
import com.demo.portailsaisie.backend.web.PsvUri;
import com.demo.portailsaisie.backend.web.dto.filter.FilterRequestWo;
import com.demo.portailsaisie.backend.web.dto.filter.FilterResponseWo;
import com.demo.portailsaisie.backend.web.dto.mapping.FilterWoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PsvUri.API_DATA)
@RequiredArgsConstructor
public class DataWebApi {

    private final FilterService filterService;
    private final FilterWoMapper mapper;

    @PostMapping("/filter")
//    @PreAuthorize("hasRole('ADMIN')")
    public FilterResponseWo filter(@RequestBody FilterRequestWo request){
        return mapper.map(
                filterService.filter(mapper.map(request))
        );
    }
}
