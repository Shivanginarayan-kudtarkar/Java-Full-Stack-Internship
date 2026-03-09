package sqlDemo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.*;

public class ConnectionPoolDemo {

    public static void main(String[] args) {

        HikariConfig config = new HikariConfig();

        // Correct Database Name
        config.setJdbcUrl("jdbc:mysql://localhost:3306/mydb3");
        config.setUsername("root");
        config.setPassword("Shivani@123");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);

        try (HikariDataSource ds = new HikariDataSource(config);
             Connection con = ds.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Student")) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("RNo") + " " +
                        rs.getString("Name") + " " +
                        rs.getString("city")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}