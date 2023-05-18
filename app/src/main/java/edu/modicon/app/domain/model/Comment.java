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
public class Comment {

    @EqualsAndHashCode.Include
    private final Long id;
    private final ZonedDateTime createdAt;
    private final ZonedDateTime updatedAt;
    private final String body;
    private final int authorId;

}
