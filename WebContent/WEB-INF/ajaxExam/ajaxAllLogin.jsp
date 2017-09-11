<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script>
$(function(){
	$("#getAll").on("click",function(){			
		$.ajax({		
			type : "POST",
			async : true,
			dataType : "json",
			url:"/bbs/ajaxJson.bbs",
			data:{				
			},
			success:function(data){	
				let result="<table border='1'>"
				$.each(data,function(index,member){
					result +="<tr><td>"+member.id+"</td>"
					             +"<td>"+member.pass+"</td></tr>";					
				});
				result +="</table>";
				$("#result").html(result);
			},
			error : function(xhr){
				alert("error html = " + xhr.statusText);
			}			
		});		
	});
	
	$("#getOne").on("click",function(){			
		$.ajax({		
			type : "POST",
			async : true,
			dataType : "json",
			url:"/bbs/ajaxJsonOne.bbs",
			data:{		
				id : $("#one").val()
			},		
			success:function(member){	
				let result="<table border='1'><tr><td>";
					result +=member.id+"</td>";
					result +="<td>"+member.pass+"</td></tr></table>";				
			
				$("#result").html(result);
			},
			error : function(xhr){
				alert("error html = " + xhr.statusText);
			}			
		});		
	});
});
</script>
</head>
<body>
	<div>
		<button id="getAll">눌리라(모두 가져오기)</button>
	</div>
	<br /><br />
	<div>
	    <input type="text"  id="one">
		<button  id="getOne">눌리라(한명가져오기)</button>
	</div>
    <br />
	<div id="result">여기에 값이 출력됩니다</div>
</body>
</html>