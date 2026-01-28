package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class KoneksiDB {

    private static final Properties prop = new Properties();

    static {
        try (InputStream is = KoneksiDB.class
                .getClassLoader()
                .getResourceAsStream("config/db.properties")) {

            if (is == null) {
                throw new RuntimeException("File db.properties tidak ditemukan di classpath");
            }

            prop.load(is);
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (Exception e) {
            throw new RuntimeException("Gagal load konfigurasi database", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                prop.getProperty("db.url"),
                prop.getProperty("db.user"),
                prop.getProperty("db.password")
        );
    }
}
