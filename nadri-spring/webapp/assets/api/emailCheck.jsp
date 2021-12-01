<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
​
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.jcraft.jsch.JSch"%>
<%@page import="com.jcraft.jsch.JSchException"%>
<%@page import="com.jcraft.jsch.Session"%>
​
<%
      // 요청파라미터로  중복할 체크할 아이디
      String id = request.getParameter("email");  // id 중복체크
      
      Enumeration e = request.getParameterNames();
      while ( e.hasMoreElements() ){
        String name = (String) e.nextElement();
        String[] values = request.getParameterValues(name);   
        for (String value : values) {
          System.out.println("name=" + name + ",value=" + value);
        }   
      }
      
     Connection conn = null;
     
     JSch jsch = new JSch();
     Session se = null;
     try {
        se = jsch.getSession("ec2-user", "3.38.149.203", 22);
        se.setPassword("kosta0000");
        System.out.println("SSH Connection...");
        se.setConfig("StrictHostKeyChecking", "no");
        se.connect();
        System.out.println("SSH Connection Success");	
	} catch (JSchException jse) {
		jse.printStackTrace();
	}
     
     //cnt=1  아이디 사용중이다.  
     //0  아이디 사용가능하다.
      int cnt = 0;

     try{
	   int forward_port = se.setPortForwardingL(0, "localhost", 1521);
	   Class.forName("oracle.jdbc.driver.OracleDriver");
	   String dburl = "jdbc:oracle:thin:@localhost:" + forward_port + ":xe";
	   conn = DriverManager.getConnection(dburl, "nadri", "kosta0000");
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
   
	   if (conn != null) {
		   System.out.println("DB Connection Success");
		   
		   String sql = " select count(*) cnt " + 
	                   "from users " + 
	                   "where u_email = ?";
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setString(1, id);
		   rs = pstmt.executeQuery();
		   
		   if(rs.next()){
		     cnt = rs.getInt("cnt"); 
		   }  
		} else {
			System.out.println("FAIL Connection");
		}
	} catch (ClassNotFoundException cnfe) {
		System.err.println("JDBC 드라이버 로드 실패!");
	} catch (JSchException jse) {
		jse.printStackTrace();
	}
    
     System.out.print("cnt값은===>" + cnt);
     
   if(cnt == 1){
	   System.out.println("true: " + cnt);
	   out.print("true");
   }else{
	   System.out.println("false: " + cnt);
	   out.print("false");
   }  
%>