package edu.modicon.app.domain.service;

import edu.modicon.app.BaseTest;
import edu.modicon.app.application.dto.article.GetArticlesRequest;
import edu.modicon.app.application.dto.article.GetArticlesResponse;
import org.junit.jupiter.api.Test;

class ArticleServiceTest extends BaseTest {

    private final ArticleService articleService;

    public ArticleServiceTest(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Test
    void shouldGetArticles() {
        // given
        GetArticlesRequest request = new GetArticlesRequest("tag", "test1", "false",
                20, 0, "test2");

        // when
        GetArticlesResponse response = articleService.getArticles(request);

        // then
        System.out.println(response);
    }
}
