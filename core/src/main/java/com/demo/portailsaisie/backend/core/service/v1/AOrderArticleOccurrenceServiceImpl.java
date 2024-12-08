package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.AOrderArticleOccurrence;
import com.demo.portailsaisie.backend.core.repository.AOrderArticleOccurrenceRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.AOrderArticleOccurrenceService;
import com.demo.portailsaisie.backend.utils.exception.ProcessErrorEnum;
import com.demo.portailsaisie.backend.utils.exception.ProcessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AOrderArticleOccurrenceServiceImpl implements AOrderArticleOccurrenceService {

    private final AOrderArticleOccurrenceRepository repository;
    public AOrderArticleOccurrence save(AOrderArticleOccurrence aOrderArticleOccurrence) {
        return repository.save(aOrderArticleOccurrence);
    }

    @Override
    public AOrderArticleOccurrence saveIfNotExist(AOrderArticleOccurrence aOrderArticleOccurrence) {
        Optional<AOrderArticleOccurrence> existingAOrderOccurrence = repository
                .findByOrderAndLine(aOrderArticleOccurrence.getOrder(),
                        aOrderArticleOccurrence.getLine());
        return existingAOrderOccurrence.orElseGet(() -> repository.save(aOrderArticleOccurrence));
    }

    @Override
    public AOrderArticleOccurrence updateQuantity(AOrderArticleOccurrence aOrderArticleOccurrence, String orderNumber) {
        AOrderArticleOccurrence aOrderArticleOccurrenceResult = repository.findByOrder_NumberAndLineAndDate(
                orderNumber,
                aOrderArticleOccurrence.getLine(),
                aOrderArticleOccurrence.getDate()
        ).orElseThrow(() -> new ProcessException(
                ProcessErrorEnum.ORDER_LINE_NOT_FOUND,
                String.format("Order: %s",orderNumber),
                String.format("Line: %s", aOrderArticleOccurrence.getLine()),
                String.format("Date: %s", aOrderArticleOccurrence.getDate()))
        );
        aOrderArticleOccurrenceResult.setQuantity(aOrderArticleOccurrence.getQuantity());
        return save(aOrderArticleOccurrenceResult);
    }
}
