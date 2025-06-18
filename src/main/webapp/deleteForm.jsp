<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String no = request.getParameter("no");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>삭제 확인</title>
</head>
<body>

	<h2>방명록 삭제</h2>

	<form action="gbc?action=delete" method="post">
		<input type="hidden" name="no" value="<%= no %>">
		
		<table border="1" width="300px">
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="password" required></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button type="submit">삭제</button>
				</td>
			</tr>
		</table>
	</form>

	<br>
	<a href="gbc?action=list">[리스트로 돌아가기]</a>

</body>
</html>
