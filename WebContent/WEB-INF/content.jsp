<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>글읽기</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
$.ajaxSetup({
	type:"POST",
	sync:true,
	dataType:"json",
	error:function(xhr){
		alert("error html = " + xhr.statusText);
	}
});
    $(document).ready(function(){
        $("#commentWrite").on("click",function(){
//             alert("클릭했음");
			$.ajax({		
			url:"/bbs/commentWrite.bbs",
			data:{
				commentContent : $("#commentContent").val(),
				articleNum : "${article.articleNum}"
			},
			success:function(data){	
// 				alert("성공!");
				showHtml(data,1);
			}
						
		});
	});
	});
    
    function getComment(commPageNum,event) {
    	$.ajax({		
			
			url:"/bbs/commentRead.bbs",
			data:{
				articleNum : "${article.articleNum}",
				commentRow : commPageNum * 10
			},
			success:function(data){
				showHtml(data,commPageNum);
			}
						
		});
	}
    function showHtml(data,commPageNum) {
/*     	let result="<table border='1' width>"
			$.each(data,function(index,member){
				result +="<tr><td>"+member.id+"</td>"
				             +"<td>"+member.commentContent+"</td>"
				             +"<td>"+member.commentDate+"</td>"
				             +"</tr>";					
			});
			result +="</table>";
			$("#showComment").html(result); */
		let html="<table border='1' width='500' align='center'>";
		$.each(data, function(index,item){
			let localDate = new Date(item.commentDate);	
			let presentDay = item.commentDate;
			presentDay= presentDay.substring(0,10);
			html +="<tr>";
			html +="<td>"+(index+1)+"</td>";
			html +="<td>"+item.id+"</td>";
			html +="<td>"+item.commentContent+"</td>";
			html +="<td>"+presentDay+"</td>";
//	 		html +="<td>"+item.commentDate+"</td>";
// 	 		html +="<td>"+localDate.toLocaleString()+"</td>";					
//	 		html +="<td>"+localDate.toLocaleDateString()+"</td>";					
//	 		html +="<td>"+localDate.toLocaleTimeString()+"</td>";					
//	 		html +="<td>"+localDate.toDateString()+"</td>";					
//	 		html +="<td>"+localDate.toTimeString()+"</td>";					
//	 		html +="<td>"+localDate.toString()+"</td>";
			html +="<td>"+item.articleNum+"</td>";					
			html +="</tr>";					
		});
		html +="</table>";
		commPageNum=parseInt(commPageNum);
		let exex = "${article.commentCount}";
// 		alert(exex);
		if(exex >commPageNum*10){			
			nextPageNum=commPageNum+1;
// 			alert("나왔다");
			html +="<br /><input type='button' onclick='getComment(nextPageNum,event)' value='다음comment보기'><br>";
		}
		$("#showComment").html(html);	
		$("#commentContent").val("");
		$("#commentContent").focus();
	}
</script>
</head>
<body>   
   <form action="/bbs/replyForm.bbs" method="post">      
    <input type="hidden" name="pageNum" value="${pageNum}">                 
    <input type="hidden" name="depth" value="${article.depth}">
    <input type="hidden" name="pos" value="${article.pos}">
    <input type="hidden" name="groupId" value="${article.groupId}">
	<table border="1" width="500" align="center">  
		<tr>
 			 <td width="40">글쓴이</td> <td width="100">${article.id}</td> 			 
 			 <td width="40">조회수</td> <td width="100">${article.hit}</td>
 		</tr>
 		<tr>	 
			 <td>제목</td><td>${article.title}</td>
			 <td>날짜</td><td>${article.writeDate}</td>
		 </tr>	
		 <tr>
			<td colspan="2">다운로드 </td>
			<td colspan="2">
			<a href="/bbs/download.bbs?fname=${article.fname}">${article.fname}</a></td>
		 </tr>					
		 <tr>			 
			  <td colspan="4"><xmp>${article.content}</xmp></td>
	     </tr> 	
	          
	     <tr>
	      <c:if test="${id !=null}">
	    	  <td colspan="4" align="right">	    	
	    	  <input type="submit" value="답글달기">
	    	  <c:if test="${id ==article.id}">
	    	  <input type="button" value="수정하기" onclick="document.location.href='/bbs/updateForm.bbs?articleNum=${article.articleNum}&pageNum=${pageNum}'">
	    	  <input type="button" value="삭제하기" onclick="document.location.href='/bbs/delete.bbs?articleNum=${article.articleNum}&pageNum=${pageNum}'">
	    	  </c:if>
	    	  <c:if test="${id !=article.id}">
	    	  <input type="button" value="수정하기" disabled="disabled">
	    	  <input type="button" value="삭제하기" disabled="disabled">
	    	  </c:if>
	    	  <input type="button" value="목록으로" onclick="document.location.href='/bbs/list.bbs?pageNum=${pageNum}'">
	    	  </td>
	      </c:if>
	      		    	
	      <c:if test="${id ==null}">
	    	  <td colspan="4" align="right">
	    	  <input type="submit" value="답글달기" disabled="disabled">
	    	  <input type="button" value="수정하기" disabled="disabled">
	    	  <input type="button" value="삭제하기" disabled="disabled">
	    	  <input type="button" value="목록으로" onclick="document.location.href='/bbs/list.bbs?pageNum=${pageNum}'">
	    	  </td>   
	      </c:if>      	 	      	 
	     </tr>
	     <tr>
		     <td colspan="4">
		   	   <textarea rows="5" cols="70" id="commentContent"></textarea><br /><br />
			   <c:if test="${id ==null}">
		    	  <input type="button" value="comment 쓰기" disabled="disabled">    	  
		       </c:if> 
		       <c:if test="${id !=null}">
	    	 	 <input type="button" value="comment 쓰기" id="commentWrite">
	     	   </c:if>	     	  
	     	   <input type="button" value="comment 읽기(${article.commentCount })" 
	     	       onclick="getComment(1,event)" id="commentRead">	     	       
		   </td> 
		 </tr> 		
	 </table>		 	
	</form>
	<form>
	<div>
		<div id="showComment" align="center">
		</div>
<!-- 		<input type="hidden" id="commPageNum" value="1"> -->
	</div>	
	</form>
	
</body>
</html>