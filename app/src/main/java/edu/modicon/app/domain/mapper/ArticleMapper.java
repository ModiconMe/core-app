package edu.modicon.app.domain.mapper;

import edu.modicon.app.application.dto.ArticleDto;
import edu.modicon.app.domain.model.Article;
import edu.modicon.app.domain.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static edu.modicon.app.application.dto.ApiException.unprocessableEntity;

@RequiredArgsConstructor
@Component
public class ArticleMapper implements Function<Article, ArticleDto> {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    @Override
    public ArticleDto apply(Article article) {
        return new ArticleDto(
                article.getSlug(), article.getTitle(), article.getDescription(),
                article.getBody(), article.getTags(), article.getCreatedAt(),
                article.getUpdatedAt(), article.isFavorited(), article.getFavoritesCount(),
                profileMapper.apply(
                        profileRepository.findById(article.getAuthorId())
                                .orElseThrow(() -> unprocessableEntity("Profile not found")))
        );
    }
}
