package sol.sv;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DelServlet extends HttpServlet {
    private PreparedStatement deleteStatement;

    @Override
    public void init() throws ServletException {
        try {
            if (Prop.conn == null || Prop.conn.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Prop.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:JAVA", "servlet", "java");
            }
            deleteStatement = Prop.conn.prepareStatement("DELETE FROM ADDRESS WHERE SEQ=?");
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            deleteStatement.setString(1, req.getParameter("seq"));
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        resp.sendRedirect("list");
    }
    
}
