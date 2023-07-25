package sol.sv;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListServlet extends HttpServlet {
    private PreparedStatement getListStatement;
    private PreparedStatement insertStatement;
    
    
    @Override
    public void init() throws ServletException {
        try {
            if (Prop.conn == null || Prop.conn.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Prop.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:JAVA", "servlet", "java");
            }
            getListStatement = Prop.conn.prepareStatement("SELECT SEQ, NAME, ADDR, TO_CHAR(RDATE, 'YYYY-MM-DD') FROM ADDRESS");
            insertStatement = Prop.conn.prepareStatement("INSERT INTO ADDRESS VALUES (ADDRESS_SEQ.nextval, ?, ?, SYSDATE)");
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(getListHtml());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String name = req.getParameter("name");
        String addr = req.getParameter("addr");
        insertIntoList(name, addr);
        resp.getWriter().println(getListHtml());
    }

    private String getListHtml() throws ServletException {
        String rows = "";
        try {
            ResultSet rs = getListStatement.executeQuery();
            while (rs.next()) {
                rows += Prop.rowTemplate.formatted(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        return Prop.htmlTemplate.formatted(rows);
    }

    private void insertIntoList(String name, String addr) throws ServletException {
        try {
            insertStatement.setString(1, name);
            insertStatement.setString(2, addr);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    
}
