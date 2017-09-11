package com.pknu.bbs;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BBSOracleDao {
	private static BBSOracleDao bbsOracleDao = new BBSOracleDao();
	private OracleDBConnector oracleDBConnector =OracleDBConnector.getInstance();
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	StringBuffer query;
	ArrayList<BBSDto> articleList;
	BBSDto article;
	
	private BBSOracleDao(){}
	
	public synchronized static BBSOracleDao getInstance(){
		if(bbsOracleDao==null){
			bbsOracleDao = new BBSOracleDao();
		}
		return bbsOracleDao;
	}
	
	public int getTotalCount() throws SQLException, IOException{
		con=oracleDBConnector.getConnection();
		query= new StringBuffer();				
		query.append("SELECT COUNT(*) FROM BBS ");			
		pstmt=con.prepareStatement(query.toString());			
		rs=pstmt.executeQuery();
		int totalCount=0;
		if(rs.next()){
			totalCount=rs.getInt(1);
		}		
		streamClose();
		return totalCount;		
	}
	
	public ArrayList<BBSDto> getArticleList(int startRow, int endRow)throws SQLException, IOException{		  
		con=oracleDBConnector.getConnection();
		query= new StringBuffer();				
		query.append("SELECT LIST.* ");	
		query.append("FROM (SELECT ROWNUM RUM,HUMAN.* ");	
		query.append("		FROM (SELECT articleNum,id,title,depth,hit,writeDate ");
		query.append("			  FROM bbs ");
		query.append("			  ORDER BY groupId DESC, pos) HUMAN) LIST ");
		query.append("WHERE RUM BETWEEN ? AND ?");
		pstmt=con.prepareStatement(query.toString());
		pstmt.setInt(1, startRow);
		pstmt.setInt(2, endRow);		
		rs=pstmt.executeQuery();
		
		articleList =new ArrayList<>();
		
		while(rs.next()){
			article= new BBSDto();
			article.setArticleNum(rs.getInt("articleNum"));
			article.setId(rs.getString("id"));
			article.setTitle(rs.getString("title"));
			article.setDepth(rs.getInt("depth"));
			article.setHit(rs.getInt("hit"));
			article.setWriteDate(rs.getTimestamp("writeDate"));
			articleList.add(article);			
		}
		streamClose();		
		return articleList;
	}	
	
	public void write(BBSDto article) throws  SQLException, IOException {
		System.out.println(article);
		con=oracleDBConnector.getConnection();
		query=new StringBuffer();
		query.append("insert into bbs values(bbs_seq.nextval,?,?,?,0,0,0,0,sysdate,?)");
		pstmt=con.prepareStatement(query.toString());
		pstmt.setString(1, article.getId());
		pstmt.setString(2, article.getTitle());
		pstmt.setString(3, article.getContent());
		pstmt.setString(4, article.getFname());
		pstmt.executeUpdate();
		
		streamClose();
	}
	
	public void streamClose(){
		try{
			if(rs!=null){
				rs.close();
			}
			pstmt.close();
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}			
	}
}
