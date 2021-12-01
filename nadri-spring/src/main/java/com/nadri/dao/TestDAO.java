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
import com.nadri.vo.NadriTestVO;

public class TestDAO {

	public ArrayList<NadriTestVO> getList() {
		Connection conn = null;
		Session session = null;
		PreparedStatement pstmt = null;
		ArrayList<NadriTestVO> vos = new ArrayList<NadriTestVO>();
		int count = 0;
		try {
			session = getSession();
			conn = getConnection(session);
		
			String query = "select * from test";
			pstmt = conn.prepareStatement(query);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NadriTestVO vo = new NadriTestVO();
				vo.setId(rs.getInt(1));
				vo.setName(rs.getString(2));
				
				vos.add(vo);
				count += 1;
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if(conn != null) conn.close();
				if(session != null) session.disconnect();
			}  catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(count + "건 조회");
		return vos;
	}
	
	private Connection getConnection(Session session) throws SQLException {
		Connection conn = null;
	
		try {
			int forward_port = session.setPortForwardingL(0, "localhost", 1521);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:" + forward_port + ":xe";
			conn = DriverManager.getConnection(dburl, "nadri", "kosta0000");
			if (conn!=null) {
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
