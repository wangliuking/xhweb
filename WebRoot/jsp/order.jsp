<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>派单</title>

<meta name="keywords" content="keyword1,keyword2,keyword3">
<meta name="description" content="this is my page">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="-1">
<script type="text/javascript" src="Resources/js/xhbootstrap-css.js"></script>

<style type="text/css">
.jsp-content {
	margin-left: 10px;
}

.order-table td {
	padding: 0;
}

.order-form {
	width: 100%;
	line-height: 100%;
	border: 0;
	padding: 6px;
	/* line-height:100%; */
}

.order-form:FOCUS {
	border: 0;
}

.textarea-div {
	width: 100%;
	height: 60px;
	margin-left: auto;
	margin-right: auto;
	padding: 3px;
	outline: 0;
	border: 0px solid #a0b3d6;
	font-size: 12px;
	word-wrap: break-word;
	overflow-x: hidden;
	overflow-y: auto;
	background: #024582;

	/* border-color: rgba(82, 168, 236, 0.8); */
	/* box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1), 0 0 8px rgba(82, 168, 236, 0.6); */
}

.input-div {
	outline: none;
	border: none;
	width: 100%;
	background: #024582;
}

.input-div:FOCUS {
	border: 1px solid #1474c0;
}

.form-select {
	outline: none;
	border: none;
	width: 100%;
	background: #024582;
}
/* CSS部分 */
#type-ul {
	list-style: none;
	margin: 0;
	padding: 0;
	text-align: center;
	background: #024582;
	color: #000;
	-webkit-transform: scaleY(0);
	-webkit-transform-origin: 0 0;
}

#type-ul li {
	padding: 0;
	margin: 0;
}

.type-div {
	width: 100px;
}

.type-div ul li {
	cursor: pointer;
	padding: 3px;
	border: 1px solid #ccc;
}

.type-div:hover ul {
	-webkit-animation: ulShow 0.4s;
	-webkit-animation-fill-mode: forwards;
	-webkit-animation-timing-function: cubic-bezier(0, 0.8, 0.9, 1);
}

type-div {
	height: 30px;
	line-height: 30px;
	text-align: center; /* background-color:#FC0; */
}

@
-webkit-keyframes ulShow {from { -webkit-transform:scaleY(0);
	
}

to {
	-webkit-transform: scaleY(1);
}

}
#level-ul {
	list-style: none;
	margin: 0;
	padding: 0;
	text-align: center;
	background: #fafafa;
	color: #000;
	-webkit-transform: scaleY(0);
	-webkit-transform-origin: 0 0;
}

#level-ul li {
	padding: 0;
	margin: 0;
}

.level-div {
	width: 100px;
}

.level-div ul li {
	cursor: pointer;
	padding: 3px;
	border: 1px solid #ccc;
}

.level-div:hover ul {
	-webkit-animation: ulShow 0.4s;
	-webkit-animation-fill-mode: forwards;
	-webkit-animation-timing-function: cubic-bezier(0, 0.8, 0.9, 1);
}

level-div {
	height: 30px;
	line-height: 30px;
	text-align: center; /* background-color:#FC0; */
}

@
-webkit-keyframes ulShow {from { -webkit-transform:scaleY(0);
	
}

to {
	-webkit-transform: scaleY(1);
}

}
.slDiv {
	float: left;
	margin-top: 5px;
	position: relative;
}

.slDiv #btnSelect {
	min-width: 125px;
	display: inline-block;
	text-indent: 10px;
}

.slDiv .ulDiv {
	display: none;
	width: 125px;
	border: 1px solid green;
	border-radius: 1px;
	background: #024582;
	margin-top: 9px;
	position: absolute;
	z-index: 100;
	max-height: 400px;
	overflow: auto;
}

.slDiv .ulDiv.ulShow {
	display: block;
}

.slDiv .ulDiv ul li {
	text-indent: 10px;
	cursor: pointer;
}

.slDiv .ulDiv ul li:hover {
	background: #cbebf6;
}

.slDiv .ulDiv ul,.slDiv .ulDiv li {
	margin: 5px 0px;
	padding: 0px;
}

.content: {
	
}

body,.content {
	background: #072f54;
}

#order {
	background: #072f54;
	color: #fff;
}

#order-table {
	color: #fff;
	font-size: 13px;
}

.btn-other {
	background: #1991ee;
}
</style>
</head>

