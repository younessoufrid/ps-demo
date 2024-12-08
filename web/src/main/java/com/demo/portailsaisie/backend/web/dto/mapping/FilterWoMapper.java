package com.demo.portailsaisie.backend.web.dto.mapping;

import com.demo.portailsaisie.backend.core.dto.AOrderArticleOccurrenceDto;
import com.demo.portailsaisie.backend.core.dto.search.FilterRequestDto;
import com.demo.portailsaisie.backend.core.dto.search.FilterResponseDto;
import com.demo.portailsaisie.backend.web.dto.OrderArticleOccurrenceWo;
import com.demo.portailsaisie.backend.web.dto.filter.FilterRequestWo;
import com.demo.portailsaisie.backend.web.dto.filter.FilterResponseWo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(uses = SummaryItemWoMapper.class)
public abstract class FilterWoMapper {

    @Mapping(target = "saleSiteRef", source = "saleSite")
    @Mapping(target = "responsibilityCenterRef", source = "cdr")
    @Mapping(target = "clientId", source = "client")
    @Mapping(target = "affairId", source = "affair")
    @Mapping(target = "orderId", source = "order")
    @Mapping(target = "date", expression = "java(parseDate(wo.getDate()))")
    public abstract FilterRequestDto map(FilterRequestWo wo);

    @Mapping(target = "orderLines", source = "orderArticleOccurrences")
    @Mapping(target = "cdrs", source = "responsibilityCenters")
    public abstract FilterResponseWo map(FilterResponseDto dto);

    @Mapping(target = "idOrder", source = "order.id")
    public abstract OrderArticleOccurrenceWo map(AOrderArticleOccurrenceDto dto);

    protected Date parseDate(String date)  {
        if (date == null)
            return null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateParsed = null;
        try {
            dateParsed = formatter.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return dateParsed;
    }
}
