package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.AOrderArticleOccurrence;
import com.demo.portailsaisie.backend.core.domain.ArticleOccurrence;
import com.demo.portailsaisie.backend.core.dto.AOrderArticleOccurrenceDto;
import com.demo.portailsaisie.backend.core.dto.ArticleOccurrenceDto;
import com.demo.portailsaisie.backend.core.dto.summary.ImmutableSummaryDto;
import com.demo.portailsaisie.backend.core.dto.summary.ImmutableSummaryItemDto;
import com.demo.portailsaisie.backend.core.dto.summary.SummaryDto;
import com.demo.portailsaisie.backend.core.dto.saisie.SaisieDto;
import com.demo.portailsaisie.backend.core.dto.summary.SummaryItemDto;
import com.demo.portailsaisie.backend.core.enums.OrderLineState;
import com.demo.portailsaisie.backend.core.enums.UniteVente;
import com.demo.portailsaisie.backend.core.mapping.ArticleOccurrenceMapper;
import com.demo.portailsaisie.backend.core.mapping.OrderMapper;
import com.demo.portailsaisie.backend.core.mapping.SaisieMapper;
import com.demo.portailsaisie.backend.core.mapping.SummaryMapper;
import com.demo.portailsaisie.backend.core.repository.AOrderArticleOccurrenceRepository;
import com.demo.portailsaisie.backend.core.repository.ArticleOccurrenceRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ArticleOccurrenceService;
import com.demo.portailsaisie.backend.utils.exception.NotFoundException;
import com.demo.portailsaisie.backend.utils.exception.ProcessErrorEnum;
import com.demo.portailsaisie.backend.utils.exception.ProcessException;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static com.demo.portailsaisie.backend.utils.date.DateUtils.*;

@Service
@RequiredArgsConstructor
public class ArticleOccurrenceServiceImpl implements ArticleOccurrenceService {

    private final AOrderArticleOccurrenceRepository aOrderArticleOccurrenceRepository;
    private final ArticleOccurrenceRepository articleOccurrenceRepository;
    private final ArticleOccurrenceMapper articleOccurrenceMapper;
    private final OrderMapper orderMapper;
    private final SaisieMapper saisieMapper;
    private final SummaryMapper summaryMapper;


