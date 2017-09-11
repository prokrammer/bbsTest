package com.pknu.ajaxtest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pknu.bbs.BBSInter;
import com.pknu.bbs.DBCon;

public class AjaxJsonOneImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LoginDto login=null;
		try {
			login = DBCon.getInstance().getOneLogin(req.getParameter("id"));
			
//			req.setAttribute("loginList", loginList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resp.setCharacterEncoding("utf-8");
		JSONObject jo = new JSONObject(login);
		PrintWriter pw = resp.getWriter();
		pw.println(jo.toString());
		return null;
	}

}
