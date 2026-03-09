package sqlDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class JoinDemo {

    static final String URL = "jdbc:mysql://localhost:3306/universityDB";
    static final String USER = "root";
    static final String PASSWORD = "Shivani@123";

    public static void main(String[] args) {

        try {

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = con.createStatement();

            // ----------------- CREATE TABLES -----------------

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Students (" +
                    "student_id INT PRIMARY KEY, " +
                    "name VARCHAR(50))");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Clubs (" +
                    "club_id INT PRIMARY KEY, " +
                    "club_name VARCHAR(50))");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Student_Club (" +
                    "student_id INT, " +
                    "club_id INT)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Users (" +
                    "user_id INT PRIMARY KEY, " +
                    "name VARCHAR(50))");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Subscriptions (" +
                    "sub_id INT PRIMARY KEY, " +
                    "user_id INT, " +
                    "plan VARCHAR(50))");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Authors (" +
                    "author_id INT PRIMARY KEY, " +
                    "author_name VARCHAR(50))");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Books (" +
                    "book_id INT PRIMARY KEY, " +
                    "title VARCHAR(100), " +
                    "author_id INT)");

            // ----------------- INSERT DATA -----------------

            st.executeUpdate("INSERT IGNORE INTO Students VALUES (1,'Rahul'),(2,'Priya'),(3,'Amit'),(4,'Neha')");

            st.executeUpdate("INSERT IGNORE INTO Clubs VALUES (101,'Robotics'),(102,'Photography')");

            st.executeUpdate("INSERT IGNORE INTO Student_Club VALUES (1,101),(2,102),(3,101)");

            st.executeUpdate("INSERT IGNORE INTO Users VALUES (1,'Arjun'),(2,'Sneha'),(3,'Karan'),(4,'Meera')");

            st.executeUpdate("INSERT IGNORE INTO Subscriptions VALUES (201,1,'Premium'),(202,2,'Basic')");

            st.executeUpdate("INSERT IGNORE INTO Authors VALUES (1,'R.K. Narayan'),(2,'Chetan Bhagat')");

            st.executeUpdate("INSERT IGNORE INTO Books VALUES (301,'Malgudi Days',1),(302,'Five Point Someone',2),(303,'Unknown Mystery',NULL)");

            // ----------------- INNER JOIN -----------------

            System.out.println("\nStudents who joined clubs:");

            ResultSet rs = st.executeQuery(
                    "SELECT Students.name, Clubs.club_name " +
                    "FROM Students " +
                    "INNER JOIN Student_Club ON Students.student_id = Student_Club.student_id " +
                    "INNER JOIN Clubs ON Student_Club.club_id = Clubs.club_id");

            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getString("club_name"));
            }

            // ----------------- LEFT JOIN -----------------

            System.out.println("\nAll users and their subscriptions:");

            rs = st.executeQuery(
                    "SELECT Users.name, Subscriptions.plan " +
                    "FROM Users " +
                    "LEFT JOIN Subscriptions ON Users.user_id = Subscriptions.user_id");

            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getString("plan"));
            }

            // ----------------- RIGHT JOIN -----------------

            System.out.println("\nAll books and their authors:");

            rs = st.executeQuery(
                    "SELECT Books.title, Authors.author_name " +
                    "FROM Authors " +
                    "RIGHT JOIN Books ON Authors.author_id = Books.author_id");

            while (rs.next()) {
                System.out.println(rs.getString("title") + " - " + rs.getString("author_name"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}