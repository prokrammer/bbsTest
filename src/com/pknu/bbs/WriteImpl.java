package com.pknu.bbs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


public class WriteImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String saveDir = req.getServletContext().getInitParameter("saveDir");
		
//		//헤더정보 표시
//		Enumeration<String> en = req.getHeaderNames();
//		while(en.hasMoreElements()) {
//			String headerName=en.nextElement();
//			System.out.println("헤더이름 : " + headerName + " 헤더값 : " + req.getHeader(headerName));
//		}
		
//		//Part정보 읽어오기
//		Collection<Part> cp = req.getParts();
//		Iterator<Part> it = cp.iterator();
//		while(it.hasNext()) {
//			Part pa = it.next();
//			System.out.println("part 이름 : " + pa.getName() + "");
//			Collection<String> co = pa.getHeaderNames();
//			Iterator<String> itt = co.iterator();
//			while(itt.hasNext()) {
//				String partHeaderName = itt.next();
//				System.out.print("part의 헤더이름 : " + partHeaderName + " ");
//				System.out.println("part 헤더값 : " + pa.getHeader(partHeaderName).);
//			}
//		}
		
		
		BBSDto article = new BBSDto();
		
		/*article.setTitle(req.getParameter("title"));
		article.setContent(req.getParameter("content"));
		article.setId((String)req.getSession().getAttribute("id"));
		article.setFname(req.getParameter("fname"));
		*/
		
		
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
			while((temp = is.read()) != -1) {
				os.write(temp);
			}
			is.close();
			os.close();
		}
		
		
		try {
			DBCon.getInstance().write(article);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/list.bbs?pageNum=1";
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
