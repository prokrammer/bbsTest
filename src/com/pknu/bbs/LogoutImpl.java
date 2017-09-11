package com.pknu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutImpl implements BBSInter{

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*req.getSession().setAttribute("id", null);*/
		req.getSession().invalidate();
		String pageNum = req.getParameter("pageNum");
		return "/list.bbs?pageNum="+pageNum;
	}

}
