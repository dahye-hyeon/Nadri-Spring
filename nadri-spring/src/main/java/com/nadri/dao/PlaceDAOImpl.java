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
import com.nadri.vo.PlaceVo;

public class PlaceDAOImpl implements PlaceDAO {

	// place 정보
	public PlaceVo getPlace(PlaceVo vo) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;
		PlaceVo placeVo = new PlaceVo();

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "select pl_name, pl_content, pl_address, pl_add, pl_image_url from place where pl_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, vo.getPlaceId());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				placeVo.setPlaceName(rs.getString(1));
				placeVo.setPlaceContent(rs.getString(2));
				placeVo.setPlaceAddress(rs.getString(3));
				placeVo.setPlaceAdd(rs.getInt(4));
				placeVo.setPlaceImageURL(rs.getString(5));
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

		return placeVo;
	}

	// place 정보 (이름 + 사진)
	@Override
	public ArrayList<PlaceVo> getPlaceList(int cityId) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;
		ArrayList<PlaceVo> list = new ArrayList<PlaceVo>();

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "select * from place where pl_city_id=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cityId);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				PlaceVo placeVo = new PlaceVo();
				placeVo.setPlaceId(rs.getInt(1));
				placeVo.setPlaceName(rs.getString(2));
				placeVo.setPlaceContent(rs.getString(3));
				placeVo.setPlaceLatitude(rs.getDouble(4));
				placeVo.setPlaceLongitude(rs.getDouble(5));
				placeVo.setPlaceCityId(rs.getInt(6));
				placeVo.setPlaceAddress(rs.getString(7));
				placeVo.setPlaceAdd(rs.getInt(8));
				placeVo.setPlaceImageURL(rs.getString(9));
				placeVo.setPlaceImageData(rs.getBinaryStream(10));
				list.add(placeVo);
				count++;
				System.out.println(placeVo.toString());
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

	// place 추가
	@Override
	public int insertPlace(PlaceVo vo) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "insert into place values (seq_place_id.nextval, ?, ?, ?, ?, ?, ?, 0, ?, ?)";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getPlaceName());
			pstmt.setString(2, vo.getPlaceContent());
			pstmt.setDouble(3, vo.getPlaceLatitude());
			pstmt.setDouble(4, vo.getPlaceLongitude());
			pstmt.setInt(5, vo.getPlaceCityId());
			pstmt.setString(6, vo.getPlaceAddress());
			pstmt.setString(7, vo.getPlaceImageURL());
			pstmt.setBinaryStream(8, vo.getPlaceImageData());

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

	// place 수정
	@Override
	public int updatePlace(PlaceVo vo) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "update set pl_name = ?, pl_content = ?, pl_image_url = ?, pl_image_data = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getPlaceName());
			pstmt.setString(2, vo.getPlaceContent());
			pstmt.setString(3, vo.getPlaceImageURL());
			pstmt.setBinaryStream(4, vo.getPlaceImageData());

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

	// place 삭제
	@Override
	public int deletePlace(PlaceVo vo) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "delete from place where pl_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, vo.getPlaceId());

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

	// 일정에 추가된 횟수
	public void clickPlace(PlaceVo vo) {

		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {
			session = getSession();
			conn = getConnection(session);

			String query = "update place set pl_add = pl_add+1 where pl_id =?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, vo.getPlaceId());

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
