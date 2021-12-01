package com.nadri.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.nadri.vo.RestaurantVo;

public class RestaurantDAOImpl implements RestaurantDAO {

	// Restaurant 정보
	public RestaurantVo getRestaurant(RestaurantVo vo) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;
		RestaurantVo RestaurantVo = new RestaurantVo();

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "select r_name, r_content, r_address, r_add, r_image_url from Restaurant where r_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, vo.getRestaurantId());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				RestaurantVo.setRestaurantName(rs.getString(1));
				RestaurantVo.setRestaurantContent(rs.getString(2));
				RestaurantVo.setRestaurantAddress(rs.getString(3));
				RestaurantVo.setRestaurantAdd(rs.getInt(4));
				RestaurantVo.setRestaurantImageURL(rs.getString(5));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (session != null)
					session.disconnect();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(count + "건 조회");

		return RestaurantVo;
	}

	// Restaurant 정보 (이름 + 사진)
	@Override
	public ArrayList<RestaurantVo> getRestaurantList(int cityId) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;
		ArrayList<RestaurantVo> list = new ArrayList<RestaurantVo>();

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "select * from Restaurant where r_city_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cityId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				RestaurantVo RestaurantVo = new RestaurantVo();
				RestaurantVo.setRestaurantId(rs.getInt(1));
				RestaurantVo.setRestaurantName(rs.getString(2));
				RestaurantVo.setRestaurantContent(rs.getString(3));
				RestaurantVo.setRestaurantLatitude(rs.getInt(4));
				RestaurantVo.setRestaurantLongitude(rs.getInt(5));
				RestaurantVo.setRestaurantCityId(rs.getInt(6));
				RestaurantVo.setRestaurantAddress(rs.getString(7));
				RestaurantVo.setRestaurantAdd(rs.getInt(8));
				RestaurantVo.setRestaurantImageURL(rs.getString(9));
				RestaurantVo.setRestaurantImageData(rs.getBinaryStream(10));
				list.add(RestaurantVo);
				count++;
				System.out.println(RestaurantVo.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (session != null)
					session.disconnect();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(count + "건 조회");

		return list;
	}

	// Restaurant 추가
	@Override
	public int insertRestaurant(RestaurantVo vo) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "insert into Restaurant values (seq_Restaurant_id.nextval, ?, ?, ?, ?, ?, ?, 0, ?, ?)";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getRestaurantName());
			pstmt.setString(2, vo.getRestaurantContent());
			pstmt.setDouble(3, vo.getRestaurantLatitude());
			pstmt.setDouble(4, vo.getRestaurantLongitude());
			pstmt.setInt(5, vo.getRestaurantCityId());
			pstmt.setString(6, vo.getRestaurantAddress());
			pstmt.setString(7, vo.getRestaurantImageURL());
			pstmt.setBinaryStream(8, vo.getRestaurantImageData());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (session != null)
					session.disconnect();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(count + "건 등록");

		return count;
	}

	// Restaurant 수정
	@Override
	public int updateRestaurant(RestaurantVo vo) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "update set r_name = ?, r_content = ?, r_image_url = ?, r_image_data = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getRestaurantName());
			pstmt.setString(2, vo.getRestaurantContent());
			pstmt.setString(3, vo.getRestaurantImageURL());
			pstmt.setBinaryStream(4, vo.getRestaurantImageData());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (session != null)
					session.disconnect();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(count + "건 수정");

		return count;
	}

	// Restaurant 삭제
	@Override
	public int deleteRestaurant(RestaurantVo vo) {
		
		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "delete from Restaurant where r_id = ?";

			pstmt = conn.prepareStatement(query);			
			pstmt.setInt(1, vo.getRestaurantId());
			
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (session != null)
					session.disconnect();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(count + "건 삭제");
		return count;
	}

	@Override
	public void clickRestaurant(RestaurantVo vo) {
		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "update restaurant set r_id = r_add+1 where r_id =?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, vo.getRestaurantId());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (session != null)
					session.disconnect();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(count + "건 변경");
	}
	
	private Connection getConnection(Session session) throws SQLException {
		Connection conn = null;

		try {
			int forward_port = session.setPortForwardingL(0, "localhost", 1521);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:" + forward_port + ":xe";
			conn = DriverManager.getConnection(dburl, "nadri", "kosta0000");
			if (conn != null) {
				System.out.println("DB Connection Success");
			} else {
				System.out.println("FAIL Connection");
			}
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC 드라이버 로드 실패!");
		} catch (JSchException e) {
			e.printStackTrace();
		}

		return conn;
	}

	private Session getSession() {
		JSch jsch = new JSch();
		Session session = null;
		try {
			session = jsch.getSession("ec2-user", "3.38.149.203", 22);
			session.setPassword("kosta0000");
			System.out.println("SSH Connection...");
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();
			System.out.println("SSH Connection Success");
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return session;
	}
}
