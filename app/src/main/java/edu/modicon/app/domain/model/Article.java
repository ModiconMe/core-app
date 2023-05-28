package edu.modicon.app.domain.model;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    // todo test validation annotation
    @EqualsAndHashCode.Include
    private Long id;
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tags;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private boolean favorited;
    private int favoritesCount;
    private long authorId;

}
