package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.domain.AOrderArticleOccurrence;
import com.demo.portailsaisie.backend.core.dto.summary.SummaryDto;
import org.mapstruct.Mapper;

@Mapper
public interface SummaryMapper {
//    @Mapping(target = "summaryItems", expression = "java(new ArrayList())")
    SummaryDto map(AOrderArticleOccurrence aOrderArticleOccurrence);


//    AggregatorAOrderArticleOccurrence map(AOrderArticleOccurrence aOrderArticleOccurrence);
//    List<AggregatorAOrderArticleOccurrence> mapToAggregator(List<AOrderArticleOccurrence> aOrderArticleOccurrence);
//
//    SummaryDto map(AggregatorAOrderArticleOccurrence aOrderArticleOccurrence);
//    List<SummaryDto> map(List<AggregatorAOrderArticleOccurrence> aOrderArticleOccurrences);
}
