package edu.modicon.app.domain.service;

import edu.modicon.app.application.dto.ArticleDto;
import edu.modicon.app.application.dto.article.GetArticlesRequest;
import edu.modicon.app.application.dto.article.GetArticlesResponse;
import edu.modicon.app.domain.mapper.ArticleMapper;
import edu.modicon.app.domain.model.User;
import edu.modicon.app.domain.repository.ArticleRepository;
import edu.modicon.app.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.modicon.app.application.dto.ApiException.notFound;

@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;

    @Override
    public GetArticlesResponse getArticles(GetArticlesRequest request) {
        User user = userRepository.findByUsername(request.getCurrentUsername())
                .orElseThrow(() -> notFound("User not found"));

        List<ArticleDto> articles = articleRepository.getArticles(
                        request.getTag(), request.getAuthor(),
                        request.getFavorited() != null && Boolean.parseBoolean(request.getFavorited()),
                        request.getLimit(), request.getOffset(), user.getId())
                .stream()
                .map(articleMapper).toList();
        return new GetArticlesResponse(articles);
    }

}
