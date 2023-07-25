package sol.board;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BoardUpdateServlet extends HttpServlet {
    private PreparedStatement insertStmt, updateStmt, deleteStmt;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type == null) {
            resp.sendError(400, "type 파라미터가 지정되지 않았습니다.");
            return;
        }

        String writer = req.getParameter("writer");
        String email = req.getParameter("email");
        String subject = req.getParameter("subject");
        String content = req.getParameter("content");

        if (type.equals("new")) { // insert
            try {
                if (insertStmt == null || insertStmt.isClosed()) {
                    insertStmt = Prop.conn.prepareStatement(
                            "INSERT INTO BOARD (SEQ, WRITER, EMAIL, SUBJECT, CONTENT, RDATE) VALUES (BOARD_SEQ.nextval, ?, ?, ?, ?, SYSDATE)");
                }
                insertStmt.setString(1, writer);
                insertStmt.setString(2, email);
                insertStmt.setString(3, subject);
                insertStmt.setString(4, content);
                insertStmt.executeUpdate();
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        } else {
            String seq = req.getParameter("seq");
            if (seq == null) {
                resp.sendError(400, "seq 파라미터가 지정되지 않았습니다.");
                return;
            }
            if (type.equals("edit")) { // update
                try {
                    if (updateStmt == null || updateStmt.isClosed()) {
                        updateStmt = Prop.conn
                                .prepareStatement("UPDATE BOARD SET EMAIL=?, SUBJECT=?, CONTENT=? WHERE SEQ=?");
                    }
                    updateStmt.setString(1, email);
                    updateStmt.setString(2, subject);
                    updateStmt.setString(3, content);
                    updateStmt.setString(4, seq);
                    updateStmt.executeUpdate();
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            // } else if (type.equals("del")) { // delete
            //     try {
            //         if (deleteStmt == null || deleteStmt.isClosed()) {
            //             deleteStmt = Prop.conn.prepareStatement("DELETE FROM BOARD WHERE SEQ=?");
            //         }
            //         deleteStmt.setString(1, seq);
            //         deleteStmt.executeUpdate();
            //     } catch (SQLException e) {
            //         throw new ServletException(e);
            //     }
            } else {
                resp.sendError(400, "type 파라미터가 잘못되었습니다.");
                return;
            }
        }

        resp.sendRedirect("./list");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getParameter("type").equals("del")) {
            resp.sendError(400, "type 파라미터가 잘못되었습니다.");
            return;
        }

        String seq = req.getParameter("seq");
        try {
            if (deleteStmt == null || deleteStmt.isClosed()) {
                deleteStmt = Prop.conn.prepareStatement("DELETE FROM BOARD WHERE SEQ=?");
            }
            deleteStmt.setString(1, seq);
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        resp.sendRedirect("./list");
    }

}
