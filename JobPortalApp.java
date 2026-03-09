package sqlDemo;

import java.sql.*;
import java.util.Scanner;

public class JobPortalApp {

    static final String URL = "jdbc:mysql://localhost:3306/jobportal";
    static final String USER = "root";
    static final String PASSWORD = "Shivani@123";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== JOB PORTAL =====");
            System.out.println("1. Candidate");
            System.out.println("2. Company");
            System.out.println("3. Admin");
            System.out.println("4. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> candidateMenu();
                case 2 -> companyMenu();
                case 3 -> adminMenu();
                case 4 -> System.exit(0);
            }
        }
    }

    // ================= CANDIDATE =================

    static void candidateMenu() {
        System.out.println("\n1. Register\n2. Login");
        int ch = sc.nextInt();
        sc.nextLine();

        if (ch == 1) registerCandidate();
        else if (ch == 2) loginCandidate();
    }

    static void registerCandidate() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();
            System.out.print("Phone: ");
            String phone = sc.nextLine();

            String sql = "INSERT INTO candidates(name,email,password,phone) VALUES(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, phone);
            ps.executeUpdate();

            System.out.println("Registration Successful!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void loginCandidate() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();

            String sql = "SELECT * FROM candidates WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int candidateId = rs.getInt("id");
                System.out.println("Login Successful!");
                candidateDashboard(candidateId);
            } else {
                System.out.println("Invalid Credentials");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void candidateDashboard(int candidateId) {
        while (true) {
            System.out.println("\n1. View Jobs\n2. Apply Job\n3. View Applied Jobs\n4. Logout");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> viewJobs();
                case 2 -> applyJob(candidateId);
                case 3 -> viewAppliedJobs(candidateId);
                case 4 -> { return; }
            }
        }
    }

    static void viewJobs() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM jobs WHERE status='OPEN'");
            while (rs.next()) {
                System.out.println("Job ID: " + rs.getInt("id") +
                        " | Title: " + rs.getString("title") +
                        " | Location: " + rs.getString("location"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void applyJob(int candidateId) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Enter Job ID to apply: ");
            int jobId = sc.nextInt();

            String sql = "INSERT INTO applications(job_id,candidate_id) VALUES(?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, jobId);
            ps.setInt(2, candidateId);
            ps.executeUpdate();

            System.out.println("Applied Successfully!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void viewAppliedJobs(int candidateId) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = """
                    SELECT j.title, a.status 
                    FROM applications a
                    JOIN jobs j ON a.job_id = j.id
                    WHERE a.candidate_id=?""";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, candidateId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Job: " + rs.getString("title") +
                        " | Status: " + rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ================= COMPANY =================

    static void companyMenu() {
        System.out.println("\n1. Register\n2. Login");
        int ch = sc.nextInt();
        sc.nextLine();

        if (ch == 1) registerCompany();
        else if (ch == 2) loginCompany();
    }

    static void registerCompany() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Company Name: ");
            String name = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();

            String sql = "INSERT INTO companies(company_name,email,password) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.executeUpdate();

            System.out.println("Company Registered!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void loginCompany() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String pass = sc.nextLine();

            String sql = "SELECT * FROM companies WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int companyId = rs.getInt("id");
                companyDashboard(companyId);
            } else {
                System.out.println("Invalid Credentials");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void companyDashboard(int companyId) {
        while (true) {
            System.out.println("\n1. Post Job\n2. View Applicants\n3. Logout");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> postJob(companyId);
                case 2 -> viewApplicants(companyId);
                case 3 -> { return; }
            }
        }
    }

    static void postJob(int companyId) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.print("Job Title: ");
            String title = sc.nextLine();
            System.out.print("Location: ");
            String location = sc.nextLine();

            String sql = "INSERT INTO jobs(company_id,title,location) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, companyId);
            ps.setString(2, title);
            ps.setString(3, location);
            ps.executeUpdate();

            System.out.println("Job Posted!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static void viewApplicants(int companyId) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String sql = """
                    SELECT c.name, j.title 
                    FROM applications a
                    JOIN candidates c ON a.candidate_id=c.id
                    JOIN jobs j ON a.job_id=j.id
                    WHERE j.company_id=?""";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Candidate: " + rs.getString("name") +
                        " | Job: " + rs.getString("title"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ================= ADMIN =================

    static void adminMenu() {
        sc.nextLine();
        System.out.print("Admin Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        if (email.equals("admin@gmail.com") && pass.equals("admin123")) {
            System.out.println("Admin Login Successful!");
            viewStatistics();
        } else {
            System.out.println("Invalid Admin Login");
        }
    }

    static void viewStatistics() {
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {

            ResultSet rs1 = con.createStatement().executeQuery("SELECT COUNT(*) FROM jobs");
            rs1.next();
            System.out.println("Total Jobs: " + rs1.getInt(1));

            ResultSet rs2 = con.createStatement().executeQuery("SELECT COUNT(*) FROM applications");
            rs2.next();
            System.out.println("Total Applications: " + rs2.getInt(1));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
