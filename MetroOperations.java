package sqlDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class MetroOperations {

    static final String URL = "jdbc:mysql://localhost:3306/MetroDB";
    static final String USER = "root";
    static final String PASSWORD = "Shivani@123";

    public static void main(String[] args) {

        try {

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = con.createStatement();

            // Insert Stations
            st.executeUpdate("INSERT INTO Stations VALUES (1,'Majestic','Bangalore',6,2011)");
            st.executeUpdate("INSERT INTO Stations VALUES (2,'Indiranagar','Bangalore',4,2012)");
            st.executeUpdate("INSERT INTO Stations VALUES (3,'MG Road','Bangalore',5,2011)");
            st.executeUpdate("INSERT INTO Stations VALUES (4,'Whitefield','Bangalore',3,2023)");
            st.executeUpdate("INSERT INTO Stations VALUES (5,'Yelahanka','Bangalore',2,2022)");

            // Insert Trains
            st.executeUpdate("INSERT INTO Trains VALUES (101,'Metro Express',1000,1)");
            st.executeUpdate("INSERT INTO Trains VALUES (102,'City Rider',900,2)");
            st.executeUpdate("INSERT INTO Trains VALUES (103,'Rapid Metro',1100,3)");

            // Update Train
            st.executeUpdate("UPDATE Trains SET capacity=1200 WHERE train_id=101");

            // Delete Station
            st.executeUpdate("DELETE FROM Stations WHERE station_id=5");

            // Display Stations
            ResultSet rs = st.executeQuery("SELECT * FROM Stations");

            System.out.println("Stations Table:");
            while(rs.next())
            {
                System.out.println(
                        rs.getInt("station_id")+" "+
                        rs.getString("station_name")+" "+
                        rs.getString("location")+" "+
                        rs.getInt("platforms")+" "+
                        rs.getInt("opening_year")
                );
            }

            // Display Trains
            rs = st.executeQuery("SELECT * FROM Trains");

            System.out.println("\nTrains Table:");
            while(rs.next())
            {
                System.out.println(
                        rs.getInt("train_id")+" "+
                        rs.getString("train_name")+" "+
                        rs.getInt("capacity")+" "+
                        rs.getInt("station_id")
                );
            }

            // DCL Commands
            st.executeUpdate("CREATE USER 'metro_staff'@'localhost' IDENTIFIED BY 'staff123'");
            st.executeUpdate("GRANT SELECT ON MetroDB.Stations TO 'metro_staff'@'localhost'");
            st.executeUpdate("GRANT INSERT ON MetroDB.Trains TO 'metro_staff'@'localhost'");
            st.executeUpdate("REVOKE INSERT ON MetroDB.Trains FROM 'metro_staff'@'localhost'");

            System.out.println("Operations Completed");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
