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

public class BoardContentServlet extends HttpServlet {
    private PreparedStatement getContentStatement;

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
        String seq = req.getParameter("seq");
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(getContentHtml(seq));
    }

    private String getContentHtml(String seq) throws ServletException {
        try {
            if (getContentStatement == null || getContentStatement.isClosed()) {
                getContentStatement = Prop.conn
                        .prepareStatement("SELECT SEQ, WRITER, EMAIL, SUBJECT, CONTENT FROM BOARD WHERE SEQ=?");
            }
            getContentStatement.setString(1, seq);
            ResultSet rs = getContentStatement.executeQuery();
            rs.next();
            return Prop.contentTemplate.formatted(seq, rs.getString(2), rs.getString(3), rs.getString(4),
                    rs.getString(5), seq, seq);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

}
