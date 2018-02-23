<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改合伙人费率</title>
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>


<form action="/partner/update" method="post" role="form">
<input type="hidden" name="id" value="${partner.id}"/>
	<div class="form-group">
		<label for="partnername">汇合伙人姓名</label>
		<input type="text" class="form-control" id="partnername" name="partnername" value="${partner.partnerName}" readonly="readonly"  placeholder="请输入名称">
	</div>
	
	<div class="form-group">
		<label for="agentid">代理商ID</label>
		<input type="text" class="form-control" id="agentid" value="${partner.agentid}" readonly="readonly"  placeholder="请输入名称">
	</div>

	<div class="form-group">
		<label for="agentName">代理商ID</label>
		<input type="text" class="form-control" id="agentName" value="${partner.agentName}" readonly="readonly"  placeholder="请输入名称">
	</div>
	
		<div class="form-group">
		<label for="agentNO">代理商编号</label>
		<input type="text" class="form-control" id="agentNO" value="${partner.agentNO}" readonly="readonly"  placeholder="请输入名称">
	</div>
	
		<div class="form-group">
			<label for="bankID">支付类型</label>
			<input type="text" class="form-control" id="bankID" value="${partner.bankID}" readonly="readonly"  placeholder="请输入名称">
		</div>

		<div class="form-group">
			<label for="t0rate">合伙人T0费率(千分比)</label>
				<input type="text" class="form-control" id="t0rate" name="t0rate" value="${partner.t0rate}"   placeholder="请输入名称">
				
		</div>
		
		<div class="form-group">
			<label  class="col-sm-2 control-label">合伙人T1费率(千分比)</label>
			<input type="text" class="form-control" id="t1rate" name="t1rate" value="${partner.t1rate}"  placeholder="请输入名称">
		</div>
		
		
		<div class="form-group">
			<label for="active">是否计算 1 计算 0 不计算</label>
				<input type="text" class="form-control" id="active" name="active" value="${partner.active}"  placeholder="请输入名称">
		</div>
		
		<div class="form-group">
			<label for="ratio">分润百分比</label>
				<input type="text" class="form-control" id="ratio" name="ratio" value="${partner.ratio}" placeholder="请输入名称">
		</div>
	
	<button type="submit" class="btn btn-default">提交</button>
</form>
</body>
</html>