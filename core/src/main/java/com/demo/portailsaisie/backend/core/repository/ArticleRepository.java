package com.demo.portailsaisie.backend.core.repository;

import com.demo.portailsaisie.backend.core.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByReferenceBu(String referenceBu);
}
