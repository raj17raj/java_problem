import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login_servlet")
public class login_servlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/simran";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rootraj";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//        	Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            
            // Verify username and password in the database
            String sql = "SELECT * FROM customer WHERE name=? AND password=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // User exists, redirect to profile.jsp
                response.sendRedirect("profile.jsp");
            } else {
                // User not found, display error message
                out.println("<h2>User not found</h2>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            // Handle errors
            e.printStackTrace();
            // Redirect to an error page or display an error message
            out.println("<h2>Database error</h2>");
        } 
    }
}