<body class="fixed-iframe">
	<div class="content animate-panel" style="padding-top:0px;">
		<div class="row">
			<div class="col-md-12" style="padding-top:0px; margin:0px;">

				<div id="order">
					<form action="" onsubmit="false" id="order-form">
						<table id="order-table"
							style="width:100%;border-collapse: collapse;">

							<tbody>
								<tr>
									<td colspan="6" style="text-align: center;">故障处理任务单</td>
								</tr>
								<tr>
									<input type="hidden" class="input-div" name="id">
									<input type="hidden" class="input-div" name="from">
									<input type="hidden" class="input-div" name="zbdldm">
								</tr>
								<tr>
									<td>基站ID</td>
									<td><input class="input-div" name="bsId" value="${bsId}"></td>
									<td>基站名称</td>
									<td colspan="3"><input class="input-div" name="name"
										value="${name }"></td>
								</tr>

								<tr>
									<td style="height:40px;width:130px;">派单时间</td>
									<td style="height:40px;width:250px;"><input
										class="input-div" name="dispatchtime" value="${nowDate}">
									</td>
									<td style="height:40px;width:100px;">派单人</td>
									<td style="height:40px;width:240px;"><input
										class="input-div" name="dispatchman">
									</td>
									<td style="width:80px;">接单人</td>
									<td>

										<div class="slDiv">
											<span id="btnSelect">点击搜索</span> <input id="userid"
												name="userid" type="hidden"></input>
											<div class="ulDiv order_man">
												<ul>
													<c:forEach items="${userlist}" var="x">
														<li value="${x.user}">${x.userName}</li>
													</c:forEach>

												</ul>
											</div>
										</div>
									</td>
								</tr>
								<tr style="height:60px;">
									<td>故障类型</td>
									<td>
										<!-- <div contenteditable="true" class="input-div" name="errtype"></div> -->
										<div class="slDiv">
											<span id="btnType">点击选择故障类型</span> <span name="errtype"
												style="display: none"></span>
											<div class="ulDiv ul_type">
												<ul>
													<li>断站故障</li>
													<li>隐患故障</li>
													<li>机房配套问题</li>
												</ul>
											</div>
										</div> <!-- <div class="type-div">
                                            <ul id="type-ul">
                                                <li>断站故障</li>
                                                <li>隐患故障</li>
                                                <li>机房配套问题</li>
                                            </ul>
                                        </div> -->
									</td>
									<td colspan="4">1、断站故障 2、隐患故障 3、机房配套问题</td>
								</tr>
								<tr style="height:80px;">
									<td>故障等级</td>
									<td>
										<div class="slDiv">
											<span id="btnLevel">点击选择故障等级</span> <span name="errlevel"
												style="display: none"></span>
											<div class="ulDiv ul_level">
												<ul>
													<li>A级</li>
													<li>B级</li>
													<li>C级</li>
													<li>D级</li>
												</ul>
											</div>
										</div> <!-- <div contenteditable="true" class="input-div" name="errlevel"></div>

                                        <div class="level-div">
                                            <ul id="level-ul">
                                                <li>A级</li>
                                                <li>B级</li>
                                                <li>C级</li>
                                                <li>D级</li>
                                            </ul>
                                        </div> -->
									</td>
									<td colspan="4">
										<p>A级，包括一级基站断站，1小时40分钟内处理恢复；</p>
										<p>B级，包括二级基站断站，2小时40分钟内处理恢复；</p>
										<p>C级，包括三级基站断站，4小时40分钟内处理恢复；</p>
										<p>D级，包括隐患故障和机房配套问题，3天内处理恢复</p>
									</td>
								</tr>
								<tr>
									<td>故障发现时间</td>
									<td><input class="input-div"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										name="errfoundtime" value="${errfoundtime}"></td>
									<td>故障恢复时间</td>
									<td colspan="3"><input class="input-div"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										name="errslovetime"></td>
								</tr>
								<tr>
									<td>处理进展</td>
									<td colspan="5"><textarea class="textarea-div"
											name="progress"></textarea></td>
								</tr>
								<tr>
									<td colspan="6">故障描述：${errInfo }</td>
								</tr>
								<tr>
									<td colspan="6">注：故障处理暂未恢复或预处理临时恢复，交付抢修组进行下一步处理。</td>
								</tr>
								<!-- <tr>
											<td>处理结果</td>
											<td colspan="5"><textarea ng-disabled="true"
													class="textarea-div" name="proresult"></textarea></td>
										</tr>
										<tr>
											<td>处理人</td>
											<td colspan="2"><input class="input-div"
												ng-disabled="true" name="workman"></td>
											<td>审核人</td>
											<td colspan="2"><input class="input-div"
												ng-disabled="true" name="auditor"></td>
										</tr> -->
							</tbody>
						</table>
					</form>
				</div>
				<div class="row">
					<div class="col-md-12">
						<button type="button" class="btn btn-block btn-primary"
							 data-dismiss="modal" id="order-btn"
							onclick="xh.order()" >
							<i class="fa fa-plus"></i>&nbsp;派单
						</button>
					</div>

				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="Resources/js/xhbootstrap-js.js"></script>
	<script type="text/javascript"
		src="lib/metisMenu/dist/metisMenu.min.js"></script>
	<script type="text/javascript" src="jsp/order.js"></script>
	<script type="text/javascript">
		$(".type-div>ul>li").on('click', function() {
			$("div[name='errtype']").html($(this).html())
		})
		$(".level-div>ul>li").on('click', function() {
			$("div[name='errlevel']").html($(this).html())
		})
		$('.slDiv #btnSelect').on('click', function() {
			$(this).siblings('.ulDiv').toggleClass('ulShow');
		});
		$('.slDiv .order_man ul').delegate('li', 'click', function() {
			var selTxt = $(this).text();
			var userid = $(this).attr("value");

			$('.slDiv #btnSelect').text(selTxt);
			$('.slDiv #userid').val(userid);
			$('.ulDiv').removeClass('ulShow');
		});
		$('.slDiv #btnType').on('click', function() {
			$(this).siblings('.ulDiv').toggleClass('ulShow');
		});
		$('.slDiv .ul_type ul').delegate('li', 'click', function() {
			var selTxt = $(this).text();
			$('.slDiv #btnType').text(selTxt);
			$('span[name="errtype"]').text(selTxt);
			$('.ulDiv').removeClass('ulShow');
		});
		$('.slDiv #btnLevel').on('click', function() {
			$(this).siblings('.ulDiv').toggleClass('ulShow');
		});
		$('.slDiv .ul_level ul').delegate('li', 'click', function() {
			var selTxt = $(this).text();
			$('.slDiv #btnLevel').text(selTxt);
			$('span[name="errlevel"]').text(selTxt);
			$('.ulDiv').removeClass('ulShow');
		});
	</script>

	<script type="text/javascript">
		
	</script>
</body>
</html>
