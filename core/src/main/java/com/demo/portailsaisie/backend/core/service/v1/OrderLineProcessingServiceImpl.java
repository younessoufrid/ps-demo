package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.*;
import com.demo.portailsaisie.backend.core.service.interfaces.*;
import com.demo.portailsaisie.backend.core.domain.*;
import com.demo.portailsaisie.backend.core.dto.wrapper.OrderLineWrapper;
import com.demo.portailsaisie.backend.core.mapping.OrderLineMapper;
import com.demo.portailsaisie.backend.core.service.interfaces.*;
import com.demo.portailsaisie.backend.utils.parser.model.ExcelCsvRow;
import com.demo.portailsaisie.backend.utils.parser.model.ImportCsvRowFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderLineProcessingServiceImpl implements OrderLineProcessingService {

    private final OrderLineMapper orderLineMapper;
    private final AffairService affairService;
    private final OrderService orderService;
    private final ClientService clientService;
    private final ResponsibilityCenterService responsibilityCenterService;
    private final ProductService productService;
    private final ProjectService projectService;
    private final ArticleOccurrenceService articleOccurrenceService;
    private final AOrderArticleOccurrenceService aOrderArticleOccurrenceService;
    private final SalesSiteService salesSiteService;
    private final ArticleService articleService;
    private final FileParsingService fileParsingService;

    @Transactional
    public void processFileAndSaveOrderLines(MultipartFile file) {
        List<ExcelCsvRow> parsedLines = fileParsingService.parseFile(file, ExcelCsvRow.class);
        if (parsedLines != null) {
            List<OrderLineWrapper> orderLineWrappers = mapFileLinesToOrderLineWrappers(parsedLines);
            saveOrderLines(orderLineWrappers);
        }
    }

    @Transactional
    public void processLocalFileAndUpdateOrderLines(MultipartFile file) {
        List<ImportCsvRowFormat> parsedLines = fileParsingService.parseFile(file, ImportCsvRowFormat.class);
        if (parsedLines != null) {
            List<OrderLineWrapper> orderLineWrappers = mapLocalFileLinesToOrderLineWrappers(parsedLines);
            updateOrderLines(orderLineWrappers);
        }
    }

    private List<OrderLineWrapper> mapFileLinesToOrderLineWrappers(List<ExcelCsvRow> lines) {
        return lines.stream()
                .map(orderLineMapper::map)
                .toList();
    }

    private List<OrderLineWrapper> mapLocalFileLinesToOrderLineWrappers(List<ImportCsvRowFormat> lines) {
        return lines.stream()
                .map(orderLineMapper::map)
                .toList();
    }

    protected void saveOrderLines(List<OrderLineWrapper> orderLineWrappers) {
        orderLineWrappers.forEach(this::saveOrderLine);
    }

    protected void updateOrderLines(List<OrderLineWrapper> orderLineWrappers) {
        orderLineWrappers.forEach(this::updateOrderLine);
    }

    protected void saveOrderLine(OrderLineWrapper orderLineWrapper) {
        // logic for saving
        Project project = saveProject(orderLineWrapper.getProject());
        Product product = saveProduct(orderLineWrapper.getProduct());
        SalesSite salesSite = saveSalesSite(orderLineWrapper.getSalesSite());
        Client client = saveClient(orderLineWrapper.getClient());
        Order order = saveOrder(orderLineWrapper.getOrder(), project, client);
        Affair affair = saveAffair(orderLineWrapper.getAffair(), order, client);
        order = updateOrder(order, affair);
        Article article = saveArticle(orderLineWrapper.getArticle(), product);
        ArticleOccurrence articleOccurrence = saveArticleOccurrence(orderLineWrapper.getArticleOccurrence(), article);
        AOrderArticleOccurrence aOrderArticleOccurrence = saveAOrderArticleOccurrence(orderLineWrapper.getAOrderArticleOccurrence(), order, articleOccurrence);
        ResponsibilityCenter responsibilityCenter = saveResponsibilityCenter(orderLineWrapper.getResponsibilityCenter(), salesSite, client);
    }

    protected void updateOrderLine(OrderLineWrapper orderLineWrapper) {
        articleOccurrenceService.getOrDuplicateInitialLinesThenGetAOrderArticleOccurrenceDto(
                orderService.findByNumber(orderLineWrapper.getOrder().getNumber()).getId(),
                orderLineWrapper.getAOrderArticleOccurrence().getDate()
        );
        aOrderArticleOccurrenceService.updateQuantity(
                orderLineWrapper.getAOrderArticleOccurrence(),
                orderLineWrapper.getOrder().getNumber()
        );
    }

    private Project saveProject(Project project) {
        return projectService.saveIfNotExist(project);
    }

    private Product saveProduct(Product product) {
        return productService.saveIfNotExist(product);
    }

    private SalesSite saveSalesSite(SalesSite salesSite) {
        return salesSiteService.saveIfNotExist(salesSite);
    }

    private Client saveClient(Client client) {
        return clientService.saveIfNotExist(client);
    }

    private Order saveOrder(Order order, Project project, Client client) {
        order = orderService.saveIfNotExist(order);
        order.setProject(project);
        order.setClient(client);
        return orderService.save(order);
    }

    private Order updateOrder(Order order, Affair affair) {
        order.setAffair(affair);
        return orderService.save(order);
    }

    private Affair saveAffair(Affair affair, Order order, Client client) {
        affair = affairService.saveIfNotExist(affair);
        affairService.addClient(affair, client);
        return affairService.addOrder(affair, order);
    }

    private Article saveArticle(Article article, Product product) {
        article = articleService.saveIfNotExist(article);
        article.setProduct(product);
        return articleService.save(article);
    }

    private ArticleOccurrence saveArticleOccurrence(ArticleOccurrence articleOccurrence, Article article) {
        articleOccurrence = articleOccurrenceService.saveIfNotExist(articleOccurrence);
        articleOccurrence.setArticle(article);
        return articleOccurrenceService.save(articleOccurrence);
    }

    private AOrderArticleOccurrence saveAOrderArticleOccurrence(AOrderArticleOccurrence aOrderArticleOccurrence, Order order, ArticleOccurrence articleOccurrence) {
        aOrderArticleOccurrence.setOrder(order);
        aOrderArticleOccurrence.setArticleOccurrence(articleOccurrence);
        aOrderArticleOccurrence.setShow(true);
        aOrderArticleOccurrence.setInit(true);
        aOrderArticleOccurrence.setDate(new Date());
        aOrderArticleOccurrence = aOrderArticleOccurrenceService.saveIfNotExist(aOrderArticleOccurrence);
        return aOrderArticleOccurrenceService.save(aOrderArticleOccurrence);
    }

    private ResponsibilityCenter saveResponsibilityCenter(ResponsibilityCenter responsibilityCenter, SalesSite salesSite, Client client) {
        responsibilityCenter = responsibilityCenterService.saveIfNotExist(responsibilityCenter);
        responsibilityCenterService.addClient(responsibilityCenter, client);
        return responsibilityCenterService.addSalesSite(responsibilityCenter, salesSite);
    }

}
