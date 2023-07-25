package sol.board;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardListServlet extends HttpServlet {
    private PreparedStatement getListStatement;

    @Override
    public void init() throws ServletException {
        try {
            if (Prop.conn == null || Prop.conn.isClosed()) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Prop.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:JAVA", "servlet", "java");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(getListHtml());
    }

    private String getListHtml() throws ServletException {
        String rows = "";
        try {
            if (getListStatement == null || getListStatement.isClosed()) {
                getListStatement = Prop.conn.prepareStatement(
                        "SELECT SEQ, WRITER, EMAIL, SUBJECT, TO_CHAR(RDATE, 'YYYY-MM-DD') FROM BOARD ORDER BY SEQ DESC");
            }
            ResultSet rs = getListStatement.executeQuery();
            while (rs.next()) {
                int seq = rs.getInt(1);
                rows += Prop.rowTemplate.formatted(seq, rs.getString(2), rs.getString(3), seq,
                rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        return Prop.listTemplate.formatted(rows);
    }

}
