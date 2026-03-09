package sqlDemo;

import java.sql.*;
import java.util.Scanner;

public class Demo {

    static final String URL = "jdbc:mysql://localhost:3306/universityDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASSWORD = "Shivani@123";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to database!");

            while (true) {
                System.out.println("\n1. Insert");
                System.out.println("2. Update");
                System.out.println("3. Delete");
                System.out.println("4. Exit");
                System.out.print("Choose option: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    // INSERT
                    case 1 -> {
                        System.out.print("Enter ID: ");
                        int studentID = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String fullName = sc.nextLine();

                        System.out.print("Enter Location: ");
                        String location = sc.nextLine();

                        String insertQuery =
                                "INSERT INTO StudentsData (StudentID, FullName, Location) VALUES (?, ?, ?)";

                        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                            insertStmt.setInt(1, studentID);
                            insertStmt.setString(2, fullName);
                            insertStmt.setString(3, location);

                            int rowsInserted = insertStmt.executeUpdate();
                            if (rowsInserted > 0)
                                System.out.println("Data inserted successfully!");
                        }
                    }

                    // UPDATE
                    case 2 -> {
                        System.out.print("Enter ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new Name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter new Location: ");
                        String newLocation = sc.nextLine();

                        String updateQuery =
                                "UPDATE StudentsData SET FullName = ?, Location = ? WHERE StudentID = ?";

                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                            updateStmt.setString(1, newName);
                            updateStmt.setString(2, newLocation);
                            updateStmt.setInt(3, updateId);

                            int rowsUpdated = updateStmt.executeUpdate();
                            if (rowsUpdated > 0)
                                System.out.println("Data updated successfully!");
                            else
                                System.out.println("ID not found!");
                        }
                    }

                    // DELETE
                    case 3 -> {
                        System.out.print("Enter ID to delete: ");
                        int deleteId = sc.nextInt();

                        String deleteQuery = "DELETE FROM StudentsData WHERE StudentID = ?";

                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                            deleteStmt.setInt(1, deleteId);

                            int rowsDeleted = deleteStmt.executeUpdate();
                            if (rowsDeleted > 0)
                                System.out.println("Data deleted successfully!");
                            else
                                System.out.println("ID not found!");
                        }
                    }

                    case 4 -> {
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    }

                    default -> System.out.println("Invalid choice!");
                }
            }

        } catch (SQLException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}