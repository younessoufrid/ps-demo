package com.demo.portailsaisie.backend.core.dto.wrapper;

import com.demo.portailsaisie.backend.core.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineWrapper {
    private Affair affair;
    private Order order;
    private Article article;
    private ArticleOccurrence articleOccurrence;
    private AOrderArticleOccurrence aOrderArticleOccurrence;
    private ResponsibilityCenter responsibilityCenter;
    private Client client;
    private SalesSite salesSite;
    private Product product;
    private Project project;
}