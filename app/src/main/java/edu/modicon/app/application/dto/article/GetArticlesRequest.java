package edu.modicon.app.application.dto.article;

import lombok.Getter;
import lombok.With;

@Getter
public class GetArticlesRequest {

    private final String tag;
    private final String author;
    private final String favorited;
    private final int limit;
    private final int offset;

    @With
    private final String currentUsername;

    public GetArticlesRequest(String tag, String author, String favorited, int limit, int offset, String currentUsername) {
        this.tag = tag;
        this.author = author;
        this.favorited = favorited;
        this.limit = limit;
        this.offset = offset;
        this.currentUsername = currentUsername;
    }
}
