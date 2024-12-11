package com.example.BuzzByte.model;

import com.example.BuzzByte.login_system.utils.validation.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    // discutabil
    @Column(unique = true)
    private UUID uniqueKey;

    private Role role;
    private boolean isEnabled;

    @Lob
    // @Basic(fetch = FetchType.EAGER)
    private byte[] profilePicture;  // null by default

    public static byte[] loadDefaultProfilePicture() {
        try {
            String defaultProfilePicturePath = "src/main/resources/images/default-pfp.jpg";
            return Files.readAllBytes(Paths.get(defaultProfilePicturePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PrePersist
    private void initializeToDefaultProfilePicture() {
        if (this.profilePicture == null) {
            this.profilePicture = User.loadDefaultProfilePicture();
        }
    }

    @ManyToMany
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"
            )
    )
    private List<Tag> tags = new ArrayList<>();

    // we'll store bookmarks: an array of post ids
    @ElementCollection
    private List<Long> bookmarks = new ArrayList<>();
}
