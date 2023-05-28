package edu.modicon.app.domain.repository;

import edu.modicon.app.domain.model.Article;

import java.util.List;

public interface ArticleRepository {

    List<Article> getArticles(String tag, String author, boolean favorited, int limit, int offset, Long userId);

}
