<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	/////예제 테스트 코드입니다.
	//여기서 request.getParameter로 안드로이드에서 보낸 값들을 받습니다.
	//안드로이드에서 보낸 sendMsg = "id="+strings[0]+"&pwd="+strings[1]; 여기서
	// 키값과 request.getParameter안의 값이 같아야 합니다 ㅎㅎ 당연히 타입도 같아야 하구요.
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");

	// 다시 안드로이드로 어떠한 값을 보내고 싶을 때는 out.print를 사용하면 됩니다 ㅎㅎ	
	if (id.equals("rain483") && pwd.equals("1234")) {
		out.print("참 true");
	} else {
		out.print("거짓 false");
	}
%>

