<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/getBankID" prefix="z"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合伙人列表</title>
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  	<link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
  	<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
  
  <link rel="stylesheet" href="jqueryui/style.css">
	
	<script type="text/javascript">
        function addpartner() {
            window.location.href = "add.jsp";
        }

        $(function () {
            $("#datepicker").datepicker({dateFormat: 'yy-mm-dd'});
        });

        function downFile(){
			var date = $("#datepicker").val();
			$.ajax({
				type:"post",
				url:"/partner/download",
				data:{
                    selectTime : date
				},
				success:function(result){
				    alert(result);
				},
				error:function(data,type, err){
                    console.log("ajax错误类型："+type);
                    console.log(err);
                }
			});
		}





	</script>
</head>
<body>
	<form action="/partner/list" method="post" class="form-inline" role="form">
		<div class="form-group">
			<label class="sr-only" for="partnername">汇合伙人姓名</label> <input type="text"
				class="form-control" id="partnername" name="partnername" value="${partnername}" placeholder="汇合伙人姓名">
		</div>
		<div class="form-group">
			<label class="sr-only" for="agentname">代理商名称</label> <input type="text"
				class="form-control" id="agentname" name="agentname"  value="${agentname}" placeholder="代理商名称">
		</div>
		<div class="form-group">
			<label class="sr-only" for="agentno">代理商编号</label> <input type="text"
				class="form-control" id="agentno" name="agentno"  value="${agentno}" placeholder="代理商编号">
		</div>
		<button type="submit" class="btn btn-default">提交</button>
		<button type="button" class="btn btn-default" onclick="addpartner();">添加</button>
	</form>
	<form action="/partner/download" method="post" class="form-inline" role="form">
		日期：<input type="text" id="datepicker" class="form-control" name="selectTime" readonly="readonly" >
		<button type="button" onclick="downFile()" class="btn btn-default">下载</button>
	</form>
	<table class="table table-bordered">
		<caption>合伙人列表</caption>
		<thead>
			<tr>
				<th>合伙人名称</th>
				<th>代理商ID</th>
				<th>代理商名称</th>
				<th>代理商编号</th>
				<th>交易类型</th>
				<th>合伙人T0费率</th>
				<th>合伙人T1费率</th>
				<th>是否计算</th>
				<th>分润百分比</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="list">
				<tr>
					<td>${list.partnerName}</td>
					<td>${list.agentid}</td>
					<td>${list.agentName}</td>
					<td>${list.agentNO}</td>
					<td>${z:getBankIDName(list.bankID)}</td>
					<td>${list.t0rate}</td>
					<td>${list.t1rate}</td>
					<td>${z:activeProfit(list.active)}</td>
					<td>${list.ratio}</td>
					<td><a href="form?id=${list.id}">修改</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>