    @Override
    public ArticleOccurrenceDto findById(Long id) {
        return articleOccurrenceMapper.map(
                articleOccurrenceRepository.findById(id)
                        .orElseThrow()
        );
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findOrderArticleOccurrenceByArticleOccurrenceId(Long id) {
        return aOrderArticleOccurrenceRepository.findByArticleOccurrence_id(id)
                .stream()
                .map(articleOccurrenceMapper::map)
                .toList();
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findByResponsibilityCenterReference(String reference) {
        return aOrderArticleOccurrenceRepository.findByOrder_Client_ResponsibilityCenters_reference(reference)
                .stream()
                .map(articleOccurrenceMapper::map)
                .toList();
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findByResponsibilityCenterReferenceAndDate(String reference, Date date) {
        return aOrderArticleOccurrenceRepository
                .findByOrder_Client_ResponsibilityCenters_referenceAndDateBetween(
                        reference,
                        setTimeToDate(date, 0, 0, 0, 0),
                        setTimeToDate(date, 23, 59, 59, 999)
                )
                .stream()
                .map(articleOccurrenceMapper::map)
                .toList();
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findByClientId(Long id) {
        return aOrderArticleOccurrenceRepository.findByOrder_Client_id(id)
                .stream()
                .map(articleOccurrenceMapper::map)
                .toList();
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findByClientIdAndDate(Long id, Date date) {
        return aOrderArticleOccurrenceRepository.findByOrder_Client_idAndDateBetween(
                        id,
                        setTimeToDate(date, 0, 0, 0, 0),
                        setTimeToDate(date, 23, 59, 59, 999)
                )
                .stream()
                .map(articleOccurrenceMapper::map)
                .toList();
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findByAffairId(Long id) {
        return aOrderArticleOccurrenceRepository.findByOrder_Affair_id(id)
                .stream()
                .map(articleOccurrenceMapper::map)
                .toList();
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findByAffairIdAndDate(Long id, Date date) {
        return aOrderArticleOccurrenceRepository.findByOrder_Affair_idAndDateBetween(
                        id,
                        setTimeToDate(date, 0, 0, 0, 0),
                        setTimeToDate(date, 23, 59, 59, 999)
                )
                .stream()
                .map(articleOccurrenceMapper::map)
                .toList();
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findByOrderIdAndInitTrue(Long id) {
        return aOrderArticleOccurrenceRepository.findByOrder_idAndInit(id, true)
                .stream()
                .map(articleOccurrenceMapper::map)
                .toList();
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findByOrderId(Long id) {
        return aOrderArticleOccurrenceRepository.findByOrder_id(id)
                .stream()
                .map(articleOccurrenceMapper::map)
                .toList();
    }

    @Override
    public List<AOrderArticleOccurrenceDto> findByOrderIdAndDate(Long id, Date date) {
        List<AOrderArticleOccurrence> aOrderArticleOccurrences = aOrderArticleOccurrenceRepository.findByOrder_idAndDateBetween(
                id,
                setTimeToDate(date, 0, 0, 0, 0),
                setTimeToDate(date, 23, 59, 59, 999)
        );
        aOrderArticleOccurrences.forEach(aOrderArticleOccurrence -> {
                    aOrderArticleOccurrence.setTotalMonthQte(this.getTotalMonthQuantity(aOrderArticleOccurrence.getOrder().getId(), aOrderArticleOccurrence.getLine(), date));
                    aOrderArticleOccurrence.setTotalPrice(this.calculateTotalPrice(aOrderArticleOccurrence.getTotalMonthQte(), aOrderArticleOccurrence.getPriceNetHT(), aOrderArticleOccurrence.getUniteVente()));

                }
        );
        return aOrderArticleOccurrences.stream().map(articleOccurrenceMapper::map).toList();
    }

    public ArticleOccurrence save(ArticleOccurrence articleOccurrence) {
        return articleOccurrenceRepository.save(articleOccurrence);
    }


    // this function check existence of order lines with select date, or duplicate initial lines then return them
    @Override
    @Transactional
    public List<AOrderArticleOccurrenceDto> getOrDuplicateInitialLinesThenGetAOrderArticleOccurrenceDto(Long orderId, Date date) {
        List<AOrderArticleOccurrenceDto> aOrderArticleOccurrenceDtoInit = this.findByOrderIdAndInitTrue(orderId);
        List<AOrderArticleOccurrenceDto> aOrderArticleOccurrenceDtoWithDate = this.findByOrderIdAndDate(orderId, date);
        if (CollectionUtils.isEmpty(aOrderArticleOccurrenceDtoWithDate) && !CollectionUtils.isEmpty(aOrderArticleOccurrenceDtoInit)) {
            duplicateInitialOrderArticleOccurrences(date, aOrderArticleOccurrenceDtoInit);
        }
        return this.findByOrderIdAndDate(orderId, date);
    }

    private void duplicateInitialOrderArticleOccurrences(Date date, List<AOrderArticleOccurrenceDto> aOrderArticleOccurrenceDtoInit) {
        boolean isSoumis = checkIfOrderIsSoumisForCurrentMonth(aOrderArticleOccurrenceDtoInit.get(0).getOrder().getId(), date);
        boolean isExported = checkIfOrderIsExportedForCurrentMonth(aOrderArticleOccurrenceDtoInit.get(0).getOrder().getId(), date);
        aOrderArticleOccurrenceDtoInit.forEach(
                aOrderArticleOccurrenceDto -> {
                    AOrderArticleOccurrence occurrenceToDuplicate = articleOccurrenceMapper.mapWithoutFeatures(aOrderArticleOccurrenceDto);
                    occurrenceToDuplicate.setQuantity(0);
                    occurrenceToDuplicate.setState(!isSoumis ? OrderLineState.NOUVEAU : isExported ? OrderLineState.TRAITE : OrderLineState.NOUVEAU);
                    occurrenceToDuplicate.setSoumis(isSoumis);
                    occurrenceToDuplicate.setDate(date);
                    occurrenceToDuplicate.setOrder(orderMapper.map(aOrderArticleOccurrenceDto.getOrder()));
                    occurrenceToDuplicate.setArticleOccurrence(articleOccurrenceMapper.map(aOrderArticleOccurrenceDto.getArticleOccurrence()));
                    aOrderArticleOccurrenceRepository.save(occurrenceToDuplicate);
                });
    }

    private boolean checkIfOrderIsSoumisForCurrentMonth(Long id, Date date) {
        return this.aOrderArticleOccurrenceRepository.findByOrder_idAndDateBetween(id, getFirstDayOfMonth(date), getLastDayOfMonth(date)).stream().anyMatch(AOrderArticleOccurrence::isSoumis);
    }

    private boolean checkIfOrderIsExportedForCurrentMonth(Long id, Date date) {
        return this.aOrderArticleOccurrenceRepository.findByOrder_idAndDateBetween(id, getFirstDayOfMonth(date), getLastDayOfMonth(date)).stream().anyMatch(a -> a.getState() == OrderLineState.TRAITE);
    }

    @Override
    public ArticleOccurrence saveIfNotExist(ArticleOccurrence articleOccurrence) {
        if (articleOccurrence.getReference() != null) {
            Optional<ArticleOccurrence> existingArticleOccurrence = articleOccurrenceRepository.findByReference(articleOccurrence.getReference());
            return existingArticleOccurrence.orElseGet(() -> articleOccurrenceRepository.save(articleOccurrence));
        }
        return save(articleOccurrence);
    }

    @Override
    public SaisieDto updateQuantity(SaisieDto saisieDto) {
        AOrderArticleOccurrence loadedaOrderArticleOccurrence = this.aOrderArticleOccurrenceRepository
                .findById(saisieDto.getId()).orElseThrow(() -> new NotFoundException(
                        ProcessException.getMessage(
                                ProcessErrorEnum.ORDER_LINE_NOT_FOUND.getKey(),
                                LocaleContextHolder.getLocale(),
                                String.valueOf(saisieDto.getId())
                        )
                ));
        loadedaOrderArticleOccurrence.setQuantity(saisieDto.getQuantity());
        loadedaOrderArticleOccurrence.setTotalMonthQte(this.getTotalMonthQuantity(loadedaOrderArticleOccurrence.getOrder().getId(), saisieDto.getLine(), saisieDto.getDate()));
        loadedaOrderArticleOccurrence.setTotalPrice(this.calculateTotalPrice(loadedaOrderArticleOccurrence.getTotalMonthQte(), loadedaOrderArticleOccurrence.getPriceNetHT(), loadedaOrderArticleOccurrence.getUniteVente()));
        this.aOrderArticleOccurrenceRepository.save(loadedaOrderArticleOccurrence);
        return this.saisieMapper.map(loadedaOrderArticleOccurrence);
    }

    @Override
    public SaisieDto resetQuantity(SaisieDto saisieDto) {
        Date firstDayOfMonth = getFirstDayOfMonth(saisieDto.getDate());
        Date lastDayOfMonth = getLastDayOfMonth(saisieDto.getDate());
        List<AOrderArticleOccurrence> loadedAOrderArticleOccurrences = this.aOrderArticleOccurrenceRepository
                .findByOrder_idAndLineAndDateBetween(saisieDto.getIdOrder(), saisieDto.getLine(), firstDayOfMonth, lastDayOfMonth);
        if (!loadedAOrderArticleOccurrences.isEmpty()) {
            loadedAOrderArticleOccurrences.forEach(aOrderArticleOccurrence -> aOrderArticleOccurrence.setQuantity(0));
            this.aOrderArticleOccurrenceRepository.saveAll(loadedAOrderArticleOccurrences);
            Optional<AOrderArticleOccurrence> aOrderArticleOccurrence = this.aOrderArticleOccurrenceRepository.findById(saisieDto.getId());
            if (aOrderArticleOccurrence.isPresent()) {
                AOrderArticleOccurrence loadedaOrderArticleOccurrence = aOrderArticleOccurrence.get();
                loadedaOrderArticleOccurrence.setTotalMonthQte(this.getTotalMonthQuantity(loadedaOrderArticleOccurrence.getOrder().getId(), loadedaOrderArticleOccurrence.getLine(), saisieDto.getDate()));
                loadedaOrderArticleOccurrence.setTotalPrice(this.calculateTotalPrice(loadedaOrderArticleOccurrence.getTotalMonthQte(), loadedaOrderArticleOccurrence.getPriceNetHT(), loadedaOrderArticleOccurrence.getUniteVente()));
                return this.saisieMapper.map(loadedaOrderArticleOccurrence);
            }
            throw new NotFoundException(
                    ProcessException.getMessage(
                            ProcessErrorEnum.ORDER_LINE_NOT_FOUND.getKey(),
                            LocaleContextHolder.getLocale(),
                            String.valueOf(saisieDto.getId())
                    )
            );
        } else {
            throw new NotFoundException(
                    ProcessException.getMessage(
                            ProcessErrorEnum.ORDER_LINE_NOT_FOUND.getKey(),
                            LocaleContextHolder.getLocale(),
                            String.valueOf(saisieDto.getId()),
                            String.valueOf(saisieDto.getLine()),
                            String.valueOf(saisieDto.getDate())
                    )
            );
        }
    }

    @Override
    public List<SaisieDto> resetOrderQuantity(Long orderId, Date date) {
        Date firstDayOfMonth = getFirstDayOfMonth(date);
        Date lastDayOfMonth = getLastDayOfMonth(date);
        List<AOrderArticleOccurrence> aOrderArticleOccurrences = this.aOrderArticleOccurrenceRepository.findByOrder_idAndDateBetween(orderId, firstDayOfMonth, lastDayOfMonth);
        aOrderArticleOccurrences.forEach(aOrderArticleOccurrence -> aOrderArticleOccurrence.setQuantity(0));
        this.aOrderArticleOccurrenceRepository.saveAll(aOrderArticleOccurrences);
        return saisieMapper.map(this.aOrderArticleOccurrenceRepository.findByOrder_idAndDateBetween(
                orderId,
                setTimeToDate(date, 0, 0, 0, 0),
                setTimeToDate(date, 23, 59, 59, 999)
        ));
    }

    @Override
    public int showHiddenLines(long idOrder, Date date) {
        Assert.notNull(date);
        List<AOrderArticleOccurrence> loadedAOrderArticleOccurrences = this.aOrderArticleOccurrenceRepository.findByOrder_id(idOrder);
        if (!loadedAOrderArticleOccurrences.isEmpty()) {
            loadedAOrderArticleOccurrences.forEach(aOrderArticleOccurrence -> aOrderArticleOccurrence.setShow(true));
            this.aOrderArticleOccurrenceRepository.saveAll(loadedAOrderArticleOccurrences);
            return loadedAOrderArticleOccurrences.stream().filter(aOrderArticleOccurrence -> aOrderArticleOccurrence.isShow() && aOrderArticleOccurrence.getDate() == date).toList().size();
        } else {
            throw new NotFoundException(
                    ProcessException.getMessage(
                            ProcessErrorEnum.ORDER_LINE_NOT_FOUND.getKey(),
                            LocaleContextHolder.getLocale(),
                            String.valueOf(idOrder)
                    )
            );
        }
    }

    @Override
    public SaisieDto hideLine(SaisieDto saisieDto) {
        List<AOrderArticleOccurrence> loadedAOrderArticleOccurrences = this.aOrderArticleOccurrenceRepository
                .findByOrder_idAndLine(saisieDto.getIdOrder(), saisieDto.getLine());
        if (!loadedAOrderArticleOccurrences.isEmpty()) {
            loadedAOrderArticleOccurrences.forEach(aOrderArticleOccurrence -> aOrderArticleOccurrence.setShow(false));
            this.aOrderArticleOccurrenceRepository.saveAll(loadedAOrderArticleOccurrences);
            Optional<AOrderArticleOccurrence> aOrderArticleOccurrence = this.aOrderArticleOccurrenceRepository.findById(saisieDto.getId());
            if (aOrderArticleOccurrence.isPresent()) {
                return this.saisieMapper.map(aOrderArticleOccurrence.get());
            }
            throw new NotFoundException(
                    ProcessException.getMessage(
                            ProcessErrorEnum.ORDER_LINE_NOT_FOUND.getKey(),
                            LocaleContextHolder.getLocale(),
                            String.valueOf(saisieDto.getId())
                    )
            );
        } else {
            throw new NotFoundException(
                    ProcessException.getMessage(
                            ProcessErrorEnum.ORDER_LINE_NOT_FOUND.getKey(),
                            LocaleContextHolder.getLocale(),
                            String.valueOf(saisieDto.getIdOrder()),
                            String.valueOf(saisieDto.getLine())
                    )
            );
        }
    }

    @Override
    public SaisieDto updateTextLigne(SaisieDto saisieDto) {
        AOrderArticleOccurrence loadedAOrderArticleOccurrence =
                this.aOrderArticleOccurrenceRepository
                        .findById(saisieDto.getId())
                        .orElseThrow(
                                () -> new NotFoundException(
                                        ProcessException.getMessage(
                                                ProcessErrorEnum.ORDER_LINE_NOT_FOUND.getKey(),
                                                LocaleContextHolder.getLocale(),
                                                String.valueOf(saisieDto.getId())
                                        )
                                )
                        );
        loadedAOrderArticleOccurrence.setTextLigne(saisieDto.getTextLigne());
        this.aOrderArticleOccurrenceRepository.save(loadedAOrderArticleOccurrence);
        return this.saisieMapper.map(loadedAOrderArticleOccurrence);
    }

    @Override
    public Double getTotalMonthQuantity(Long id, long line, Date date) {
        Date firstDayOfMonth = getFirstDayOfMonth(date);
        Date lastDayOfMonth = getLastDayOfMonth(date);
        List<AOrderArticleOccurrence> aOrderArticleOccurrences = this.aOrderArticleOccurrenceRepository.findByOrder_IdAndLineAndDateBetween(id, line, firstDayOfMonth, lastDayOfMonth);
        return aOrderArticleOccurrences.stream().map(AOrderArticleOccurrence::getQuantity).reduce(0D, Double::sum);
    }

    @Override
    public Double calculateTotalPrice(double quantity, double priceNetHT, UniteVente uniteVente) {
        if (uniteVente == null){
            uniteVente = UniteVente.UN;
        }
        return (quantity * priceNetHT) / uniteVente.getValue();
    }

    @Override
    public List<SummaryDto> getSummaryOrder(Long ordeId, Date date) {
        List<SummaryDto> recapOrderLines = new ArrayList<>(List.of());
        ImmutableSummaryDto.Builder builder = ImmutableSummaryDto.builder();
        Date firstDayOfMonth = getFirstDayOfMonth(date);
        Date lastDayOfMonth = getLastDayOfMonth(date);
        List<AOrderArticleOccurrence> aOrderArticleOccurrences = this.aOrderArticleOccurrenceRepository.findByOrder_idAndDateBetween(ordeId, firstDayOfMonth, lastDayOfMonth);
        List<AOrderArticleOccurrence> initAOrderArticleOccurrences = this.aOrderArticleOccurrenceRepository.findByOrder_idAndInit(ordeId, true);
        initAOrderArticleOccurrences.sort((s1, s2) -> Math.toIntExact(s1.getLine() - s2.getLine()));
        initAOrderArticleOccurrences.forEach(summaryLine -> {
                    if (!ObjectUtils.isEmpty(summaryLine.getLine())) {
                        List<AOrderArticleOccurrence> aOrderArticleOccurrencesSameLine = aOrderArticleOccurrences.stream().filter(item -> item.getLine() == summaryLine.getLine()).toList();
                        List<SummaryItemDto> items = fillSummaryItems(aOrderArticleOccurrencesSameLine, firstDayOfMonth, lastDayOfMonth);
                        double totalMonthQuantity = setTotalQuantityAndTotalPrice(items);
                        builder
                                .designation(summaryLine.getDesignation())
                                .priceNetHT(summaryLine.getPriceNetHT())
                                .uniteVente(summaryLine.getUniteVente())
                                .line(summaryLine.getLine())
                                .summaryItems(items)
                                .totalMonthQte(totalMonthQuantity)
                                .totalPrice(calculateTotalPrice(totalMonthQuantity, summaryLine.getPriceNetHT(), summaryLine.getUniteVente()));
                        aOrderArticleOccurrencesSameLine.stream().map(AOrderArticleOccurrence::isSoumis).findFirst().ifPresent(
                                builder::soumis
                        );
                    }
                    recapOrderLines.add(builder.build());
                }
        );
        return recapOrderLines;
    }


    private List<SummaryItemDto> fillSummaryItems(List<AOrderArticleOccurrence> aOrderArticleOccurrencesSameLine, Date firstDayOfMonth, Date lastDayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDayOfMonth);
        List<SummaryItemDto> s = new ArrayList<>();
        while (calendar.getTime().compareTo(lastDayOfMonth) <= 0) {
            Optional<AOrderArticleOccurrence> element = aOrderArticleOccurrencesSameLine.stream().filter(item -> compareDates(item.getDate(), calendar.getTime())).findFirst();
            if (element.isPresent()) {
                s.add(ImmutableSummaryItemDto.builder().date(element.get().getDate()).quantity(element.get().getQuantity()).build());
            } else {
                s.add(ImmutableSummaryItemDto.builder().date(calendar.getTime()).quantity(0D).build());
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return s;
    }

    private double setTotalQuantityAndTotalPrice(List<SummaryItemDto> summaryDtos) {
        return summaryDtos.stream().map(SummaryItemDto::getQuantity).reduce(0D, Double::sum);
    }

    @Override
    public Boolean validateMonthQuantity(Long orderId) {
        Date date = new Date();
        Date firstDayeOfMonth = getFirstDayOfMonth(date);
        Date lastDayeOfMonth = getLastDayOfMonth(date);
        List<AOrderArticleOccurrence> aOrderArticleOccurrences = this.aOrderArticleOccurrenceRepository.findByOrder_idAndDateBetween(orderId, firstDayeOfMonth, lastDayeOfMonth);
        if (CollectionUtils.isNotEmpty(aOrderArticleOccurrences)) {
            aOrderArticleOccurrences.forEach(aOrderArticleOccurrence -> aOrderArticleOccurrence.setSoumis(true));
            this.aOrderArticleOccurrenceRepository.saveAll(aOrderArticleOccurrences);
            return true;
        }
        throw new NotFoundException(
                ProcessException.getMessage(
                        ProcessErrorEnum.ORDER_LINE_NOT_FOUND.getKey(),
                        LocaleContextHolder.getLocale(),
                        String.valueOf(orderId)
                )
        );
    }
}
