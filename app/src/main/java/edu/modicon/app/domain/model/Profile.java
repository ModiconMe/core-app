package edu.modicon.app.domain.model;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private String username;
    private String bio;
    private String image;
    @With
    private boolean following;
}
