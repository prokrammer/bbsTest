package com.pknu.bbs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class ReplyImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BBSDto article = new BBSDto();
		
		req.setCharacterEncoding("UTF-8");
		
		String saveDir = req.getServletContext().getInitParameter("saveDir");
		
		/*article.setTitle(req.getParameter("title"));
		article.setContent(req.getParameter("content"));
		article.setId((String)req.getSession().getAttribute("id"));
		article.setFname(req.getParameter("fname"));
*/
		article.setDepth(Integer.parseInt(req.getParameter("depth")));
		article.setPos(Integer.parseInt(req.getParameter("pos")));
		article.setGroupId(Integer.parseInt(req.getParameter("groupId")));
		System.out.println(req.getParameter("groupId"));
		
//		article.setGroupId((int)req.getAttribute("groupId"));
//		article.setDepth((int)req.getAttribute("depth"));
//		article.setPos((int)req.getAttribute("pos"));
		/*return "/list.bbs?pageNum=" + req.getParameter("pageNum");
	}*/
		article.setTitle(readParameterValue(req.getPart("title")));
		article.setContent(readParameterValue(req.getPart("content")));
		article.setId((String)req.getSession().getAttribute("id"));
//		article.setFname(req.getParameter("fname"));
		
		//fileInputStream이 fileReader보다 빠를 경우 -> text파일이 아닌 2진 파일일 경우
		if(req.getPart("fname").getSize()!=0) {
			Part filePart = req.getPart("fname");
			String originFname = getFileName(filePart);
			article.setFname(originFname);
			
			File file = new File(saveDir + originFname);
			InputStream is = filePart.getInputStream();
			
			FileOutputStream os = new FileOutputStream(file);
			int temp = -1;
			Long time=0l;
			/*time = System.currentTimeMillis();
			
			while((temp = is.read()) != -1) {
				os.write(temp);
			}
			
			System.out.println("걸린시간 : " +(System.currentTimeMillis()-time));
			*/
			
			BufferedInputStream bis = new BufferedInputStream(is);
			
			BufferedOutputStream bos = new BufferedOutputStream(os);
			time = System.currentTimeMillis();
			while((temp=bis.read())!=-1) {
				bos.write(temp);
			}
			System.out.println("걸린시간 : " +(time-System.currentTimeMillis()));
			bis.close();
			bos.close();
			 
			is.close();
			os.close();
		}
		
		try {
			DBCon.getInstance().reply(article);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			DBCon.getInstance().write(article);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return "/list.bbs?pageNum="+req.getParameter("pageNum");
	}
	
	private String getFileName(Part filePart) {
		String originFname = null;
		for(String cd:filePart.getHeader("content-disposition").split(";")) {
			if(cd.trim().startsWith("filename")) {
				originFname = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return originFname;
	}

	private String readParameterValue(Part part) throws IOException{
		InputStreamReader reader = new InputStreamReader(part.getInputStream(),"utf-8");

		int temp = -1;
		StringBuffer builder = new StringBuffer();
		while((temp = reader.read())!=-1) {
			//char로 형변환해야 문자로됨
			builder.append((char)temp);
		}
		return builder.toString();
	}

	
}
