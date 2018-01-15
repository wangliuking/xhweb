/**
 * 用户管理
 */
if (!("xh" in window)) {
	window.xh = {};
};
require.config({
	paths : {
		echarts : 'lib/echarts'
	}
});
var alarmbs=true;
var alarmji=true;
var appElement = document.querySelector('[ng-controller=frist]');
xh.load = function() {
	var app = angular.module("app", []);
	app.filter('bsName', function() { // 可以注入依赖
		return function(text) {
			
			if(text.indexOf("，")>0){
				return text.split("，")[0];
			}else{
				return text;
			}
			
		};
	});
	app.filter('alarmContent', function() { // 可以注入依赖
		return function(text) {
			
			if(text.indexOf("，")>0){
				return text.split("，")[2];
			}else{
				return text;
			}
			
		};
	});
	app.controller("frist", function($scope, $http) {
		xh.map();
		xh.callTop5();
		xh.groupTop5();
		xh.userTop5();
		xh.callInfo();

	});
};

xh.map=function(){
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#map").width(600);
		$("#map").height(500);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	
	
	require([ 'echarts', 'echarts/chart/map' ], function(ec) {
		chart = ec.init(document.getElementById('map'));
		require('echarts/util/mapData/params').params.CD = {
		    getGeoJson: function (callback) {
		        $.getJSON('lib/echarts/util/mapData/params/510100.json',callback);
		    }
		}
		var option = {
			    backgroundColor: '',
			    color: ['gold','aqua','lime'],
			 
			    textStyle:{
                	color:'#fff'
                },
			    tooltip : {
			        trigger: 'item',
			        formatter: '{b}'
			    },
			   /* legend: {
			        orient: 'vertical',
			        x:'left',
			        data:['北京 Top10', '上海 Top10', '广州 Top10'],
			        selectedMode: 'single',
			        selected:{
			            '上海 Top10' : false,
			            '广州 Top10' : false
			        },
			        textStyle : {
			            color: '#fff'
			        }
			    },*/
			  
			   /* dataRange: {
			        min : 0,
			        max : 100,
			        calculable : true,
			        color: ['#ff3333', 'orange', 'yellow','lime','aqua'],
			        textStyle:{
			            color:'#fff'
			        }
			    },*/
			    series : [
			        {
			            name: '全国',
			            type: 'map',
			            roam: true,
			            hoverable: false,
			            mapType: 'CD',
			            itemStyle:{
			                normal:{
			                	label:{
			                		show:true,
			                		textStyle:{
			    			            color:'#fff'
			    			        }
			                		} ,
			                    borderColor:'#fff',
			                    borderWidth:0.5,
			                    areaStyle:{
			                        color: '#4B0082'
			                    },
			                    
			                    emphasis:{label:{show:true}} 
			                }
			            },
			        
		                
			            data:[],
			            markLine : {
			                smooth:true,
			                symbol: ['none', 'circle'],  
			                symbolSize : 1,
			                itemStyle : {
			                    normal: {
			                        color:'#fff',
			                        borderWidth:1,
			                        borderColor:'rgba(30,144,255,0.5)'
			                    }
			                },
			                data : [
			                    [{name:'北京'},{name:'包头'}]
			                ],
			            },
			            geoCoord: {
			                '上海': [121.4648,31.2891]
			            }
			        }
			    ]
			};
		
		chart.setOption(option);

	});
	/*window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};*/
	
}
xh.callTop5=function(){
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#call-top5").width(450);
		$("#call-top5").height(150);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	
	
	require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
		chart = ec.init(document.getElementById('call-top5'));
		
		var option = {
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        textStyle:{
			        	color:'#fff',
			        },
			        data:['测试基站1','测试基站2','测试基站3','测试基站4','测试基站5']
			    },
			  
			    calculable : false,
			    series : [
			        {
			            name:'基站呼叫TOP5',
			            type:'pie',
			            radius : ['50%', '70%'],
			            center : ['200', 60],
			            itemStyle : {
			                normal : {
			                    label : {
			                        show : false
			                    },
			                    labelLine : {
			                        show : false
			                    }
			                },
			                emphasis : {
			                    label : {
			                        show : true,
			                        position : 'center',
			                        textStyle : {
			                            fontSize : '15',
			                            fontWeight : 'bold'
			                        }
			                    }
			                }
			            },
			            data:[
			                {value:335, name:'测试基站1'},
			                {value:310, name:'测试基站2'},
			                {value:234, name:'测试基站3'},
			                {value:135, name:'测试基站4'},
			                {value:154, name:'测试基站5'}
			            ]
			        }
			    ]
			};
		chart.setOption(option);

	});
	/*window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};*/
	
}
xh.groupTop5=function(){
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#group-top5").width(300);
		$("#group-top5").height(150);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	
	
	require([ 'echarts', 'echarts/chart/funnel' ], function(ec) {
		chart = ec.init(document.getElementById('group-top5'));
		
		var option = {
			    
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c}"
			    },
			   
			    legend: {
			    	 orient : 'vertical',
				        x : 'left',
				        textStyle:{
				        	color:'#fff',
				        },
			        data : ['测试基站1','测试基站2','测试基站3','测试基站4','测试基站5']
			    },
			    calculable : false,
			    series : [
			        {
			            name:'漏斗图',
			            type:'funnel',
			            width: '20%',
			            height:'80%',
			            x:'40%',
			            y:10,
			            data:[
			                {value:60, name:'测试基站1'},
			                {value:40, name:'测试基站2'},
			                {value:20, name:'测试基站3'},
			                {value:80, name:'测试基站4'},
			                {value:100, name:'测试基站5'}
			            ]
			        }
			    ]
			};
		chart.setOption(option);

	});
	/*window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};*/
	
}
xh.userTop5=function(){
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#user-top5").width(300);
		$("#user-top5").height(130);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	
	
	require([ 'echarts', 'echarts/chart/funnel' ], function(ec) {
		chart = ec.init(document.getElementById('user-top5'));
		
		var option = {
			    
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c}"
			    },
			   
			    legend: {
			    	 orient : 'vertical',
				        x : 'left',
				        textStyle:{
				        	color:'#fff',
				        },
			        data : ['测试基站1','测试基站2','测试基站3','测试基站4','测试基站5']
			    },
			    calculable : false,
			    series : [
			        {
			            name:'漏斗图',
			            type:'funnel',
			            width: '20%',
			            height:'80%',
			            x:'40%',
			            y:10,
			            data:[
			                {value:60, name:'测试基站1'},
			                {value:40, name:'测试基站2'},
			                {value:20, name:'测试基站3'},
			                {value:80, name:'测试基站4'},
			                {value:100, name:'测试基站5'}
			            ]
			        }
			    ]
			};
		chart.setOption(option);

	});
	/*window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};*/
	
}
xh.callInfo=function(){
	// 设置容器宽高
	var resizeBarContainer = function() {
		$("#call-bar").width(500);
		$("#call-bar").height(200);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	
	
	require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
		chart = ec.init(document.getElementById('call-bar'));
		
		var option = {
			    /*title : {
			       text: '今日全网呼叫实时统计',
			       textStyle:{
			    	   color:'#fff'
			       }
			    },*/
			    tooltip : {
			        trigger: 'axis'
			    },
			    /*legend: {
			        data:['Tera系统[基站，交换中心，网管，调度台]告警统计']
			    },*/
			    
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#fff'
                            }
                        },
                        splitLine:{show: false},//去除网格线
                        splitArea : {show : false},//去除网格区域
			            data : ["00","01","02","03","04","05",
			                    "06","07","08","09","10","11",
			                    "12","13","14","15","16","17",
			                    "18","19","20","21","22","23"]
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            splitLine:{show: false},//去除网格线
			            splitArea : {show : false},//去除网格区域
			            axisLabel : {
                            formatter: '{value}',
                            textStyle: {
                                color: '#fff'
                            }
                        }
			            
			        }
			    ],
			    series : [
			        {
			            name:'呼叫信息',
			            type:'bar',
			            barWidth: 5,//固定柱子宽度
			            data:[2,5,7,8,9,10,11,12,13,14,15,14,45,34,45,46,56,56,56,3,2,34,55,54],
			            itemStyle:{
			            	normal:{
			            		color:'#9ACD32',
			            		cursor:'pointer'
			            	}},
			            
			            markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            }
			        }
			    ]
			};
		
		chart.setOption(option);

	});
	/*window.onresize = function() {
		// 重置容器高宽
		chart.resize();
	};*/
	
}