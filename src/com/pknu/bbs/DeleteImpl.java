package com.pknu.bbs;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteImpl implements BBSInter{

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String articleNum = req.getParameter("articleNum");
		
		try {
			DBCon.getInstance().delete(articleNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "/list.bbs?pageNum=" + req.getParameter("pageNum");
	}

}
