if (!("xh" in window)) {
	window.xh = {};
};
require.config({
	paths : {
		echarts : '../../lib/echarts'
	}
});
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
var appElement = document.querySelector('[ng-controller=xhcontroller]');
xh.load = function() {
	var app = angular.module("app", []);
	app.filter('qualitystatus', function() { // 可以注入依赖
		return function(text) {
			if (text == 0) {
				return "未签收";
			} else if (text == 1) {
				return "签收";
			}
		};
	});

	app.controller("xhcontroller", function($scope, $http) {
		xh.maskShow();
		$scope.type="2";
		$scope.time=xh.getNowMonth();
		
		/* 获取用户权限 */
		$http.get("../../web/loginUserPower").success(
				function(response) {
					$scope.up = response;
				});
		// 获取登录用户
		$http.get("../../web/loginUserInfo").success(function(response) {
			xh.maskHide();
			$scope.loginUser = response.user;
			console.log("loginuser="+$scope.loginUser);
			$scope.loginUserRoleId = response.roleId;
			$scope.roleType = response.roleType;
		});

		$scope.getData=function(){
			var time=$("input[name='time']").val()==""?$scope.time:$("input[name='time']").val();
			$http.get("../../flow/month_user_flow?time="+time).success(
					function(response) {
						xh.maskHide();
						$scope.data = response.data;
					});
		}
		$scope.showDate=function(){
			var type=$("select[name='type']").val();
			$("input[name='time']").val("");
			var a="",b="",c="";
			if(type==1){
				a="选择周";
				b="WdatePicker({isShowWeek:true,onpicked:funccc,errDealMode:3,firstDayOfWeek:1})";
				c="周"
				
			}else if(type==2){
				a="选择月份";
				b="WdatePicker({dateFmt:'yyyy-MM'})"
				c="月";
			}
			else if(type==3){
				a="选择年";
				b="WdatePicker({dateFmt:'yyyy'})";
				c="年"
				
			}else{
				a="选择年份";
				b="WdatePicker({dateFmt:'yyyy'})";
				c="年";
			}
			$("label[for='time']").html(a);
			$("input[name='time']").attr("onfocus",b);
			$("input[name='time']").attr("placeholder",c);
		}
		$scope.line1 = function() {
			
			xh.maskShow();
			var time=$("input[name='time']").val()==""?$scope.time:$("input[name='time']").val();
			// 设置容器宽高
			 var resizeBarContainer = function() {
			  $("#line1").width(parseInt($("#line1").parent().width()));
			  $("#line1").height(250);
			  };
			  resizeBarContainer();
			 
			// 基于准备好的dom，初始化echarts实例
			var chart = null;
			if (chart != null) {
				chart.clear();
				chart.dispose();
			}
			require([ 'echarts', 'echarts/chart/bar','echarts/chart/line' ], function(ec) {
				chart = ec.init(document.getElementById('line1'));
				chart.showLoading({
					text : '正在努力的读取数据中...'
				});
				var option = {
					    title : {
					        text: ''/*,
					        subtext: '纯属虚构'*/
					    },
					    tooltip : {
							trigger : 'item'
						},
					    legend: {
					        data:['上行流量','下行流量']
					    },
					    calculable : true,
					    yAxis : [
							      {
				                    type: 'value',
				                    name:'流量G'
				                    }
							    ],
					    xAxis : [
					        {
					            type : 'category',
					            name:'用户',
					            left:200,
					            data : []
					        }
					    ],
					    grid: {
					         x: 150
					       },
					    
					    series : [
					        {
					            
					            type:'bar',
					            name:'上行流量',
					            barWidth:30,
					            barCategoryGap:'70%',
					           
					            data:[],
					            itemStyle:{
					            	normal:{
					            		color:'#FF82AB',
					            		label:{
					            			 show: true,//是否展示
						                        textStyle: {
						                       		fontWeight:'bolder',
						                        	fontSize : '12',
						                        	color:'#000',
						                            fontFamily : '微软雅黑',
						                        }

							            }
					            		}},    
					        },
{
					            
					            type:'bar',
					            name:'下行流量',
					            barWidth:30,
					            data:[],
					            itemStyle:{
					            	normal:{
					            		color:'green',
					            		label:{
					            			 show: true,//是否展示
						                        textStyle: {
						                       		fontWeight:'bolder',
						                        	fontSize : '12',
						                        	color:'#000',
						                            fontFamily : '微软雅黑',
						                        }

							            }
					            		}},    
					        }
					    ]
					};

				$.ajax({
					url : '../../flow/month_user_flow?time='+time,
					data:{
						time:time==""?$scope.time:time,
					},
					type : 'POST',
					dataType : "json",
					async : false,
					success : function(response) {
						var data = response.data;
						var xData=[],yData=[],y2Data=[]
						
						if(data.length>0){
							for(var i=0;i<data.length;i++){
								xData.push(data[i].userName);
								yData.push(data[i].up);
								y2Data.push(data[i].down);
							}
							
							option.series[0].data = yData;
							option.series[1].data = y2Data;
							option.xAxis[0].data = xData;
							chart.hideLoading();
							chart.setOption(option);
						}else{
							$("#line1").html("没有数据")
						}
						
						
						xh.maskHide();

					},
					failure : function(response) {
						xh.maskHide();
					}
				});
				

			});
			
			// 用于使chart自适应高度和宽度
			window.onresize = function() {
				// 重置容器高宽
				 resizeBarContainer();
			};
		};
		$scope.elec = function() {
			
			xh.maskShow();
			var time=$("input[name='time']").val();
			var jd=$("select[name='jd']").val();
			// 设置容器宽高
			 var resizeBarContainer = function() {
			  $("#bar-elec").width(parseInt($("#bar-elec").parent().width()));
			  $("#bar-elec").height(350);
			  };
			  resizeBarContainer();
			 
			// 基于准备好的dom，初始化echarts实例
			var chart = null;
			if (chart != null) {
				chart.clear();
				chart.dispose();
			}
			require([ 'echarts', 'echarts/chart/bar','echarts/chart/line' ], function(ec) {
				chart = ec.init(document.getElementById('bar-elec'));
				chart.showLoading({
					text : '正在努力的读取数据中...'
				});
				var option = {
					    title : {
					        text: ''/*,
					        subtext: '纯属虚构'*/
					    },
					    /*tooltip : {
					        trigger: 'axis'
					    },*/
					    tooltip : {
							trigger : 'item'
						},
					    legend: {
					        data:['断电次数']
					    },
					    calculable : true,
					    xAxis : [
					        {
					            type : 'category',
					            name: '基站ID',
					            data : []
					        }
					    ],
					    yAxis : [
					      {
		                    type: 'value',
		                    name: '断电次数(次)',
		                    min: 0,
		                    
		                    position: 'left',
		                    axisLabel: {
		                        formatter: '{value}'
		                    }
		                    }
					    ],
					    series : [
					        {
					            name:'断电次数',
					            type:'bar',
					            data:[],
					            itemStyle:{
					            	normal:{
					            		color:'#E066FF',
					            		label:{
					            			 show: true,//是否展示
						                        textStyle: {
						                       		fontWeight:'bolder',
						                        	fontSize : '12',
						                        	color:'#000',
						                            fontFamily : '微软雅黑',
						                        }

							            }
					            	}
					          },
					            yAxisIndex:0,
					            barWidth: 20
					        }
					    ]
					};

				$.ajax({
					url : '../../top10/elec',
					data:{
						type:$scope.type,
						time:time==""?$scope.time:time,
						jd:jd
					},
					type : 'POST',
					dataType : "json",
					async : false,
					success : function(response) {
						var data = response.data;
						var xData=[],yData=[]
						
						if(data.length>0){
							for(var i=0;i<data.length;i++){
								xData.push(data[i].bsId);
								yData.push(data[i].total);
							}
							
							option.series[0].data = yData;
							option.xAxis[0].data = xData;
							chart.hideLoading();
							chart.setOption(option);
						}else{
							$("#bar-elec").html("没有数据")
						}
						xh.maskHide();

					},
					failure : function(response) {
						xh.maskHide();
					}
				});

			});
			
			// 用于使chart自适应高度和宽度
			window.onresize = function() {
				// 重置容器高宽
				 resizeBarContainer();
			};
		};
		$scope.getData();
		$scope.line1();
		$scope.search=function(){
			$scope.getData();
			$scope.line1();

		}
	});
};
//刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};

xh.getNowMonth=function()   
{   
    var   today=new Date();      
    var   yesterday_milliseconds=today.getTime();    //-1000*60*60*24

    var   yesterday=new   Date();      
    yesterday.setTime(yesterday_milliseconds);      
        
    var strYear=yesterday.getFullYear(); 

    var strDay=yesterday.getDate();   
    var strMonth=yesterday.getMonth()+1; 

    if(strMonth<10)   
    {   
        strMonth="0"+strMonth;   
    } 
    var strYesterday=strYear+"-"+strMonth;   
    return  strYesterday;
}
function funccc(){

	$dp.$('time').value=$dp.cal.getP('y')+"年第"+$dp.cal.getP('W','WW')+"周";



	}
