package com.demo.portailsaisie.backend.core.repository;

import com.demo.portailsaisie.backend.core.domain.Article;
import com.demo.portailsaisie.backend.core.domain.ArticleOccurrence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleOccurrenceRepository extends JpaRepository<ArticleOccurrence, Long> {
    Optional<ArticleOccurrence> findByReference(String reference);
    Optional<ArticleOccurrence> findByArticle(Article article);
}
