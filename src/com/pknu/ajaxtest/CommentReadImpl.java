package com.pknu.ajaxtest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.pknu.bbs.BBSInter;
import com.pknu.bbs.DBCon;

public class CommentReadImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String articleNum = req.getParameter("articleNum");
		String commentRow = req.getParameter("commentRow");
		ArrayList<CommentDto> commentList = null;
		try {
			commentList = DBCon.getInstance().getComments(articleNum,commentRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setCharacterEncoding("utf-8");
		JSONArray jb = new JSONArray(commentList);
		PrintWriter pw = resp.getWriter();
		pw.println(jb.toString());
		
		return null;
	}

}
