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
		
//		//������� ǥ��
//		Enumeration<String> en = req.getHeaderNames();
//		while(en.hasMoreElements()) {
//			String headerName=en.nextElement();
//			System.out.println("����̸� : " + headerName + " ����� : " + req.getHeader(headerName));
//		}
		
//		//Part���� �о����
//		Collection<Part> cp = req.getParts();
//		Iterator<Part> it = cp.iterator();
//		while(it.hasNext()) {
//			Part pa = it.next();
//			System.out.println("part �̸� : " + pa.getName() + "");
//			Collection<String> co = pa.getHeaderNames();
//			Iterator<String> itt = co.iterator();
//			while(itt.hasNext()) {
//				String partHeaderName = itt.next();
//				System.out.print("part�� ����̸� : " + partHeaderName + " ");
//				System.out.println("part ����� : " + pa.getHeader(partHeaderName).);
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
		
		//fileInputStream�� fileReader���� ���� ��� -> text������ �ƴ� 2�� ������ ���
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
			//char�� ����ȯ�ؾ� ���ڷε�
			builder.append((char)temp);
		}
		return builder.toString();
	}
	
	

}
