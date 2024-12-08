package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.dto.wrapper.OrderLineWrapper;
import com.demo.portailsaisie.backend.utils.parser.model.ExcelCsvRow;
import com.demo.portailsaisie.backend.utils.parser.model.ImportCsvRowFormat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderLineMapper {
    // salesSite
    @Mapping(source = "salesSiteReference", target = "salesSite.reference")
    // client
    @Mapping(source = "clientOrder", target = "client.reference")
    @Mapping(source = "clientName", target = "client.name")
    // affair
    @Mapping(source = "startService", target = "affair.startService")
    @Mapping(source = "endService", target = "affair.endService")
    @Mapping(source = "affair", target = "affair.reference")
    // order
    @Mapping(source = "orderNumber", target = "order.number")
    @Mapping(source = "orderReference", target = "order.reference")
    // aOrderArticleOccurrence
    @Mapping(source = "line", target = "aOrderArticleOccurrence.line")
    @Mapping(source = "quantity", target = "aOrderArticleOccurrence.quantity")
    @Mapping(source = "priceNetHT", target = "aOrderArticleOccurrence.priceNetHT")
    @Mapping(source = "textLine", target = "aOrderArticleOccurrence.textLigne")
    @Mapping(source = "saleUnit", target = "aOrderArticleOccurrence.uniteVente")
    @Mapping(source = "delivery", target = "aOrderArticleOccurrence.typeLivraison")
    @Mapping(source = "designation", target = "aOrderArticleOccurrence.designation")
    // article
    @Mapping(source = "buReference", target = "article.referenceBu")
    // ArticleOccurrence
    // reference ?
    @Mapping(source = "article", target = "articleOccurrence.reference")
    @Mapping(source = "activity", target = "articleOccurrence.activity")
    // cdr
    @Mapping(source = "cdr", target = "responsibilityCenter.reference")
    // project
    @Mapping(source = "project", target = "project.reference")
    // product
    @Mapping(source = "product", target = "product.reference")
    OrderLineWrapper map(ExcelCsvRow line);


    // salesSite
    @Mapping(source = "salesSiteReference", target = "salesSite.reference")
    // client
    @Mapping(source = "clientOrder", target = "client.reference")
    @Mapping(source = "clientName", target = "client.name")
    // affair
    @Mapping(source = "affair", target = "affair.reference")
    // order
    @Mapping(source = "orderNumber", target = "order.number")
    @Mapping(source = "orderReference", target = "order.reference")
    // aOrderArticleOccurrence
    @Mapping(source = "line", target = "aOrderArticleOccurrence.line")
    @Mapping(source = "quantity", target = "aOrderArticleOccurrence.quantity")
    @Mapping(source = "priceNetHT", target = "aOrderArticleOccurrence.priceNetHT")
    @Mapping(source = "textLine", target = "aOrderArticleOccurrence.textLigne")
    @Mapping(source = "saleUnit", target = "aOrderArticleOccurrence.uniteVente")
    @Mapping(source = "delivery", target = "aOrderArticleOccurrence.typeLivraison")
    @Mapping(source = "designation", target = "aOrderArticleOccurrence.designation")
    @Mapping(source = "date", target = "aOrderArticleOccurrence.date")
    // article
    @Mapping(source = "buReference", target = "article.referenceBu")
    // ArticleOccurrence
    @Mapping(source = "article", target = "articleOccurrence.reference")
    @Mapping(source = "activity", target = "articleOccurrence.activity")
    // cdr
    @Mapping(source = "cdr", target = "responsibilityCenter.reference")
    // project
    @Mapping(source = "project", target = "project.reference")
    // product
    @Mapping(source = "product", target = "product.reference")
    OrderLineWrapper map(ImportCsvRowFormat line);

}
