<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="android_Dao.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="android_Domain.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.json.simple.*"%>
<%
	// 자바파일이 필요하므로 위 코드처럼 임포트합니다.
%>

<%
	//여기서 request.getParameter로 안드로이드에서 보낸 값들을 받습니다.
	//안드로이드에서 보낸 sendMsg = "id="+strings[0]+"&pwd="+strings[1]; 여기서
	// 키값과 request.getParameter안의 값이 같아야 합니다 ㅎㅎ 당연히 타입도 같아야 하구요.

	request.setCharacterEncoding("UTF-8");
	String latitude = request.getParameter("latitude");
	String longitude = request.getParameter("longitude");
	String radius = request.getParameter("radius");
	String name = request.getParameter("name");
	//String id_num = request.getParameter("id_num");

	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	String place = request.getParameter("place");
	String num = request.getParameter("num");
	String contents = request.getParameter("contents");
	String expiration = request.getParameter("expiration");
	String photourl = request.getParameter("photourl");
	String type = request.getParameter("type");

	String returns, sql;

	//로그인 요청인지 회원가입 요청인지를 구분하여 메서드를 실행하도록 합니다.
	Dao dao = new DaoFactory().dao();
	Apps sa = new Apps();
	JSONObject obj = new JSONObject();

	if (type.equals("maching")) {
		returns = dao.matching(latitude, longitude, radius, name);
		out.print(returns);
		System.out.println("매칭 요청 결과 : " + returns);
	} else if (type.equals("add")) {
		returns = dao.add(latitude, longitude, radius, name);
		out.print(returns);
		System.out.println("GpsDB추가 요청 결과 : " + returns);
	} else if (type.equals("Cadd")) {
		returns = dao.couponadd(id, place, contents, name, photourl);
		out.print(returns);
		System.out.println("Coupon추가 요청 결과 : " + returns);
	} else if (type.equals("C_use")) {
		returns = dao.useCoupon(id, place, num, expiration);
		out.print(returns);
		System.out.println("Coupon사용 요청 결과 : " + returns);
	} else if (type.equals("delete")) {
		returns = dao.delete(latitude, longitude);
		out.print(returns);
		System.out.println("Gps삭제 요청 결과 : " + returns);
	} else if (type.equals("C_delete")) {
		returns = dao.c_delete(id, contents);
		out.print(returns);
		System.out.println("Coupon삭제 요청 결과 : " + returns);
	} else if (type.equals("selectAll")) {
		System.out.println("Gps All요청 들어옴");
		sql = "select * from gps_data";
		//ArrayList<Gps_data> arraylist = sa.getDBAll(sql);
		obj = sa.getResult(sql);
		out.println(obj.toJSONString());
		out.flush();
		System.out.println("Gps All 요청 결과 : " + obj.toJSONString());
	} else if (type.equals("C_selectAll")) {
		System.out.println("Coupon All요청 들어옴");
		sql = "select * from coupon";
		//ArrayList<Coupon> arraylist = sa.getAll(sql);
		obj = sa.getCouponResult(sql);
		out.println(obj.toJSONString());
		out.flush();
		System.out.println("Coupon All요청 결과 : " + obj.toJSONString());
	} else if (type.equals("C_unUsedAll")) {
		System.out.println("C_unUsedAll 요청 들어옴");
		sql = "select * from coupon where num not in(select distinct num from 3cs.coupon "
				+ "where num =any(select usecoupon_num from usecoupon where id ='" + id + "')) "
				+ "and expiration >= date(now())";
		//ArrayList<Coupon> arraylist = sa.getAll(sql);
		obj = sa.getCouponResult(sql);
		out.println(obj.toJSONString());
		out.flush();
		System.out.println("C_unUsedAll 요청 결과 : " + obj.toJSONString());
	} else if (type.equals("C_findCoupon")) {
		System.out.println("C_unUsedAll 요청 들어옴");
		sql = "select * from coupon where num not in(select distinct num from 3cs.coupon "
				+ "where num =any(select usecoupon_num from usecoupon where id ='" + id + "'))"
				+ "and expiration >= date(now()) and place = '" + place + "'";
		//ArrayList<Coupon> arraylist = sa.getAll(sql);
		System.out.println(place);
		obj = sa.getCouponResult(sql);
		out.println(obj.toJSONString());
		out.flush();
		System.out.println("C_unUsedAll 요청 결과 : " + obj.toJSONString());
	} else if (type.equals("C_UsedAll")) {
		System.out.println("Used Coupon All요청 들어옴");
		sql = "select * from 3cs.coupon c Inner join 3cs.usecoupon u on c.num = u.usecoupon_num where u.id = "
				+ "'" + id + "'";
		//ArrayList<Coupon> arraylist = sa.getAll(sql);
		obj = sa.getCouponResult(sql);
		out.println(obj.toJSONString());
		out.flush();
		System.out.println("Used Coupon All요청 결과 : " + obj.toJSONString());
	} else if (type.equals("C_select")) {
		System.out.println("C_select 요청 들어옴");
		sql = "select * from coupon where id = " + "'" + id + "'";
	//	ArrayList<Coupon> arraylist = sa.getAll(sql);
		obj = sa.getCouponResult(sql);
		out.println(obj.toJSONString());
		out.flush();
		System.out.println("Coupon All요청 결과 : " + obj.toJSONString());
	} else if (type.equals("c_search")) {
		String searchPlace = "'" + "%" + place + "%" + "'";
		sql = "select * from coupon where place like" + searchPlace;
	//	ArrayList<Coupon> arraylist = sa.getAll(sql);
		obj = sa.getCouponResult(sql);
		out.println(obj.toJSONString());
		out.flush();
	} else if (type.equals("user_search")) {
		String searchPlace = "'" + "%" + place + "%" + "'";
		String userId = "'" + id + "'";
		sql = "select * from coupon where place like" + searchPlace + " and id =  " + userId;
	//	ArrayList<Coupon> arraylist = sa.getAll(sql);
		obj = sa.getCouponResult(sql);
		out.println(obj.toJSONString());
		out.flush();
	} else if (type.equals("search")) {
		String searchName = "'" + "%" + name + "%" + "'";
		sql = "select * from gps_data where name like" + searchName;
	//	ArrayList<Gps_data> arraylist = sa.getDBAll(sql);
		obj = sa.getResult(sql);
		out.println(obj.toJSONString());
		out.flush();
	}
	if (type.equals("login")) {
		returns = dao.logindb(id, pwd);
		out.print(returns);
		System.out.println("로그인 리턴되나?" + returns);
	} else if (type.equals("join")) {
		returns = dao.joindb(id, pwd);
		out.print(returns);
	}
	// 다시 안드로이드로 어떠한 값을 보내고 싶을 때는 out.print를 사용하면 됩니다 ㅎㅎ
%>


<%--
	//싱글톤 방식으로 자바 클래스를 불러옵니다.
	dao dao = dao.getInstance();
	String returns = dao.connectionDB("test", "1234");
	System.out.println("요청 결과 : " + returns);
--%>
