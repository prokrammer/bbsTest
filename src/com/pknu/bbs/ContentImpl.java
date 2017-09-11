package com.pknu.bbs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContentImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String articleNum = req.getParameter("articleNum");
		String pageNum = req.getParameter("pageNum");
		BBSDto bbsdto = new BBSDto();
		StringBuffer view = new StringBuffer();
		try {
			bbsdto = DBCon.getInstance().getContent(articleNum);
			req.setAttribute("article", bbsdto);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		req.setAttribute("article", bbsdto);
		req.setAttribute("pageNum", pageNum);
		return "/WEB-INF/content.jsp";
	}

}
