package repository;

import config.KoneksiDB;
import model.GameItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameRepository {

    public List<GameItem> findAll() throws SQLException {
        String sql = """
            SELECT id, title, icon_resource, jar_path, description
            FROM game_library
            ORDER BY id DESC
        """;

        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<GameItem> out = new ArrayList<>();
            while (rs.next()) {
                out.add(new GameItem(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("icon_resource"),
                        rs.getString("jar_path"),
                        rs.getString("description")
                ));
            }
            return out;
        }
    }

    public void insert(String title, String iconResource, String jarPath, String description) throws SQLException {
        String sql = """
            INSERT INTO game_library
            (title, icon_resource, jar_path, description)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, iconResource);
            ps.setString(3, jarPath);
            ps.setString(4, description);
            ps.executeUpdate();
        }
    }

    public void deleteById(int id) throws SQLException {
        String sql = "DELETE FROM game_library WHERE id = ?";

        try (Connection c = KoneksiDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
