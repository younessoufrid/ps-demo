package com.demo.portailsaisie.backend.core.service.interfaces;

import com.demo.portailsaisie.backend.core.domain.AOrderArticleOccurrence;

public interface AOrderArticleOccurrenceService extends GenericService<AOrderArticleOccurrence> {
    AOrderArticleOccurrence updateQuantity(AOrderArticleOccurrence aOrderArticleOccurrence, String orderNumber);
}
