package edu.modicon.app.infrastructure.repository;

import edu.modicon.app.domain.model.Article;
import edu.modicon.app.domain.repository.ArticleRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static edu.modicon.app.infrastructure.repository.BaseDao.ParamBuilder.parameters;

@Repository
public class JdbcArticleRepository extends BaseDao implements ArticleRepository {

    public JdbcArticleRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public List<Article> getArticles(String tag, String author, boolean favorited, int limit, int offset, Long userId) {
        SqlParameterSource params = parameters()
                .add("tag", tag)
                .add("author", author)
                .add("favorited", favorited)
                .add("limit", limit)
                .add("offset", offset)
                .add("userId", userId)
                .build();
        return list(Sql.Article.GET_ARTICLES, params, (rs, rowNum) -> new Article(rs.getLong("id"),
                rs.getString("slug"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("body"),
                new ArrayList<>(),
                rs.getTimestamp("created_at").toInstant().atZone(ZoneId.of("Asia/Barnaul")),
                rs.getTimestamp("updated_at").toInstant().atZone(ZoneId.of("Asia/Barnaul")),
                rs.getBoolean("favorited"),
                rs.getInt("favorites_count"),
                rs.getLong("author_id")));
    }
}
