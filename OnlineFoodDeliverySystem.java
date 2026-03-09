package sqlDemo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class OnlineFoodDeliverySystem {

    static final String URL = "jdbc:mysql://localhost:3306/foodDB";
    static final String USER = "root";
    static final String PASSWORD = "Shivani@123";

    public static void main(String[] args) {

        try {

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement st = con.createStatement();

            // ---------- CREATE TABLES ----------

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Customers("
                    + "customer_id INT PRIMARY KEY,"
                    + "name VARCHAR(50),"
                    + "city VARCHAR(50),"
                    + "phone VARCHAR(15))");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Restaurants("
                    + "restaurant_id INT PRIMARY KEY,"
                    + "restaurant_name VARCHAR(50),"
                    + "city VARCHAR(50),"
                    + "rating FLOAT)");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS Food_Items("
                    + "food_id INT PRIMARY KEY,"
                    + "food_name VARCHAR(50),"
                    + "price INT,"
                    + "restaurant_id INT,"
                    + "FOREIGN KEY (restaurant_id) REFERENCES Restaurants(restaurant_id))");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS  ("
                    + "order_id INT PRIMARY KEY,"
                    + "customer_id INT,"
                    + "food_id INT,"
                    + "quantity INT,"
                    + "order_date DATE,"
                    + "FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),"
                    + "FOREIGN KEY (food_id) REFERENCES Food_Items(food_id))");


            // ---------- INSERT SAMPLE DATA ----------

            st.executeUpdate("INSERT INTO Customers VALUES (1,'Anita','Bangalore','9876543210')");
            st.executeUpdate("INSERT INTO Customers VALUES (2,'Rahul','Chennai','9876543211')");
            st.executeUpdate("INSERT INTO Customers VALUES (3,'Aman','Delhi','9876543212')");

            st.executeUpdate("INSERT INTO Restaurants VALUES (1,'Dominos','Bangalore',4.5)");
            st.executeUpdate("INSERT INTO Restaurants VALUES (2,'Pizza Hut','Chennai',4.2)");
            st.executeUpdate("INSERT INTO Restaurants VALUES (3,'Udupi Hotel','Bangalore',4.0)");

            st.executeUpdate("INSERT INTO Food_Items VALUES (1,'Veg Pizza',250,1)");
            st.executeUpdate("INSERT INTO Food_Items VALUES (2,'Cheese Burger',150,2)");
            st.executeUpdate("INSERT INTO Food_Items VALUES (3,'Masala Dosa',120,3)");
            st.executeUpdate("INSERT INTO Food_Items VALUES (4,'Paneer Pizza',300,2)");

            st.executeUpdate("INSERT INTO Orders VALUES (1,1,1,2,'2026-03-05')");
            st.executeUpdate("INSERT INTO Orders VALUES (2,2,3,1,'2026-03-05')");


            // ---------- TASK 1 : SELECT ----------

            System.out.println("\nAll Food Items:");
            ResultSet rs = st.executeQuery("SELECT * FROM Food_Items");

            while(rs.next()){
                System.out.println(rs.getInt("food_id")+" "
                        +rs.getString("food_name")+" "
                        +rs.getInt("price"));
            }


            // ---------- TASK 2 : WHERE ----------

            System.out.println("\nFood Items costing more than 200:");

            rs = st.executeQuery("SELECT * FROM Food_Items WHERE price > 200");

            while(rs.next()){
                System.out.println(rs.getString("food_name")+" "
                        +rs.getInt("price"));
            }


            // ---------- TASK 3 : AND ----------

            System.out.println("\nFood price >150 AND restaurant_id = 2");

            rs = st.executeQuery(
                    "SELECT * FROM Food_Items WHERE price >150 AND restaurant_id =2");

            while(rs.next()){
                System.out.println(rs.getString("food_name")+" "
                        +rs.getInt("price"));
            }


            // ---------- TASK 3 : OR ----------

            System.out.println("\nRestaurants in Chennai OR Bangalore");

            rs = st.executeQuery(
                    "SELECT * FROM Restaurants WHERE city='Chennai' OR city='Bangalore'");

            while(rs.next()){
                System.out.println(rs.getString("restaurant_name")+" "
                        +rs.getString("city"));
            }


            // ---------- TASK 4 : LIKE ----------

            System.out.println("\nCustomers starting with A");

            rs = st.executeQuery(
                    "SELECT * FROM Customers WHERE name LIKE 'A%'");

            while(rs.next()){
                System.out.println(rs.getString("name"));
            }


            System.out.println("\nFood items containing Pizza");

            rs = st.executeQuery(
                    "SELECT * FROM Food_Items WHERE food_name LIKE '%Pizza%'");

            while(rs.next()){
                System.out.println(rs.getString("food_name"));
            }


            // ---------- TASK 5 : BETWEEN ----------

            System.out.println("\nFood price between 100 and 300");

            rs = st.executeQuery(
                    "SELECT * FROM Food_Items WHERE price BETWEEN 100 AND 300");

            while(rs.next()){
                System.out.println(rs.getString("food_name")+" "
                        +rs.getInt("price"));
            }


            // ---------- TASK 6 : ORDER BY ----------

            System.out.println("\nFood items sorted by price (High → Low)");

            rs = st.executeQuery(
                    "SELECT * FROM Food_Items ORDER BY price DESC");

            while(rs.next()){
                System.out.println(rs.getString("food_name")+" "
                        +rs.getInt("price"));
            }

            con.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
