package com.pknu.bbs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BBSServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2087572126698643838L;
	HashMap<String, BBSInter> hm;
	BBSInter bbsInter;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		bbs(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		bbs(req, resp);
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		String url = config.getInitParameter("bbsp");
		Properties prop = new Properties();
		
		try {
			prop.load(new FileReader(url));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator it = prop.keySet().iterator();
		hm = new HashMap<>();
		while(it.hasNext()) {
			try {
				String key = (String) it.next();
				String value = prop.getProperty(key);
				Class cla = Class.forName(value);
				BBSInter bi = (BBSInter)cla.newInstance();
				hm.put(key, bi);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
	}
	protected void bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String requestUri = req.getServletPath();
		System.out.println(requestUri);
		
		bbsInter = hm.get(requestUri);
		
		String view = bbsInter.bbs(req, resp);
		if(view!=null) {
			RequestDispatcher rd = req.getRequestDispatcher(view);
			rd.forward(req, resp);
		}
	}
	
}
