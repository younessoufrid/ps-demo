package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.domain.AOrderArticleOccurrence;
import com.demo.portailsaisie.backend.core.domain.ArticleOccurrence;
import com.demo.portailsaisie.backend.core.dto.AOrderArticleOccurrenceDto;
import com.demo.portailsaisie.backend.core.dto.ArticleOccurrenceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ArticleOccurrenceMapper {

    AOrderArticleOccurrenceDto map(AOrderArticleOccurrence occurrence);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quantity", ignore = true)
    @Mapping(target = "date", ignore = true)
    AOrderArticleOccurrence mapWithoutFeatures(AOrderArticleOccurrenceDto occurrence);
    ArticleOccurrenceDto map(ArticleOccurrence occurrence);
    ArticleOccurrence map(ArticleOccurrenceDto  occurrence);
}
