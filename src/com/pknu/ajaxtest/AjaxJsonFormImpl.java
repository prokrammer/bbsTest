package com.pknu.ajaxtest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pknu.bbs.BBSInter;

public class AjaxJsonFormImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return "/WEB-INF/ajaxExam/ajaxAllLogin.jsp";
	}

}
