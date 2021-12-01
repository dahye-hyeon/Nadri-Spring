package com.nadri.util;

import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHConnection {
	private final static String HOST = "3.38.149.203";
	private final static Integer PORT = 22; //기본포트는 22
	private final static String SSH_USER = "ec2-user"; // 연결 USER값 ex) root
	private final static String SSH_PW = "kosta0000"; //연결 비밀번호값 ex) 1234
	
	private Session session;
	
	public void closeSSH() {
		session.disconnect();
	}
	
	public SSHConnection() {
		try {
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			session = jsch.getSession(SSH_USER, HOST, PORT);
			session.setPassword(SSH_PW);
			session.setConfig(config);
			session.connect();
			session.setPortForwardingL(0, "localhost", 1521); //127.0.0.1/3316으로 접근한 포트를 연결HOST/3306으로 포트포워딩
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
