package edu.modicon.app.domain.model;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @EqualsAndHashCode.Include
    private Long id;
    private String email;
    private String username;
    private String password;
    private String bio;
    private String image;

}
