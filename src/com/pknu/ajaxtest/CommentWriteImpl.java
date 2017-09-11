package com.pknu.ajaxtest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.pknu.bbs.BBSInter;
import com.pknu.bbs.DBCon;

public class CommentWriteImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content = req.getParameter("commentContent");
		String articleNum = req.getParameter("articleNum");
		String id = (String) req.getSession().getAttribute("id");
		System.out.println("라이트임플");
		DBCon.getInstance().writeContent(id, articleNum, content);
		ArrayList<CommentDto> commentList = null;
		try {
			commentList = DBCon.getInstance().getComments(articleNum, "10");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.setCharacterEncoding("utf-8");
		JSONArray jb = new JSONArray(commentList);
		PrintWriter pw = resp.getWriter();
		pw.println(jb.toString());
		
		return null;
	}
}
