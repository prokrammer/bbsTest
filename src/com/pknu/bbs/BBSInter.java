package com.pknu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BBSInter {
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException ;
}
