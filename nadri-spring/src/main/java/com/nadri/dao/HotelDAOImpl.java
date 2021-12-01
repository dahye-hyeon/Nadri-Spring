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
import com.nadri.vo.HotelVo;

public class HotelDAOImpl implements HotelDAO {

   // To get datail hotel information when you click info button.
   public HotelVo getHotel(HotelVo vo) {

      Connection conn = null;
      Session session = null;
      PreparedStatement pstmt = null;
      HotelVo hotelVo = new HotelVo();

      int count = 0;

      try {
         session = getSession();
         conn = getConnection(session);

         String query = "select h_name, h_content, h_address, h_add, h_image_url from hotel where h_id = ?";

         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, vo.getHotelId());

         ResultSet rs = pstmt.executeQuery();

         while (rs.next()) {
            hotelVo.setHotelName(rs.getString(1));
            hotelVo.setHotelContent(rs.getString(2));
            hotelVo.setHotelAddress(rs.getString(3));
            hotelVo.setHotelAdd(rs.getInt(4));
            hotelVo.setHotelImageURL(rs.getString(5));
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

      return hotelVo;
   }

   // To get hotel information using citiId
   @Override
   public ArrayList<HotelVo> getHotelList(int cityId) {

      Connection conn = null;
      Session session = null;
      PreparedStatement pstmt = null;
      ArrayList<HotelVo> list = new ArrayList<HotelVo>();
      

      int count = 0;

      try {
         session = getSession();
         conn = getConnection(session);

         String query = "select * from hotel where h_city_id=?";

         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, cityId);
         ResultSet rs = pstmt.executeQuery();

         while (rs.next()) {
            HotelVo hotelVo = new HotelVo();
            hotelVo.setHotelId(rs.getInt(1));
            hotelVo.setHotelName(rs.getString(2));
            hotelVo.setHotelContent(rs.getString(3));
            hotelVo.setHotelLatitude(rs.getDouble(4));
            hotelVo.setHotelLongitude(rs.getDouble(5));	
            hotelVo.setHotelCityId(rs.getInt(6));
            hotelVo.setHotelAddress(rs.getString(7));
            hotelVo.setHotelAdd(rs.getInt(8));
            hotelVo.setHotelImageURL(rs.getString(9));
            hotelVo.setHotelImageData(rs.getBinaryStream(10));       
        
            list.add(hotelVo);
            count++;
           
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

   // hotel 추가
   @Override
   public int insertHotel(HotelVo vo) {

      Connection conn = null;
      Session session = null;
      PreparedStatement pstmt = null;

      int count = 0;

      try {
         session = getSession();
         conn = getConnection(session);

         String query = "insert into hotel values (seq_hotel_id.nextval, ?, ?, ?, ?, ?, ?, 0, ?, ?)";

         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, vo.getHotelName());
         pstmt.setString(2, vo.getHotelContent());
         pstmt.setDouble(3, vo.getHotelLatitude());
         pstmt.setDouble(4, vo.getHotelLongitude());
         pstmt.setInt(5, vo.getHotelCityId());
         pstmt.setString(6, vo.getHotelAddress());
         pstmt.setString(7, vo.getHotelImageURL());
         pstmt.setBinaryStream(8, vo.getHotelImageData());

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

   // hotel 수정
   @Override
   public int updateHotel(HotelVo vo) {

      Connection conn = null;
      Session session = null;
      PreparedStatement pstmt = null;

      int count = 0;

      try {
         session = getSession();
         conn = getConnection(session);

         String query = "update set h_name = ?, h_content = ?, h_image_url = ?, h_image_data = ?";

         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, vo.getHotelName());
         pstmt.setString(2, vo.getHotelContent());
         pstmt.setString(3, vo.getHotelImageURL());
         pstmt.setBinaryStream(4, vo.getHotelImageData());

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

   // hotel 삭제
   @Override
   public int deleteHotel(HotelVo vo) {
      
      Connection conn = null;
      Session session = null;
      PreparedStatement pstmt = null;

      int count = 0;

      try {
         session = getSession();
         conn = getConnection(session);

         String query = "delete from hotel where h_id = ?";

         pstmt = conn.prepareStatement(query);         
         pstmt.setInt(1, vo.getHotelId());
         
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
   public void clickHotel(HotelVo vo) {
      Connection conn = null;
      Session session = null;
      PreparedStatement pstmt = null;
      
      int count = 0;

      try {
         session = getSession();
         conn = getConnection(session);

         String query = "update hotel set h_add = h_add +1 where h_id =?";

         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1,vo.getHotelId());

         count=pstmt.executeUpdate();

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