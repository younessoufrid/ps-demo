package com.demo.portailsaisie.backend.core.service.v1;

import com.demo.portailsaisie.backend.core.domain.Article;
import com.demo.portailsaisie.backend.core.repository.ArticleRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository repository;

    @Override
    public Article save(Article article) {
        return repository.save(article);
    }

    @Override
    public Article saveIfNotExist(Article article) {
        if (article.getReferenceBu() != null) {
            Optional<Article> existingArticle = repository.findByReferenceBu(article.getReferenceBu());
            return existingArticle.orElseGet(() -> repository.save(article));
        }
        return save(article);
    }

}
