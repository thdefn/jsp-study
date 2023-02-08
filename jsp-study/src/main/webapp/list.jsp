<%@page import="db.Member"%>
<%@page import="java.util.List"%>
<%@page import="db.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
		width: 100%;
	}
	th, td{
		border:solid 1px #000;
	}
</style>
</head>
<body>

<%
	
	MemberService memberService = new MemberService();
    List<Member> memberList = memberService.list();
    
	%>


<h1>회원 목록</h1>
	<table>
		<thead>
			<tr>
				<th>회원구분</th>
				<th>아이디</th>
				<th>비밀번호</th>
				<th>이름</th>
			</tr>
		</thead>
	
		<tbody>
			<tr>
			<%
	
    
    for(Member member : memberList){
    	//태그 형태로 출력하기 위함 블록태그로 만들자
    	out.write("<tr>");
    	 out.write("<td>"+ member.getMemberType()+"</td>");
    	 out.write("<td>"+member.getUserId()+"</td>");
    	 out.write("<td>"+member.getPassword()+"</td>");
    	 out.write("<td>"+member.getName()+"</td>");
    	 out.write("</tr>");
    }
    
	%>
		
			</tr>
		</tbody>
	
	</table>

</body>
</html>