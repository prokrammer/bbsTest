package com.pknu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String pass = req.getParameter("pass");
		String pageNum = req.getParameter("pageNum");
		System.out.println(pageNum);
		int result = 0;
		String view = null;
				
		try {
			result = DBCon.getInstance().loginCheck(id,pass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		if(result==LoginStatus.LOGIN_SUCCESS) {
			req.getSession().setAttribute("id", id);
			view="/list.bbs?pageNum="+pageNum;
		} else if(result==LoginStatus.LOGIN_FAIL){
			view="/WEB-INF/login.jsp";
		} else {
			view="/WEB-INF/join.jsp";
		}
		
		return view;
	}

}
