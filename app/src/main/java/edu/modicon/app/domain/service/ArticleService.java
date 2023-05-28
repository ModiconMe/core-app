package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.article.GetArticlesRequest;
import edu.modicon.app.application.dto.article.GetArticlesResponse;

public interface ArticleService {

    GetArticlesResponse getArticles(GetArticlesRequest request);

}
