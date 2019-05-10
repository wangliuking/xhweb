require.config({
	paths : {
		echarts : '../lib/echarts'
	}
});
bui.ready(function() {

    var pageview = {},
        uiTab,wc,store;
    
    pageview.data=function(){
   	
    	 bui.ajax({
 	        url: xh.getUrl()+"webapp/data",
 	        data: {}
 	      }).then(function(res){
 	    	console.log(JSON.stringify(res))
 	    	store=bui.store({
 	            scope:'page',
 	            data:{
 	            	useOnlineCount:res.useOnlineCount,
 	            	userCount:res.userCount,
 	            	bs_gd_count:res.bs_gd_count,
 	            	bs_vertical_count:res.bs_vertical_count,
 	            	room:res.room,
 	            	bx:res.bx,
 	            	embus:res.embus,
 	            	vpn:res.vpn,
 	            	access:res.access,
 	            	dispatch:res.dispatch,
 	            	subway:res.subway
 	            }
 	    	})
 	    },function(res,status){
 	        console.log(status);
 	    })
    }

    pageview.tab = function () {
        var uiTab = bui.tab({
            id: "#uiTab",
            scroll: true,
            swipe: false,
            animate: true,
            // 1: 声明是动态加载的tab
            autoload: true,
        });
    }
    

    pageview.init = function () {

        // 绑定事件
        this.tab();
        this.data();
        
        callbar();
    }

    // 初始化
    pageview.init();

    // 输出模块
    return pageview;
});
function callbar(){
	// 设置容器宽高
	var height=document.documentElement.clientHeight;
	/* var width=document.documentElement.clientWidth; */
	var resizeBarContainer = function() {
		// $("#call-bar").width((width/12)*4-40);
		$("#call-bar").height(height-270);
	};
	resizeBarContainer();

	// 基于准备好的dom，初始化echarts实例
	var chart = null;
	if (chart != null) {
		chart.clear();
		chart.dispose();
	}
	
	
	
	require([ 'echarts', 'echarts/chart/bar','echarts/chart/line' ], function(ec) {
		chart = ec.init(document.getElementById('call-bar'));
		
		var option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			    	data:['呼叫时长','呼叫次数'],
			    	textStyle:{
			    		color:'#000'
			    	}
			    },
			    
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            axisLabel: {
                            show: true,
                            textStyle: {
                                color: '#000'
                            }
                        },
                        splitLine:{show: false},// 去除网格线
                        splitArea : {show : false},// 去除网格区域
			            data : ["00","01","02","03","04","05",
			                    "06","07","08","09","10","11",
			                    "12","13","14","15","16","17",
			                    "18","19","20","21","22","23"]
			        }
			    ],
			    yAxis : [ {
                    type: 'value',
                    name: '呼叫时长',
                    min: 0,
                    
                    position: 'left',
                    axisLabel: {
                        formatter: '{value}(分钟)',
                        textStyle:{
                        	color:'#000'
                        }
                    }
                    },{
	                    type: 'value',
	                    name: '呼叫次数',
	                    min: 0,
	                  
	                    position: 'right',
	                    axisLabel: {
	                        formatter: '{value}(次)',
	                        textStyle:{
	                        	color:'#000'
	                        }
	                    }
	                }],
			    series : [{
		            name:'呼叫时长',
		            type:'bar',
		            data:[],
		            itemStyle:{normal:{color:'#436EEE'}}
		        },{
		            name:'呼叫次数',
		            type:'line',
		            yAxisIndex:1,
		            itemStyle:{normal:{color:'#FF7F00'}},
		            data:[]
		        }
			    ]
			};
		
		$.ajax({
			url : xh.getUrl()+'call/chart_call_hour_now',
			type : 'POST',
			dataType : "json",
			async : false,
			timeout:2000,
			data:{
				bsId:0,
				time:xh.getOneDay(),
				type:'hour'
			},
			success : function(response) {
				var data = response.time;
				var num = response.num;
				var xData=[],yData=[],yData2=[];
				
				for(var i=0;i<data.length;i++){
					xData.push(data[i].label);
					yData.push(data[i].time);
					yData2.push(num[i].num);
				}
				option.series[0].data = yData;
				option.series[1].data = yData2;
				option.xAxis[0].data = xData;
				chart.setOption(option);
				xh.maskHide();

			},
			failure : function(response) {
				xh.maskHide();
			}
		});
		
		

	});
		
	/*
	 * window.onresize = function() { // 重置容器高宽 chart.resize(); };
	 */
	
}
