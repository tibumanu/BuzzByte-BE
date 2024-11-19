package domain.User;


public class User implements IUser {
    // maybe this won't be needed in the end since we can set to auto-increment
    // the id (primary key) of the Users table in the database, will see.
    static private Integer lastId = 0;

    private Integer id;
    private String username;
    private String email;
    private String hashedPassword;
    private String avatarUrl;
    private String role;

    public User(Integer id, String username, String email, String hashedPassword, String avatarUrl, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    public User(String username, String email, String hashedPassword, String avatarUrl, String role) {
        this.id = ++lastId;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.avatarUrl = avatarUrl;
        this.role = role;
    }

    @Override
    public Integer getId() { return id; }
    @Override
    public void setId(Integer id) { this.id = id; }

    @Override
    public String getUsername() { return username; }
    @Override
    public void setUsername(String username) { this.username = username; }

    @Override
    public String getEmail() { return email; }
    @Override
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getHashedPassword() { return hashedPassword; }
    @Override
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

    @Override
    public String getAvatarUrl() { return avatarUrl; }
    @Override
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    @Override
    public String getRole() { return role; }
    @Override
    public void setRole(String role) { this.role = role; }
}
