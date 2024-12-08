package com.demo.portailsaisie.backend.core.repository;

import com.demo.portailsaisie.backend.core.domain.AOrderArticleOccurrence;
import com.demo.portailsaisie.backend.core.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AOrderArticleOccurrenceRepository extends JpaRepository<AOrderArticleOccurrence, Long> {

    List<AOrderArticleOccurrence> findByOrder_id(Long id);
    List<AOrderArticleOccurrence> findByOrder_idAndDateBetween(Long id, Date date1, Date date2);
    List<AOrderArticleOccurrence> findByArticleOccurrence_id(Long id);
    List<AOrderArticleOccurrence> findByOrder_Affair_id(Long id);
    List<AOrderArticleOccurrence> findByOrder_Affair_idAndDateBetween(Long id, Date date1, Date date2);
    List<AOrderArticleOccurrence> findByOrder_Client_id(Long id);
    List<AOrderArticleOccurrence> findByOrder_Client_idAndDateBetween(Long id, Date date1, Date date2);
    List<AOrderArticleOccurrence> findByOrder_Client_ResponsibilityCenters_reference(String reference);
    List<AOrderArticleOccurrence> findByOrder_Client_ResponsibilityCenters_referenceAndDateBetween(String reference, Date date1, Date date2);

    List<AOrderArticleOccurrence> findByOrder_idAndLineAndDateBetween(long orderId, long ligne, Date date1, Date date2);
    List<AOrderArticleOccurrence> findByOrder_idAndLine(long orderId, long ligne);
    List<AOrderArticleOccurrence> findByOrder_idAndInit(long orderId, boolean init);

    List<AOrderArticleOccurrence> findByOrder_IdAndLineAndDateBetween(long orderId, long ligne, Date date1, Date date2);
    Optional<AOrderArticleOccurrence> findByOrder_NumberAndLineAndDate(String orderNumber, long ligne, Date date1);
    List<AOrderArticleOccurrence> findByOrder_IdAndLine(long orderId, long ligne);
    Optional<AOrderArticleOccurrence> findByOrderAndLine(Order order, double line);

    List<AOrderArticleOccurrence> findByOrder_idAndDate(Long orderId, Date date);
}
