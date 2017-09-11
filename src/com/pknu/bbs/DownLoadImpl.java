package com.pknu.bbs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoadImpl implements BBSInter {

	@Override
	public String bbs(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fname = req.getParameter("fname");
		String saveDir = req.getServletContext().getInitParameter("saveDir");
		
		File file = new File(saveDir + fname);
		
		resp.setContentType("application/download");
		resp.setContentLength((int)file.length());
		//�ͽ��÷η� ���� -- �����ؾ���
//		fname = URLEncoder.encode(fname,"utf-8").replace("+", "%20").replace("%28", "(").replace("%29", ")");
		
		//�ͽ��÷η��� ���� �ȵ�
		//����, Ư����ȣ('(',')',' ')���� ó������ �ʾƵ� ��
		fname = new String(fname.getBytes("utf-8"),"iso-8859-1")/*.replace("+", "%20")*/;
		
		resp.setHeader("content-disposition", "attachment; filename=\""+fname+"\";");
		OutputStream out = resp.getOutputStream();
		FileInputStream fis = null;
		
		int temp;
		try {
			fis = new FileInputStream(file);
			
			BufferedInputStream bis = new BufferedInputStream(fis);
			BufferedOutputStream bos = new BufferedOutputStream(out);
			Long time = System.currentTimeMillis();
			while((temp=bis.read())!=-1) {
				bos.write(temp);
			}
			System.out.println(System.currentTimeMillis()-time+"�и��� �ɷȽ��ϴ�");
			
			bos.close();
			bis.close();
//			Long time = System.currentTimeMillis();
//			while((temp=fis.read())!=-1) {
//				out.write(temp);
//			}
//			System.out.println(System.currentTimeMillis()-time+"�и��� �ɷȽ��ϴ�");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
				
		return null;
	}

}
