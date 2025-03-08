package domain.User;


public interface IUser {
    Integer getId();
    void setId(Integer id);

    String getUsername();
    void setUsername(String username);

    String getEmail();
    void setEmail(String email);

    String getHashedPassword();
    void setHashedPassword(String hashedPassword);

    String getAvatarUrl();
    void setAvatarUrl(String avatarUrl);

    String getRole();
    void setRole(String role);
}
