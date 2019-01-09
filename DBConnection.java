import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;

import javax.imageio.IIOException;
import javax.xml.parsers.ParserConfigurationException;

public class DBConnection {
    // public static void main(String []args) throws ParserConfigurationException, IIOException {
    //     String username = "root";
    //     String password = "3190";
    //     String connectionUrl = "jdbc:mysql://localhost:3306/testdb";

    //     DriverManager.registerDriver(new com.mysql.jdbcDriver());
    //     Class.forName("com.mysql.jdbc.Driver");
    //     // try(Connection connection = DriverManager.getConnection(connectionUrl, username, password)) {
    //     //     System.out.println("We're connected");
    //     // }
    //     try (Connection connection = DriverManager.getConnection(connectionUrl, username, password); 
    //             Statement statement = connection.createStatement()) {
    //         System.out.println("We're connected");

    //         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            
    //         Date setDate = dateFormat.parse("2009-12-31");
    //         String query = "Select * from websitebase where date > "+ dateFormat.format(setDate);
    //         Resultset resultSet = statement.executeQuery(query);

    //     }
    //     finally {
    //         connection.close();
    //     }
    // }

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/testdb";
    private static final String user = "root";
    private static final String password = "3190";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static PreparedStatement pstmt;


    public static void main(String args[]) throws Exception {

        String dateString = "2018-12-31";
        java.sql.Date setDate = java.sql.Date.valueOf(dateString);

        //getting all the websites that haven't been updated sinse "setDate"
        String query = "select * from websitebase where date < "+ setDate;

        //getting current date
        java.util.Date date= Date.valueOf(LocalDate.now());
        java.sql.Date today = new java.sql.Date(date.getTime());

        String sqlUpdate = "UPDATE candidates "
                            + "SET status = ? "
                            + "SET date = ? "
                            + "WHERE id = ?";

        try {

            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            pstmt = con.prepareStatement(sqlUpdate);

            while (rs.next()) {

                int id = rs.getInt("id");
                int status = URLResponse.getResponseCode(rs.getString("url"));

                //setting new values fow selected rows
                pstmt.setInt(1, status);
                pstmt.setDate(2, today);
                pstmt.setInt(3, id);

                //updating specific rows
                int rowAffected = pstmt.executeUpdate();
                System.out.println(String.format("Row affected %d", rowAffected));
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            
        } finally {
            try { con.close(); } catch(SQLException se) { /*smth went wrong */ }
            try { stmt.close(); } catch(SQLException se) { /*smth went wrong */ }
            try { rs.close(); } catch(SQLException se) { /*smth went wrong */ }
        }
    }
}