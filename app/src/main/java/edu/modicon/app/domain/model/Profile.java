package edu.modicon.app.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class Profile {

    private final String username;
    private final String bio;
    private final String image;
    private final Boolean following;

}
