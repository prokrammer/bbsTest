package com.pknu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormImpl implements BBSInter {
	BBSDto article = new BBSDto();

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String articleNum = req.getParameter("articleNum");
		String pageNum = req.getParameter("pageNum");
		
		DBCon dbcon = DBCon.getInstance();
		
		
		try {
			article=dbcon.getUpdateArticle(articleNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		req.setAttribute("article", article);
		req.setAttribute("pageNum", pageNum);
		return "/WEB-INF/updateform.jsp";
	}

}
