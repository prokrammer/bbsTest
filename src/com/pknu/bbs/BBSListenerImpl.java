package com.pknu.bbs;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BBSListenerImpl implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("리스너 초기화 작업");
	}

}
