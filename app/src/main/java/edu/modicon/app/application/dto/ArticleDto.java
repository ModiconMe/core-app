package edu.modicon.app.application.dto;

import edu.modicon.app.application.dto.profile.ProfileDto;

import java.time.ZonedDateTime;
import java.util.List;

public record ArticleDto(
        String slug,
        String title,
        String description,
        String body,
        List<String> tagList,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        boolean favorited,
        int favoritesCount,
        ProfileDto author
) {
}
