<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加</title>
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>


<form action="/partner/add" method="post" role="form">
<input type="hidden" name="id" value="${partner.id}"/>
	<div class="form-group">
		<label for="partnername">汇合伙人姓名</label>
		<input type="text" class="form-control" id="partnername" name="partnername" value="${partner.partnerName}"  placeholder="请输入名称">
	</div>

	<div class="form-group">
		<label for="agentName">代理商名称</label>
		<input type="text" class="form-control" id="agentName" name="agentName" value="${partner.agentName}"  placeholder="请输入名称">
	</div>
	
		<div class="form-group">
		<label for="agentNO">代理商编号</label>
		<input type="text" class="form-control" id="agentNO" name="agentNO" value="${partner.agentNO}"   placeholder="请输入名称">
	</div>
	
		<div class="form-group">
			<label for="bankID">支付类型</label>
			<select id="bankID" name="bankID" class="form-control" >
				<option value="Alipay">支付宝</option>
				<option value="OnlineKuaiPay">普通快捷（无积分）</option>
				<option value="NET">网关</option>
				<option value="QQ">QQ</option>
				<option value="UnionPay">银联二维码</option>
				<option value="WX">微信</option>
				<option value="KUAI2B">普通快捷（带积分）</option>
				<option value="KUAI2C">小额快捷（无积分）</option>
				<option value="ESKUAI">特殊快捷（对公结算）</option>
				<option value="TKUAI">普通快捷（大额无积分）</option>
				<option value="DIRKUAI">无短快捷（带积分）</option>
				<option value="UNIONPAY_QRCODE_LARGE">银联二维码（大额）</option>
				<option value="MKUAI">同名快捷（无积分）</option>
			</select>
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