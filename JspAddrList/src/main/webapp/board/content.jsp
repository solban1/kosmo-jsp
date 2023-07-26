<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.sql.*"%>
<%!Connection conn = null;
	PreparedStatement getContentStatement = null;%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
}

th, td {
	padding: 5px;
}

a {
	text-decoration: none
}
</style>
</head>
<body>
	<%
	String seq = request.getParameter("seq");
	String writer = null;
	String email = null;
	String subject = null;
	String content = null;
	try {
		if (conn == null || conn.isClosed()) {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:JAVA", "servlet", "java");
		}
	} catch (ClassNotFoundException | SQLException e) {
		throw new ServletException(e);
	}
	try {
		if (getContentStatement == null || getContentStatement.isClosed()) {
			getContentStatement = conn
			.prepareStatement("SELECT SEQ, WRITER, EMAIL, SUBJECT, CONTENT FROM BOARD WHERE SEQ=?");
		}
		getContentStatement.setString(1, seq);
		ResultSet rs = getContentStatement.executeQuery();
		rs.next();
		writer = rs.getString(2);
		email = rs.getString(3);
		subject = rs.getString(4);
		content = rs.getString(5);
	} catch (SQLException e) {
		throw new ServletException(e);
	}
	%>
	<center>
		<hr width='600' size='2' noshade>
		<h2>Simple Board with Servlet</h2>
		&nbsp;&nbsp;&nbsp; <a href='write'>글쓰기</a>
		<hr width='600' size='2' noshade>
		<table border='1' width='600' align='center' cellpadding='3'>
			<tr>
				<td width='100' align='center'>글번호</td>
				<td><%=seq%></td>
			</tr>
			<tr>
				<td align='center'>글쓴이</td>
				<td><%=writer%></td>
			</tr>
			<tr>
				<td align='center'>이메일</td>
				<td><%=email%></td>
			</tr>
			<tr>
				<td align='center'>글제목</td>
				<td><%=subject%></td>
			</tr>
			<tr>
				<td align='center'>글내용</td>
				<td><%=content%></td>
			</tr>
		</table>
		<hr width='600' size='2' noshade>
		<b> <a href='write.jsp?seq=<%=seq%>'>수정</a> | <a
			href='update.jsp?type=del&seq=<%=seq%>'>삭제</a> | <a href='list'>목록</a>
		</b>
		<hr width='600' size='2' noshade>
	</center>
</body>
</html>