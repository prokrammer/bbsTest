package com.pknu.bbs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListImpl implements BBSInter{
	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int totalCount = 0;
		ArrayList<BBSDto> articleList;
		//page¥¬ ΩÃ±€≈Ê¿∏∑Œ «“ ∞Õ¿Ã¥Ÿ
		
		DBCon dbcon = DBCon.getInstance();
		articleList = new ArrayList<BBSDto>();
		
		/*if(req.getParameter("pageNum")==null) {
			req.setAttribute("pageNum", "1");
		}*/
		
		int pageSize = Integer.parseInt(req.getServletContext().getInitParameter("pageSize"));
		int pageBlock = Integer.parseInt(req.getServletContext().getInitParameter("pageBlock"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		HashMap<String, String> pagingValue;
		
		Page page = Page.getInstance();
		
		try {
			pagingValue = new HashMap<>();
			totalCount = dbcon.getTotalCount();
			pagingValue = page.paging(pageNum, totalCount, pageSize, pageBlock);
			articleList = dbcon.getArticleList(Integer.parseInt(pagingValue.get("startRow")),Integer.parseInt(pagingValue.get("endRow")));
			req.setAttribute("pageCode", pagingValue.get("pageCode"));
			req.setAttribute("pageNum", pageNum);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("naga", articleList);
		
		return "/WEB-INF/bbs/list.jsp";
	}
	
}
