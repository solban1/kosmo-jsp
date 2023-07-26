<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.sql.*"%>
<%!private Connection conn;
	private PreparedStatement getNameStatement;%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script language="javascript">
	function check() {
		for (var i = 0; i < document.input.elements.length; i++) {
			if (document.input.elements[i].value == "") {
				alert("모든 값을 입력 하셔야 합니다. ");
				return false;
			}
		}
		document.input.submit();
	}
</script>
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
<body onload="input.name.focus()">
	<%
	String seq = request.getParameter("seq");
	String type = "new";
	String disabled = "";
	String writer = "";
	String email = "";
	String subject = "";
	String content = "";
	if (seq != null) {
		try {
			if (conn == null || conn.isClosed()) {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:JAVA", "servlet", "java");
			}
			if (getNameStatement == null || getNameStatement.isClosed()) {
		getNameStatement = conn.prepareStatement("SELECT WRITER, EMAIL, SUBJECT, CONTENT FROM BOARD WHERE SEQ=?");
			}
			getNameStatement.setString(1, seq);
			ResultSet rs = getNameStatement.executeQuery();
			rs.next();
			type = "edit&seq=" + seq;
			disabled = "disabled";
			writer = rs.getString(1);
			email = rs.getString(2);
			subject = rs.getString(3);
			content = rs.getString(4);
		} catch (ClassNotFoundException | SQLException e) {
			throw new ServletException(e);
		}
	}
	%>
	<center>
		<hr width="600" size="2" noshade>
		<h2>Simple Board with Servlet</h2>
		<a href='list'>글목록</a>
		<hr width="600" size="2" noshade>
	</center>
	<form name="input" method="post" action="update.jsp?type=<%=type%>">
		<table border="1" width="600" align="center" cellpadding="3"
			cellspacing="1">
			<tr>
				<td width="30%" align="center">글쓴이</td>
				<td align="center"><input type="text" name="writer" size="60"
					value="<%=writer%>" <%=disabled%>></td>
			</tr>
			<tr>
				<td align="center">이메일</td>
				<td align="center"><input type="text" name="email" size="60"
					value="<%=email%>"></td>
			</tr>
			<tr>
				<td align="center">글제목</td>
				<td align="center"><input type="text" name="subject" size="60"
					value="<%=subject%>"></td>
			</tr>
			<tr>
				<td align="center">글내용</td>
				<td align="center"><textarea name="content" rows="5" cols="53"><%=content%></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" value="전송"
					onclick="check()"> <input type="reset" value="다시입력">
				</td>
			</tr>
		</table>
		<br>
		<hr width="600" size="2" noshade>
	</form>
</body>
</html>