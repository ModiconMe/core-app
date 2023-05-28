package edu.modicon.app.application.dto.article;

import edu.modicon.app.application.dto.ArticleDto;
import lombok.Value;

import java.util.List;

@Value
public class GetArticlesResponse {
    List<ArticleDto> articles;
}
