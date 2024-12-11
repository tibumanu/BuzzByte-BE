package com.example.BuzzByte.model;

import com.example.BuzzByte.login_system.utils.validation.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")   // "user" is a reserved keyword in SQL
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username cannot be null")
    @NotEmpty(message = "Username cannot be empty")
    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String hashedPassword;
//    private String avatarUrl;  // todo: should be deleted?

    // discutabil
    @Column(unique = true)
    private UUID uniqueKey;

    private Role role;
    private boolean isEnabled;

    @Lob
    private byte[] profilePicture;  // null by default

    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"
            )
    )
    private List<Tag> tags = new ArrayList<>();
}
