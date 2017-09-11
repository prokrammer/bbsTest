package com.pknu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getParameter("pageNum"));
		req.setAttribute("pageNum", req.getParameter("pageNum"));
		return "/WEB-INF/joinform.jsp";
	}

}
