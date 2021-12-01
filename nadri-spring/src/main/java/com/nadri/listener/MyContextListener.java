package com.nadri.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.nadri.util.SSHConnection;

@WebListener
public class MyContextListener implements ServletContextListener{
	
	private SSHConnection sshConnection;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("init start!");
		try {
			sshConnection = new SSHConnection();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("init destory!");
		sshConnection.closeSSH();
	}
}
