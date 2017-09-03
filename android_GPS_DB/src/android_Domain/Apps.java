package android_Domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android_Dao.DBConn;

public class Apps {
	ResultSet rs;
	DBConn dbc = new DBConn();

	public ArrayList<Gps_data> getDBAll(String sql) {
		ArrayList<Gps_data> arraylist = new ArrayList<Gps_data>();
		try {
			rs = dbc.getRs(sql);
			while (rs.next()) {

				Gps_data gps = new Gps_data();
				gps.setLatitude(rs.getDouble("latitude"));
				gps.setLongitude(rs.getDouble("longitude"));
				gps.setRadius(rs.getInt("radius"));
				gps.setName(rs.getString("name"));
				gps.setId_num(rs.getInt("id_num"));
				arraylist.add(gps);
			}
		} catch (SQLException e) {
			System.out.println("SQL state:" + e.getSQLState());
		}
		return arraylist;
	}

	public ArrayList<Coupon> getAll(String sql) {
		ArrayList<Coupon> arraylist = new ArrayList<Coupon>();
		try {
			System.out.println(sql);
			rs = dbc.getRs(sql);
			while (rs.next()) {

				Coupon coupon = new Coupon();
				coupon.setPlace(rs.getString("place"));
				coupon.setNum(rs.getInt("num"));
				coupon.setId(rs.getInt("id"));
				coupon.setWritedate(rs.getString("writedate"));
				coupon.setExpireation(rs.getString("expireation"));
				coupon.setContents(rs.getString("contents"));
				coupon.setContents(rs.getString("photourl"));

				arraylist.add(coupon);
			}
		} catch (SQLException e) {
			System.out.println("SQL state:" + e.getSQLState());
		}
		return arraylist;
	}

	public JSONObject getResult(String sql) throws ClassNotFoundException, SQLException {
		JSONObject jMain = new JSONObject(); /* 총 데이터를 담는 MAIN */
		JSONArray jArray = new JSONArray(); /* row별로 데이터를 담을 배열 */
		JSONObject jObject;

		rs = dbc.getRs(sql);
		while (rs.next()) {
			jObject = new JSONObject();
			jObject.put("latitude", rs.getDouble("latitude"));
			jObject.put("longitude", rs.getDouble("longitude"));
			jObject.put("radius", rs.getInt("radius"));
			jObject.put("name", rs.getString("name"));
			jObject.put("id_num", rs.getInt("id_num"));
			// jObject.put("contents",rs.getString("contents"));

			jArray.add(jObject);
		}
		jMain.put("sendData", jArray);

		return jMain;
	}

	public JSONObject getCouponResult(String sql) throws ClassNotFoundException, SQLException {
		JSONObject jMain = new JSONObject(); /* 총 데이터를 담는 MAIN */
		JSONArray jArray = new JSONArray(); /* row별로 데이터를 담을 배열 */
		JSONObject jObject;

		rs = dbc.getRs(sql);
		while (rs.next()) {
			jObject = new JSONObject();
			jObject.put("place", rs.getString("place"));
			jObject.put("num", rs.getInt("num"));
			jObject.put("writedate", rs.getString("writedate"));
			jObject.put("expiration", rs.getString("expiration"));
			jObject.put("id", rs.getString("id"));
			jObject.put("contents", rs.getString("contents"));
			jObject.put("photourl", rs.getString("photourl"));
			jArray.add(jObject);
		}
		jMain.put("sendData", jArray);

		return jMain;
	}

}
