/**

 * 资产管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
var appElement = document.querySelector('[ng-controller=xhcontroller]');
toastr.options = {
	"debug" : false,
	"newestOnTop" : false,
	"positionClass" : "toast-top-center",
	"closeButton" : true,
	/* 动态效果 */
	"toastClass" : "animated fadeInRight",
	"showDuration" : "300",
	"hideDuration" : "1000",
	/* 消失时间 */
	"timeOut" : "1000",
	"extendedTimeOut" : "1000",
	"showMethod" : "fadeIn",
	"hideMethod" : "fadeOut",
	"progressBar" : true,
};
xh.load = function() {
	var app = angular.module("app", []);

	app.controller("xhcontroller", function($scope, $http) {
		$scope.businessMenu=true; //菜单变色
		
		$scope.data=[];
		$scope.totals=0;
		$scope.trigger=3;
		$scope.date="";
		$scope.selectDate=[];
		$scope.selectDateTotals=0;
		var run=0;
		
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
				});
		
		$scope.showTrigger=function(){
			$scope.trigger=$("input[name='operation']:checked").val();
			run=0;
		}
		$scope.showTx=function(){
			run=0;
		}
		
		/* 刷新数据 */
		$scope.refresh = function() {
			$scope.search(1);
			$("#table-checkbox").prop("checked", false);
		};
		//添加一个用户
		$scope.addOneUser=function(){
			run=0;
			var userId=$("#userId").val();
			var record={userId:'',status:"等待执行",result:""};
			
			if(userId==""){
				toastr.error("用户ID不能为空", '提示');
				return;
			}
			i
			record.userId=userId;
			var flag=0;
			for(var i=0;i<$scope.data.length;i++){
				if($scope.data[0].userId==userId){
					flag=1;
				}
			}
			if(flag==0){
				$scope.data.push(record);
			}
			
			$scope.totals=$scope.data.length;
		};
		/* 添加多用户 */
		$scope.addMore = function() {
			run=0;
			var user1=parseInt($("#user1").val());
			var user2=parseInt($("#user2").val());
			if(user2-user1<0){
				toastr.error("用户ID区间不正确", '提示');
				return;
			}
			if(user2-user1>50){
				toastr.error("批量添加最多可以添加50个终端号码", '提示');
				return;
			}
			for(var i=0;i<=user2-user1;i++){
				var record={userId:'',status:"等待执行",result:""};
				record.userId=user1+i;	
				var flag=0;
				for(var j=0;j<$scope.data.length;j++){
					if($scope.data[j].userId==record.userId){
						flag=1;
					}
				}
				if(flag==0){
					$scope.data.push(record);
					
				}
				
			}
			$scope.totals=$scope.data.length;
			
			
		};
		$scope.addMore2 = function() {
			run=0;
			var user=$("#userId").val();
			var uu=user.split(",");
			
			for(var i=0;i<uu.length;i++){
				
				var record={userId:'',status:"等待执行",result:""};
				record.userId=uu[i];	
				var flag=0;
				for(var j=0;j<$scope.data.length;j++){
					if($scope.data[j].userId==record.userId){
						flag=1;
					}
				}
				if(flag==0){
					$scope.data.push(record);
					
				}
				
			}
			$scope.totals=$scope.data.length;
			
			
		};
		$scope.addMore3= function() {
			run=0;
			var bsId=$("#bsId").val();
			if(bsId==""){
				return;
			}
			$http.get("../../operations/data/userOnlineByBs?bsId="+bsId).success(
					function(response) {
						var uu = response.items;
						if(uu==null){
							return;
						}
						for(var i=0;i<uu.length;i++){
							
							var record={userId:'',status:"等待执行",result:""};
							record.userId=uu[i].userId;	
							var flag=0;
							for(var j=0;j<$scope.data.length;j++){
								if($scope.data[j].userId==record.userId){
									flag=1;
								}
							}
							if(flag==0){
								$scope.data.push(record);
								
							}
							
						}
						$scope.totals=$scope.data.length;
			});
			
		};
		$scope.addMore4= function() {
			run=0;
			$http.get("../../gpsOperation/now_gps_close").success(
					function(response) {
						var uu = response.items;
						if(uu==null){
							return;
						}
						for(var i=0;i<uu.length;i++){
							
							var record={userId:'',status:"等待执行",result:""};
							record.userId=uu[i].userId;	
							var flag=0;
							for(var j=0;j<$scope.data.length;j++){
								if($scope.data[j].userId==record.userId){
									flag=1;
								}
							}
							if(flag==0){
								$scope.data.push(record);
								
							}
							
						}
						$scope.totals=$scope.data.length;
			});
			
		};
		$scope.getHandleResult=function(){
			if($scope.selectData==null || $scope.selectData.length<1){
				return null;
			}
			var ss=[];
			for(var i=0;i<$scope.selectData.length;i++){
				ss.push($scope.selectData[i].userId)
			}
			var operation=$("input[name='operation']:checked").val();
			var gpsen=$("input[name='gpsen']:checked").val();
			var type=null;
			if(operation==1){
				type=3;
			}else if(operation==2){
				type=2;
			}
			else if(operation==3){
				if(gpsen==1){
					type=1;
				}else{
					type=0;
				}
			}
			$http.get("../../gpsOperation/now_operation_record?time="+$scope.date+"&type="+type+"&ids="+ss.join(",")).success(
					function(response) {
						var data = response.items;
						for(var i=0;i<$scope.selectData.length;i++){
							for(var j=0;j<$scope.data.length;j++){
								if($scope.selectData[i].userId==$scope.data[j].userId){
									var record=$scope.data[j];
									for(var k=0;k<data.length;k++){
										if(record.userId==data[k].dstId){
											if(data[k].operationOpt==1){
												if(data[k].status==0){
													$scope.data[j].result="失败"
												}else if(data[k].status==1){
													$scope.data[j].result="成功"
												}else if(data[k].status==2){
													$scope.data[j].result="等待终端回复"
												}else if(data[k].status==3){
													$scope.data[j].result="调用东信服务失败"
												}
											}else{
												if(data[k].status==0){
													$scope.data[j].result="成功"
												}else if(data[k].status==1){
													$scope.data[j].result="失败"
												}else if(data[k].status==2){
													$scope.data[j].result="等待终端回复"
												}else if(data[k].status==3){
													$scope.data[j].result="调用东信服务失败"
												}
											}
											
										}
									}
								}
							}
						}
						
						
						
			});
		}
		
		/* 删除 */
		$scope.delBs = function(id) {
			$scope.data.splice(id,1);
			$scope.totals=$scope.data.length;
			
		};
		/*刷新*/
		$scope.refresh=function(){
			$scope.data=$scope.data;
		}
		/* 清空 */
		$scope.clear = function() {
			$scope.data.splice(0,$scope.data.length);
			$scope.totals=$scope.data.length;	
		};
		var i=0;  var flag=0;var successTag=false;
		$scope.startBtn=false;
		$scope.task=function(i){
			if($scope.start(i)){
				//$scope.data[i].status="数据发送成功";	
				flag=0;
				
			}else{
				//$scope.data[i].status="失败！";
				flag=0;
			}
			
		}
		//设置
		$scope.start=function(i){
			window.localStorage.setItem("gpsset_time",xh.getNowDate());	
			$scope.date=xh.getNowDate();
			console.log("fff->"+i)
			var data=[];
			data.push($scope.selectData[i].userId);
			var operation=$("input[name='operation']:checked").val();
			var locationDstId=$("input[name='locationDstId']").val()==''?0:$("input[name='locationDstId']").val();
			var triggerParaTime=$("input[name='triggerParaTime']").val()==''?30:$("input[name='triggerParaTime']").val();
			var gpsen=$("input[name='gpsen']:checked").val();
			if(operation==2 && (triggerParaTime=='' || triggerParaTime<10)){
				toastr.error("定时触发器设置时间不能小于10秒钟", '提示');
				$('button').prop('disabled', false);
				return;	
			}
			$http({
				method:'post',
				url : '../../ucm/gpsset',
				headers:{'Content-Type': 'application/x-www-form-urlencoded'},
				transformRequest: function (data) {
				    　　return $.param(data);
				},
				data:{
					data:data.join(","),
					//dstId:dstId,
					operation:operation,
					triggerParaTime:triggerParaTime,
					//locationDstId:locationDstId,
					gpsen:gpsen
				}	
			}).success(function(data){ 
				if (data.success) {
					successTag=true;
					
					for(var j=0;j<$scope.totals;j++){
						if($scope.selectData[i].userId==$scope.data[j].userId){
							$scope.data[j].status=data.message;
						}					
					}
				} else {
					successTag=false;
					for(var j=0;j<$scope.totals;j++){
						if($scope.selectData[i].userId==$scope.data[j].userId){
							$scope.data[j].status=data.message;
						}					
					}
				}
			}).error(function(e){
				successTag=false;
				
				for(var j=0;j<$scope.totals;j++){
					if($scope.selectData[i].userId==$scope.data[j].userId){
						$scope.data[j].status="服务器响应超时";
					}					
				}
			})
			return successTag;
		};
		$scope.run=function(){
			run=0;
			var data=[];
			$("[name='tb-check']:checkbox").each(function() {
				if ($(this).is(':checked')) {
					var record={userId:''};
					record.userId=$(this).attr("value");
					data.push(record);
				}
			});
			
			if (data.length < 1) {
				swal({
					title : "提示",
					text : "请至少选择一条数据",
					type : "error"
				});
				return;
			}
			$scope.selectData=data;
			$scope.selectDataTotals=data.length;
			console.log("ddd->"+JSON.stringify($scope.selectData))
			
			for(var k=0;k<$scope.selectDataTotals;k++){
				var ss=$scope.selectData[k].userId;
				for(var j=0;j<$scope.totals;j++){
					if($scope.data[j].userId==ss){
						$scope.data[j].status="等待执行";
						$scope.data[j].result="";
					}					
				}
			}
			$scope.startBtn=true;
			$('button').prop('disabled', true);
		
			for(var j=0;j<$scope.totals;j++){
				if($scope.selectData[i].userId==$scope.data[j].userId){
					$scope.data[j].status="处理中，请稍等!";
				}					
			}
			
			
			var timeout=setInterval(function(){
				if(flag==0){
					flag=1;
					if(i<$scope.selectDataTotals){					
						$scope.task(i);
						i++;
					}else{
						clearInterval(timeout);
						$scope.startBtn=false;
						$('button').prop('disabled', false);
						toastr.success("数据发送完成", '提示');
						run=1;
						
						i=0;flag=0;successTag=false;
						
						
						
					}
				}
			},1000);
		}
		
		
		var timeout=setInterval(function(){
			if(run==1){
				$scope.getHandleResult();
			}
		},3000)	
	});
	
};
xh.getNowDate=function()   
{   
    var   today=new Date();      
    var   yesterday_milliseconds=today.getTime();    //-1000*60*60*24

    var   yesterday=new   Date();      
    yesterday.setTime(yesterday_milliseconds);      
        
    var strYear=yesterday.getFullYear(); 

    var strDay=yesterday.getDate();   
    var strMonth=yesterday.getMonth()+1; 
    var strH=yesterday.getHours();
    var strM=yesterday.getMinutes();
    var strS=yesterday.getSeconds();

    if(strMonth<10)   
    {   
        strMonth="0"+strMonth;   
    } 
    if(strDay<10){
    	strDay="0"+strDay;
    }
    if(strH<10){
    	strH="0"+strH;
    }
    if(strM<10){
    	strM="0"+strM;
    }
    if(strS<10){
    	strS="0"+strS;
    }
    var strYesterday=strYear+"-"+strMonth+"-"+strDay+" "+strH+":"+strM+":"+strS;   
    return  strYesterday;
}
