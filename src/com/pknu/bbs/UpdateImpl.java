package com.pknu.bbs;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateImpl implements BBSInter {
	
	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String articleNum = req.getParameter("articleNum");
		String pageNum = req.getParameter("pageNum");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		
		DBCon dbcon = DBCon.getInstance();
		
		try {
			dbcon.getUpdateArticle(articleNum,title,content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "/content.bbs?pageNum=" + pageNum + "&articleNum=" + articleNum;
	}

}
