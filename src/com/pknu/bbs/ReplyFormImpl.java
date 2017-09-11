package com.pknu.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReplyFormImpl implements BBSInter{

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BBSDto bd = new BBSDto();
		String pageNum = req.getParameter("pageNum");
		bd.setDepth(Integer.parseInt(req.getParameter("depth")));
		bd.setPos(Integer.parseInt(req.getParameter("pos")));
		bd.setGroupId(Integer.parseInt(req.getParameter("groupId")));
		req.setAttribute("replyDto", bd);
		req.setAttribute("pageNum", pageNum);
		System.out.println("µª½º : " + bd.getDepth());
		return "/WEB-INF/replyform.jsp";
	}

}
