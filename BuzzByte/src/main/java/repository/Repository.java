package repository;

import domain.User.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Repository implements IRepository {
    private final String url;       // should be like "jdbc:postgresql://localhost:5432/buzzbytedb";
    private final String user;      // should be like "buzzbytedev";
    private final String password;  // should be like "buzz".

    // Get database connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public Repository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public void addUser(IUser user) {
        String sqlQuery = "INSERT INTO \"Users\" (id, username, email, hashedPassword, avatarUrl, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery))
        {
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getHashedPassword());
            pstmt.setString(5, user.getAvatarUrl());
            pstmt.setString(6, user.getRole());
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public List<IUser> getAllUsers() {
        List<IUser> users = new ArrayList<>();
        String sqlQuery = "SELECT * FROM \"Users\"";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery))
        {
            while (rs.next())
            {
                IUser user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("hashedPassword"),
                        rs.getString("avatarUrl"),
                        rs.getString("role")
                );
                users.add(user);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public IUser getUserById(Integer id) {
        IUser user = null;
        String sqlQuery = "SELECT * FROM \"Users\" WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery))
        {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("hashedPassword"),
                            rs.getString("avatarUrl"),
                            rs.getString("role")
                    );
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateUser(IUser user) {
        String sqlStmt = "UPDATE \"Users\" SET username = ?, email = ?, hashedPassword = ?, avatarUrl = ?, role = ? WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlStmt))
        {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getHashedPassword());
            pstmt.setString(4, user.getAvatarUrl());
            pstmt.setString(5, user.getRole());
            pstmt.setInt(6, user.getId());
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Integer id) {
        String sqlStmt = "DELETE FROM \"Users\" WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlStmt))
        {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
