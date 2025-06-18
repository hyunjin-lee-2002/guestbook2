<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.javaex.vo.GuestbookVO" %>

<%
	List<GuestbookVO> guestList = (List<GuestbookVO>) request.getAttribute("gList");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>

		<!-- 등록폼 -->
		<form action="gbc?action=add" method="post">
			<table border="1" width="540px">
				<tr>
					<td>이름</td>
					<td>
						<input type="text" name="name" value="">
					</td>

					<td>비밀번호</td>
					<td>
						<input type="password" name="password" value="">
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea name="content" cols="72" rows="5"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<button type="submit">등록</button>
					</td>
				</tr>
			</table>
		</form>

		<br>

		<!-- 등록된 글 리스트 -->
		<%
			if (guestList != null) {
				for (GuestbookVO vo : guestList) {
		%>

		<table border="1" width="540px">
			<tr>
				<td>[<%= vo.getNo() %>]</td>
				<td><%= vo.getName() %></td>
				<td><%= vo.getRegDate() %></td>
				<td>
					<a href="gbc?action=deleteform&no=<%= vo.getNo() %>">삭제</a>
				</td>
			</tr>
			<tr>
				<td colspan="4"><%= vo.getContent().replaceAll("\n", "<br>") %></td>
			</tr>
		</table>

		<br>

		<%
				}
			}
		%>

	</body>
</html>
