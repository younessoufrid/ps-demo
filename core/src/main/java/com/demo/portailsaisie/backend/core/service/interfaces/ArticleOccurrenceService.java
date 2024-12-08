package com.demo.portailsaisie.backend.core.service.interfaces;

import com.demo.portailsaisie.backend.core.domain.ArticleOccurrence;
import com.demo.portailsaisie.backend.core.dto.AOrderArticleOccurrenceDto;
import com.demo.portailsaisie.backend.core.dto.ArticleOccurrenceDto;
import com.demo.portailsaisie.backend.core.dto.summary.SummaryDto;
import com.demo.portailsaisie.backend.core.dto.saisie.SaisieDto;
import com.demo.portailsaisie.backend.core.enums.UniteVente;

import java.util.Date;
import java.util.List;

public interface ArticleOccurrenceService extends GenericService<ArticleOccurrence> {

    ArticleOccurrenceDto findById(Long id);
    List<AOrderArticleOccurrenceDto> findOrderArticleOccurrenceByArticleOccurrenceId(Long id);
    List<AOrderArticleOccurrenceDto> findByResponsibilityCenterReference(String reference);
    List<AOrderArticleOccurrenceDto> findByResponsibilityCenterReferenceAndDate(String reference, Date date);
    List<AOrderArticleOccurrenceDto> findByClientId(Long id);
    List<AOrderArticleOccurrenceDto> findByClientIdAndDate(Long id, Date date);
    List<AOrderArticleOccurrenceDto> findByAffairId(Long id);
    List<AOrderArticleOccurrenceDto> findByAffairIdAndDate(Long id, Date date);
    List<AOrderArticleOccurrenceDto> findByOrderId(Long id);
    List<AOrderArticleOccurrenceDto> findByOrderIdAndInitTrue(Long id);
    List<AOrderArticleOccurrenceDto> findByOrderIdAndDate(Long id, Date date);

    List<AOrderArticleOccurrenceDto> getOrDuplicateInitialLinesThenGetAOrderArticleOccurrenceDto(Long orderId, Date date);


    SaisieDto updateQuantity(SaisieDto saisieDto);
    SaisieDto resetQuantity(SaisieDto saisieDto);
    List<SaisieDto>  resetOrderQuantity(Long orderId, Date date);
    int showHiddenLines(long idOrder, Date date);
    SaisieDto hideLine(SaisieDto saisieDto);
    SaisieDto updateTextLigne(SaisieDto saisieDto);
    Double getTotalMonthQuantity(Long id, long line, Date date);
    Double calculateTotalPrice(double quantity, double priceNetHT, UniteVente uniteVente);

    List<SummaryDto>  getSummaryOrder(Long ordeId, Date date);
    Boolean validateMonthQuantity(Long orderId);
}
