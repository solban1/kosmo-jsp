<%@ page language="java" contentType="text/plain; charset=UTF-8"
	pageEncoding="UTF-8" import="java.sql.*"%>
<%!private Connection conn;
	private PreparedStatement insertStmt, updateStmt, deleteStmt;%>
<%
String type = request.getParameter("type");
if (type == null) {
	response.sendError(400, "type 파라미터가 지정되지 않았습니다.");
	return;
}

String writer = request.getParameter("writer");
String email = request.getParameter("email");
String subject = request.getParameter("subject");
String content = request.getParameter("content");

try {
    if (conn == null || conn.isClosed()) {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:JAVA", "servlet", "java");
    }
} catch (ClassNotFoundException | SQLException e) {
    throw new ServletException(e);
}

if (type.equals("new")) { // insert
	try {
		if (insertStmt == null || insertStmt.isClosed()) {
	insertStmt = conn.prepareStatement(
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
	String seq = request.getParameter("seq");
	if (seq == null) {
		response.sendError(400, "seq 파라미터가 지정되지 않았습니다.");
		return;
	}
	if (type.equals("edit")) { // update
		try {
	if (updateStmt == null || updateStmt.isClosed()) {
		updateStmt = conn.prepareStatement("UPDATE BOARD SET EMAIL=?, SUBJECT=?, CONTENT=? WHERE SEQ=?");
	}
	updateStmt.setString(1, email);
	updateStmt.setString(2, subject);
	updateStmt.setString(3, content);
	updateStmt.setString(4, seq);
	updateStmt.executeUpdate();
		} catch (SQLException e) {
	throw new ServletException(e);
		}
	} else if (type.equals("del")) { // delete
		try {
	if (deleteStmt == null || deleteStmt.isClosed()) {
		deleteStmt = conn.prepareStatement("DELETE FROM BOARD WHERE SEQ=?");
	}
	deleteStmt.setString(1, seq);
	deleteStmt.executeUpdate();
		} catch (SQLException e) {
	throw new ServletException(e);
		}
	} else {
		response.sendError(400, "type 파라미터가 잘못되었습니다.");
		return;
	}
}

response.sendRedirect("./list.jsp");
%>