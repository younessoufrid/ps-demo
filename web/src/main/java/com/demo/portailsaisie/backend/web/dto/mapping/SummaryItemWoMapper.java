package com.demo.portailsaisie.backend.web.dto.mapping;

import com.demo.portailsaisie.backend.core.dto.summary.SummaryItemDto;
import com.demo.portailsaisie.backend.web.dto.sammury.SummaryItemWo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Mapper
public interface SummaryItemWoMapper {
    @Mapping(target = "date", expression = "java(formatDate(summaryItemDto.getDate()))")
    SummaryItemWo map(SummaryItemDto summaryItemDto);

    default String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("d-MMM", Locale.FRENCH);
        return formatter.format(date);
    }
}
