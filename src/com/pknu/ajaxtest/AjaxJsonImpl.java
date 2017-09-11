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

public class AjaxJsonImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<LoginDto> loginList=null;
		try {
			loginList = DBCon.getInstance().getAllLogin();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JSONArray ja = new JSONArray(loginList);
		PrintWriter pw = resp.getWriter();
		pw.println(ja.toString());
		return null;
	}

}
