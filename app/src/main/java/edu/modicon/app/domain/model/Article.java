package edu.modicon.app.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Article {

    @EqualsAndHashCode.Include
    private final Long id;
    private final String slug;
    private final String title;
    private final String description;
    private final String body;
    private final List<String> tags;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private final boolean favorited;
    private final int favoritesCount;
    private final int authorId;

}
