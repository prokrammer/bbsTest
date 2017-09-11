package com.pknu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WriteFormImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		
		if((String)session.getAttribute("id")==null) {
			return "/WEB-INF/login.jsp";
		}
		
		return "/WEB-INF/bbs/writeform.jsp";
	}
}
