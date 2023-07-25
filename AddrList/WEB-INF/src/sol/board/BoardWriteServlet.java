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

public class BoardWriteServlet extends HttpServlet {
    private PreparedStatement getNameStatement;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String seq = req.getParameter("seq");
        resp.setContentType("text/html;charset=utf-8");
        if (seq == null) { // new
            resp.getWriter().println(Prop.writeTemplate.formatted("new", "", "", "", "", ""));
        } else { // edit
            try {
                if (Prop.conn == null || Prop.conn.isClosed()) {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Prop.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:JAVA", "servlet", "java");
                }
                if (getNameStatement == null || getNameStatement.isClosed()) {
                    getNameStatement = Prop.conn
                            .prepareStatement("SELECT WRITER, EMAIL, SUBJECT, CONTENT FROM BOARD WHERE SEQ=?");
                }
                getNameStatement.setString(1, seq);
                ResultSet rs = getNameStatement.executeQuery();
                rs.next();
                resp.getWriter().println(Prop.writeTemplate.formatted("edit&seq=" + seq, rs.getString(1), "disabled",
                        rs.getString(2), rs.getString(3), rs.getString(4)));
            } catch (ClassNotFoundException | SQLException e) {
                throw new ServletException(e);
            }

        }

    }

}
