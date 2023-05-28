package edu.modicon.app.application.api.controller;

import edu.modicon.app.application.dto.article.GetArticlesRequest;
import edu.modicon.app.application.dto.article.GetArticlesResponse;
import edu.modicon.app.domain.constant.Constant;
import edu.modicon.app.domain.service.ArticleService;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Constant.Endpoint.ARTICLES)
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public GetArticlesResponse getArticles(@RequestParam(name = "tag") String tag,
                                           @RequestParam(name = "author") String author,
                                           @RequestParam(name = "favorited") String favorited,
                                           @RequestParam(name = "limit") String limit,
                                           @RequestParam(name = "offset") String offset,
                                           @AuthenticationPrincipal AppUserDetails currentUser) {
        return articleService.getArticles(new GetArticlesRequest(
                tag, author, favorited, Integer.parseInt(limit), Integer.parseInt(offset), currentUser.getUsername()));
    }

}
