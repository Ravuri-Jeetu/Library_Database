import java.sql.*;
import java.util.*;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        String url = "jdbc:postgresql://localhost:5432/Library";
        String user = "postgres";
        String pass = "Ravuri@123";

        Class.forName("org.postgresql.Driver");

        Connection con = DriverManager.getConnection(url, user, pass);

        while(true){

            System.out.println("\nWelcome to Library!");
            System.out.println("Select the operation to perform:");
            System.out.println("1. Borrow a Book");
            System.out.println("2. Return a Book");
            System.out.println("3. Register a User");
            System.out.println("4. Delete a User");
            System.out.println("5. Show Borrowed Books");
            System.out.println("6. Exit");

            int ch = sc.nextInt();

            switch(ch){

                case 1:
                    System.out.println("Enter Book ID to borrow:");
                    int bookId = sc.nextInt();

                    System.out.println("Enter User ID:");
                    int userId = sc.nextInt();

                    String query = "INSERT INTO borrow(id, book_id) VALUES (?, ?)";
                    PreparedStatement ps1 = con.prepareStatement(query);
                    ps1.setInt(1, userId);
                    ps1.setInt(2, bookId);

                    int r1 = ps1.executeUpdate();

                    if(r1 > 0)
                        System.out.println("Book borrowed successfully");
                    else
                        System.out.println("Borrow failed");

                    break;

                case 2:
                    System.out.println("Enter Book ID to return:");
                    int id = sc.nextInt();

                    String Rquery = "DELETE FROM borrow WHERE book_id=?";
                    PreparedStatement ps2 = con.prepareStatement(Rquery);
                    ps2.setInt(1, id);

                    int r2 = ps2.executeUpdate();

                    if(r2 > 0)
                        System.out.println("Book returned");
                    else
                        System.out.println("Book not found");

                    break;

                case 3:
                    System.out.println("Enter User ID:");
                    int userID = sc.nextInt();

                    System.out.println("Enter Name:");
                    sc.nextLine();
                    String name = sc.nextLine();

                    String insertQuery = "INSERT INTO users VALUES (?, ?)";
                    PreparedStatement ps3 = con.prepareStatement(insertQuery);

                    ps3.setInt(1, userID);
                    ps3.setString(2, name);

                    ps3.executeUpdate();

                    System.out.println("User Created");

                    break;

                case 4:
                    System.out.println("Enter user ID to delete:");
                    int UserID_D = sc.nextInt();

                    String DQuery = "DELETE FROM users WHERE id=?";
                    PreparedStatement ps4 = con.prepareStatement(DQuery);
                    ps4.setInt(1, UserID_D);

                    int r4 = ps4.executeUpdate();

                    if(r4 > 0)
                        System.out.println("User Deleted");
                    else
                        System.out.println("User not found");

                    break;
                case 5:

                    String showQuery =
                            "SELECT u.user_name, b.book_name " +
                                    "FROM borrow br " +
                                    "JOIN users u ON br.user_id = u.user_id " +
                                    "JOIN books b ON br.book_id = b.book_id";

                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(showQuery);

                    boolean found = false;

                    while(rs.next()){
                        found = true;
                        String uname = rs.getString("user_name");
                        String bname = rs.getString("book_name");


                        System.out.println(uname + " borrowed " + bname);
                    }

                    if(!found){
                        System.out.println("No borrowed books found.");
                    }

                    break;

                case 6:
                    System.out.println("Exiting...");
                    con.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}