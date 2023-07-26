<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.sql.*"%>
<%!private Connection conn;
	private PreparedStatement getListStatement;%>
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
	<center>
		<hr width='600' size='2' noshade>
		<h2>Simple Board with Servlet</h2>
		&nbsp;&nbsp;&nbsp; <a href='write.jsp'>글쓰기</a> &nbsp;&nbsp;&nbsp; <a
			href='../'>인덱스</a>
		<hr width='600' size='2' noshade>
	</center>
	<table border='1' width='600' align='center' cellpadding='2'>
		<tr>
			<th align='center' width='10%'>글번호</th>
			<th align='center' width='15%'>작성자</th>
			<th align='center' width='30%'>이메일</th>
			<th align='center' width='30%'>글제목</th>
			<th align='center' width='15%'>날짜</th>
		</tr>
		<%
		String rows = "";
		try {
			if (conn == null || conn.isClosed()) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:JAVA", "servlet", "java");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new ServletException(e);
		}
		try {
			if (getListStatement == null || getListStatement.isClosed()) {
				getListStatement = conn.prepareStatement(
				"SELECT SEQ, WRITER, EMAIL, SUBJECT, TO_CHAR(RDATE, 'YYYY-MM-DD') FROM BOARD ORDER BY SEQ DESC");
			}
			ResultSet rs = getListStatement.executeQuery();
			while (rs.next()) {
				int seq = rs.getInt(1);
		%>
		<tr>
			<td align='center'><%=seq%></td>
			<td align='center'><%=rs.getString(2)%></td>
			<td align='center'><%=rs.getString(3)%></td>
			<td align='center'><a href='content.jsp?seq=<%=seq%>'><%=rs.getString(4)%></a></td>
			<td align='center'><%=rs.getString(5)%></td>
		</tr>
		<%
		}
		} catch (SQLException e) {
		throw new ServletException(e);
		}
		%>
	</table>
	<hr width='600' size='2' noshade>
</body>
</html>