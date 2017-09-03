package android_Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class Dao {
	private ConnectionMaker connectionMaker;
	
	public Dao() {
	}

	public Dao(ConnectionMaker connectionMaker) {
		this.connectionMaker=connectionMaker;
	}
/*	// 싱글톤 패턴으로 사용 하기위 한 코드들
	private static Dao instance = new Dao();

	public static Dao getInstance() {
		return instance;
	}*/

	String drv = "com.mysql.jdbc.Driver";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private PreparedStatement pstmt2 = null;
	private ResultSet rs = null;
	private String sql = "";
	private String sql2 = "";
	String returns = "";

	public String joindb(String id, String pwd) {
		try {
			System.out.println("회원가입에 " + id + " " + pwd + "가 들어옴");
			Class.forName(drv);
			conn = connectionMaker.makeConnection();
			sql = "select id from client where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id").equals(id)) { // 이미 아이디가 있는 경우
					returns = "id";
				}
			} else { // 입력한 아이디가 없는 경우
				sql2 = "insert into client values(?,?,?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, pwd);
				pstmt2.setInt(3, 1);
				pstmt2.executeUpdate();
				returns = "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String logindb(String id, String pwd) {
		System.out.println("로그인에 " + id + " " + pwd + "가 들어옴");
		try {
			Class.forName(drv);
			conn = connectionMaker.makeConnection();
			sql = "select * from client where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id").equals(id) && rs.getString("pwd").equals(pwd)) {
					returns = String.valueOf(rs.getInt("class"));// 로그인 가능
					// returns = "true";// 로그인 가능
				} else {
					returns = "false"; // 로그인 실패
				}
			} else {
				returns = "noId"; // 아이디 또는 비밀번호 존재 X
			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}
	public String useCoupon(String id, String place, String num,String expiration) {
		try {
			System.out.println("useCoupon메소드  "+id+"  " +place+"  "+num+"  "+expiration);
			Class.forName(drv);
			conn = connectionMaker.makeConnection();
			sql = "select * from usecoupon where id = ?  and usecoupon_num= ? and place = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, num);
			pstmt.setString(3, place);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("usecoupon_num").equals(num)&&rs.getString("place").equals(place)&&rs.getString("id").equals(id)) {
					returns = "false"; 
				} 
			} else {
				sql2 = "insert into usecoupon (usecoupon_num,id,place,expiration) values(?,?,?,?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, num);
				pstmt2.setString(2, id);
				pstmt2.setString(3, place);
				pstmt2.setString(4, expiration);
				pstmt2.executeUpdate();
				// returns = "추가 ok";
				returns = "ok";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}
	public String couponadd(String id, String place, String contents, String name,String photourl) {
		Calendar dateIn = Calendar.getInstance();
		int year = dateIn.get(Calendar.YEAR);
		int month = dateIn.get(Calendar.MONTH) + 1;
		int day = dateIn.get(Calendar.DATE);
		int exmonth = (month - 1 + 1) % 12 + 1;
		int exyear = year;
		if (exmonth == 1)
			exyear++;
		String expiration = exyear + "-" + exmonth + "-" + day;
		String writedata = year + "-" + month + "-" + day;

		int num = (int) (Math.random() * 100000 + 100000);

		try {
			Class.forName(drv);
			conn = connectionMaker.makeConnection();
			sql = "select * from coupon where num= ? and id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, id);
			// pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("num").equals(num)) {
					num = (int) (Math.random() * 100000 + 1);
				} else {
					returns = "false"; 
				}
			} else {
				sql2 = "insert into coupon (place,num,writedate,expiration,id,contents,photourl) values(?,?,?,?,?,?,?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, place);
				pstmt2.setInt(2, num);
				pstmt2.setString(3, writedata);
				pstmt2.setString(4, expiration);
				pstmt2.setString(5, id);
				pstmt2.setString(6, contents);
				pstmt2.setString(7, photourl);
				pstmt2.executeUpdate();
				// returns = "추가 ok";
				returns = "ok";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	// 데이터베이스와 통신하기 위한 코드가 들어있는 메서드
	public String add(String latitude, String longitude, String radius, String name) {
		try {
			Class.forName(drv);
			conn = connectionMaker.makeConnection();
			sql = "select latitude,longitude  from gps_data where latitude=? and longitude=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, latitude);
			pstmt.setString(2, longitude);
			rs = pstmt.executeQuery();
			System.out.println("위도 :  " + latitude);
			System.out.println("경도 : " + longitude);
			System.out.println("반경 : " + radius);
			System.out.println("이름 : " + name + " 가들어옴");

			if (rs.next()) {
				if (rs.getString("latitude").equals(latitude) && rs.getString("longitude").equals(longitude)) { // 이미
																												// 해당
																												// 위도
																												// 있는
					// 경우
					returns = "exist";
					// returns="위도가 있다.";
					System.out.println("좌표가 있다");
				}
			} else { // 입력한 위도 경도가 없는 경우
				System.out.println("추가 할것이다");
				sql2 = "insert into gps_data (latitude,longitude,radius,name) values(?,?,?,?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, latitude);
				pstmt2.setString(2, longitude);
				pstmt2.setString(3, radius);
				pstmt2.setString(4, name);
				pstmt2.executeUpdate();
				// returns = "추가 ok";
				returns = "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String matching(String latitude, String longitude, String radius, String name) {

		try {
			Class.forName(drv);
			conn =connectionMaker.makeConnection();
			sql = "select latitude,longitude from gps_data where latitude=? and longitude=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, latitude);
			pstmt.setString(2, longitude);

			rs = pstmt.executeQuery();
			if (rs.next()) {

				if (rs.getString("latitude").equals(latitude) && rs.getString("longitude").equals(longitude)) {
					returns = "true";// 매칭 성공
					System.out.println(latitude + " " + longitude + "가 들어옴");
				} else {
					returns = "false"; // 매칭 실패
					System.out.println(latitude + " " + longitude + "가 들어와서 실패");
				}
			} else {
				returns = "noData"; // 위도 또는 경도 존재 X
			}

		} catch (Exception e) {

		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String delete(String latitude, String longitude) {

		try {
			Class.forName(drv);
			conn =connectionMaker.makeConnection();
			sql = "select latitude,longitude from gps_data where latitude=? and longitude=? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, latitude);
			pstmt.setString(2, longitude);
			// System.out.println(latitude + " " + longitude + "가 들어옴");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (String.valueOf(rs.getDouble("latitude")).equals(latitude)
						&& String.valueOf(rs.getDouble("longitude")).equals(longitude)) {
					sql2 = "delete from gps_data where latitude=? and longitude=?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, latitude);
					pstmt2.setString(2, longitude);
					pstmt2.executeUpdate();
					returns = "delete";

				} else {
					returns = "false"; // 매칭 실패
					System.out.println("rsString위도 : " + String.valueOf(rs.getString("latitude")) + "  rsString경도 : "
							+ String.valueOf(rs.getString("longitude")) + "가 들어와서 실패");
					System.out.println("rs위도gets : " + rs.getString("latitude") + "  경도 : " + rs.getString("longitude")
							+ "가 들어와서 실패");
					System.out.println("rs위도getDouble : " + rs.getDouble("latitude") + "  경도 : "
							+ rs.getDouble("longitude") + "가 들어와서 실패");
					System.out.println("위도 : " + latitude + "  경도 : " + longitude + "가 들어와서 실패");
				}
			} else {
				returns = "noData"; // 위도 또는 경도 존재 X
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;

	}
	public String c_delete(String id, String contents) {
		try {
			System.out.println(id+"  "+contents);
			Class.forName(drv);
			conn = connectionMaker.makeConnection();
			sql = "select * from coupon where id= ? and contents= ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, contents);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("받아온값"+rs.getString("id")+rs.getString("contents"));
				if (id.equals(rs.getString("id"))) {
					sql2 = "delete from coupon where id=? and contents=?";
					pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setString(1, id);
					pstmt2.setString(2, contents);
					pstmt2.executeUpdate();
					returns = "delete";

				} else {
					returns = "false";
				}
			} else {
				returns = "noData";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;

	}
}
