package edu.modicon.app.domain.model;

import lombok.*;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class User {

    @EqualsAndHashCode.Include
    private final Long id;
    private final String email;
    private final String username;
    private final String bio;
    private final String image;

}
