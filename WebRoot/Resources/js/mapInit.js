var app = angular.module("app", []);
app.controller("map", function($scope, $http) {
	$http.get("bs/map/area").success(
			function(response) {
				$scope.data = response.items;
			});
	$http.get("bs/map/level").success(
			function(response) {
				$scope.levelData = response.items;
			});
	/*top5话务量*/
	$http.get("bs/map/top5Calllist").success(
			function(response) {
				$scope.top5Calllist = response.items;
			});
	/*top5排队数*/
	$http.get("bs/map/top5Channel").success(
			function(response) {
				$scope.top5Channel = response.items;
			});
	/* 级别选择 */
	var level={
			"0":{"lat":"30.664979585525476","lng":"104.05377994605959","zoom":"7"},
			"1":{"lat":"30.6670418358257","lng":"104.07508986582853","zoom":"6"},
			"2":{"lat":"30.819648358042055","lng":"104.08952561793008","zoom":"6"},
			"3":{"lat":"30.680790171160506","lng":"103.91492175917804","zoom":"5"},
	}
	$scope.levelChoose=function(params){
		$http.get("bs/map/bsByLevel?level="+params).success(
				function(response) {
					var tempData = response.items;					
					var point = new esri.geometry.Point(level[params].lng*1, level[params].lat*1);
					myMap.centerAndZoom(point,level[params].zoom*1);
					layerCreate(tempData);
					option.series[0].markPoint.data=baseMark(tempData);
					option.series[1].markPoint.data=flashMark(tempData);
					overlay.setOption(option);
				});
	} 
	/* 区域选择 */
	var area={
			"123":{"lat":"30.646419332823488","lng":"104.29987514855263","zoom":"7"},
			"双流":{"lat":"30.52955848247763","lng":"103.93520055379688","zoom":"7"},
			"都江堰":{"lat":"31.106988566539517","lng":"103.68807422615372","zoom":"6"},
			"天府新区":{"lat":"30.406854589614476","lng":"104.08540111732965","zoom":"7"},
			"温江":{"lat":"30.665323293908852","lng":"103.83758737291974","zoom":"8"},
			"龙泉驿区":{"lat":"30.601049826218624","lng":"104.25588047548125","zoom":"7"},
			"金堂":{"lat":"30.73097159513254","lng":"104.59133985765051","zoom":"7"},
			"青白江":{"lat":"30.716535843031004","lng":"104.38373999409495","zoom":"7"},
			"新都":{"lat":"30.862955614346696","lng":"104.07783953289548","zoom":"7"},
			"彭州":{"lat":"31.13998457134305","lng":"103.89636150647604","zoom":"7"},
			"郫县":{"lat":"30.843020528111225","lng":"103.87092708610665","zoom":"7"},
			"崇州":{"lat":"30.644357082523264","lng":"103.64957888721625","zoom":"7"},
			"大邑":{"lat":"30.60998624418625","lng":"103.36705059608597","zoom":"7"},
			"邛崃":{"lat":"30.39070029559608","lng":"103.38079893142077","zoom":"7"},
			"蒲江":{"lat":"30.22984477217884","lng":"103.5017842823671","zoom":"7"},
			"新津":{"lat":"30.433320135133982","lng":"103.80905957710003","zoom":"7"},
			"主城区":{"lat":"30.662917335225256","lng":"104.06340378079395","zoom":"7"},
			"都江堰市":{"lat":"31.042371390465927","lng":"103.62826896744731","zoom":"7"},
			"青白江城区":{"lat":"30.78527751970504","lng":"104.3390579042568","zoom":"7"},
			"主城区-高新南":{"lat":"30.406510881231107","lng":"104.09227528499704","zoom":"7"}
	};
	$scope.areaChoose=function(params){
		if ($(".areaChoose input[value="+params+"]").prop("checked") == true) {
			var t=[];
			$(".areaChoose input:checked").each(function(i){
				t.push($(this).val());
			});
			$http.get("bs/map/bsByArea?zone="+t).success(
					function(response) {
						var tempData = response.items;					
						var point = new esri.geometry.Point(area[params].lng*1, area[params].lat*1);
						myMap.centerAndZoom(point,area[params].zoom*1);
						layerCreate(tempData);	
						option.series[0].markPoint.data=baseMark(tempData);
						option.series[1].markPoint.data=flashMark(tempData);
						overlay.setOption(option);
					});
		} else {
			var t=[];
			$(".areaChoose input:checked").each(function(i){
				t.push($(this).val());
			});
			$http.get("bs/map/bsByArea?zone="+t).success(
					function(response) {
						var tempData = response.items;		
						var point = new esri.geometry.Point(area[params].lng*1, area[params].lat*1);
						myMap.centerAndZoom(point,area[params].zoom*1);
						layerCreate(tempData);	
						option.series[0].markPoint.data=baseMark(tempData);
						option.series[1].markPoint.data=flashMark(tempData);
						overlay.setOption(option);
					});
		}
		
	} 
	
	$scope.test=function(){
		var t = $scope.top5Calllist;
		console.log(t);
		t[0]="";
		$scope.top5Calllist=t;
		/*var temp;
		temp=t[0];
		t[0]=t[1];
		t[1]=t[2];
		t[2]=t[3];
		t[3]=t[4];
		t[4]=temp;
		$scope.top5Calllist = t;*/
	}
	
	/*话务量点击定位*/
	$scope.calllistChoose=function(x){
		$http.get("bs/map/data").success(
				function(response) {
					var tempData = response.items;				
					var point = new esri.geometry.Point(x.lng*1, x.lat*1);
					myMap.centerAndZoom(point,8);
					layerCreate(tempData);
					var temp=[];
					var y = {
							name : x.name,
							value : x.bsId
						};
					temp.push(y);
					option.series[0].markPoint.data=baseMark(tempData);
					option.series[1].markPoint.data=temp;
					overlay.setOption(option);
				});	
	}
	/*排队数点击定位*/
	$scope.channel=function(x){
		$http.get("bs/map/data").success(
				function(response) {
					var tempData = response.items;				
					var point = new esri.geometry.Point(x.lng*1, x.lat*1);
					myMap.centerAndZoom(point,8);
					layerCreate(tempData);
					var temp=[];
					var y = {
							name : x.name,
							value : x.bsId
						};
					temp.push(y);
					option.series[0].markPoint.data=baseMark(tempData);
					option.series[1].markPoint.data=temp;
					overlay.setOption(option);
				});	
	}
});
/**
 * 本示例使用arcgis api for javascript 技术显示地图。 相关官方API文档地址为:
 * https://developers.arcgis.com/javascript/jsapi/ 所有示例代码访问地址为：
 * https://developers.arcgis.com/javascript/jssamples/
 */
dojo.require("esri.map");
var myMap;
var scalebar;
var draw;
var gLayer;
var gLayermiddle;
var gLayerbig;
var levelLayer,areaLayer
var roadtest;
function floor(data) {
	var options = {
		logo : false
	};
	esri.symbols = esri.symbol;
	myMap = new esri.Map("mapDiv", options);// 在mapDiv中创建map地图对象
	// 创建底图图层对象,http://10.190.230.165/arcgisditu/rest/services/NEWMAP/MapServer为政务外网底图服务地址,
	var myTiledMapServiceLayer = new
	// esri.layers.ArcGISDynamicMapServiceLayer("动态服务地址");//动态服务
	esri.layers.ArcGISTiledMapServiceLayer(
			"http://125.70.9.194:801/services/MapServer/map2d");// 切片服务
	myMap.addLayer(myTiledMapServiceLayer);// 将底图图层对象添加到地图中
	gLayer = new esri.layers.GraphicsLayer({id:"小图标"}); // 创建图形显示图层，图形显示图层专门用于在地图上显示点，线，面图形数据
	gLayermiddle = new esri.layers.GraphicsLayer({id:"中图标"});// 创建中图标图层
	gLayerbig = new esri.layers.GraphicsLayer({id:"大图标"});// 创建大图形显示图层
	levelLayer = new esri.layers.GraphicsLayer({id:"基站级别"});
	areaLayer = new esri.layers.GraphicsLayer({id:"基站区域"});
	roadtest = new esri.layers.GraphicsLayer({id:"路测数据"});
	var point = new esri.geometry.Point(104.06340378079395, 30.66016766815829);
	myMap.centerAndZoom(point, 6);// 地图首次加载显示的位置和放大级别
	myMap.addLayer(gLayer);// 将图形显示图层添加到地图中
	myMap.setInfoWindowOnClick(true);

	// 创建点的显示样式对象
	/*
	 * var pSymbol = new esri.symbols.SimpleMarkerSymbol(); pSymbol.style =
	 * esri.symbols.SimpleMarkerSymbol.STYLE_CIRCLE; //设置点的类型为圆形
	 * pSymbol.setSize(12); //设置点的大小为12像素 pSymbol.setColor(new
	 * dojo.Color("#FFFFCC"));
	 */
	//创建图层
	layerCreate(data);
	//路测数据创建图层
	$.ajax({
		type : "GET",
		url : "bs/map/roadtest",
		dataType : "json",
		success : function(dataMap) {
			var data = dataMap.items;
			roadtestCreate(data);
		}
	});
	// 添加控件
	// 比例尺
	require([ "esri/dijit/Scalebar" ], function(Scalebar) {
		scalebar = new esri.dijit.Scalebar({
			map : myMap,
			// "dual" displays both miles and kilmometers
			// "english" is the default, which displays miles
			// use "metric" for kilometers
			scalebarUnit : "metric"
		});
	});
	
	// 图层组
	require([ "esri/dijit/LayerList" ], function(LayerList) {
		var layerList = new esri.dijit.LayerList({
			map: myMap,
		    showLegend: false,
		    showSubLayers: false,
		    showOpacitySlider: false,
		    layers: [roadtest]
		},"test");
		layerList.startup();
	});
	//区域圈选
	var map, toolbar, symbol, geomTask;
    require([
      "esri/map", 
      "esri/toolbars/draw",
      "esri/graphic",
      "esri/symbols/SimpleMarkerSymbol",
      "esri/symbols/SimpleLineSymbol",
      "esri/symbols/SimpleFillSymbol",
      "dojo/parser", "dijit/registry",
      "dijit/layout/BorderContainer", "dijit/layout/ContentPane", 
      "dijit/form/Button", "dijit/WidgetSet", "dojo/domReady!"
    ], function(
      Map, Draw, Graphic,
      SimpleMarkerSymbol, SimpleLineSymbol, SimpleFillSymbol,
      parser, registry
    ) {
      map = myMap;    
      map.on("load", createToolbar);
      // loop through all dijits, connect onClick event
      // listeners for buttons to activate drawing tools
      registry.forEach(function(d) {
        // d is a reference to a dijit
        // could be a layout container or a button
        if ( d.declaredClass === "dijit.form.Button" ) {
          d.on("click", activateTool);
        }
      });

      function activateTool() {
        var tool = this.label.toUpperCase().replace(/ /g, "_");
        toolbar = new Draw(map);
        toolbar.activate(Draw[tool]);
        map.hideZoomSlider();
      }

      function createToolbar(themap) {
        toolbar = new Draw(map);
        toolbar.on("draw-end", addToMap);
      }

      function addToMap(evt) {
        var symbol;
        toolbar.deactivate();
        map.showZoomSlider();
        var graphic = new Graphic(evt.geometry, symbol);
        map.graphics.add(graphic);
      }
    });
	

	// 鼠标获取经纬度

myMap.on("mouse-down", function(e){ console.log(e.mapPoint.x ,
 e.mapPoint.y); });

}
//路测图层创建
function roadtestCreate(data){
	require(["esri/Color"], function(Color) {
		var i;
		for(i=0;i<data.length;i++){
			var temp=[];
			if(data[i].db>=-103 && data[i].db<-100){
				temp=[255,0,0]//红色
			}else if(data[i].db>=-100 && data[i].db<-95){
				temp=[255,255,0]//黄色
			}else if(data[i].db>=-95 && data[i].db<-85){
				temp=[0,0,255]//蓝色
			}else if(data[i].db>=-85 && data[i].db<-39){
				temp=[0,255,0]//绿色
			}
			var symbol = new esri.symbol.SimpleMarkerSymbol({
				"color": temp,
				  "size": 8,
				  "type": "simplemarkersymbol"
			});
			var pt = new esri.geometry.Point(data[i].lng*1, data[i].lat*1);// 创建点对象
			var attr = {
				"db" : data[i].db,
				"lng" : data[i].lng,
				"lat" : data[i].lat,
				"nPositionArea" : data[i].nPositionArea,
				"ndb" : data[i].ndb,	
				"positionArea" : data[i].positionArea
			};// 设置相关的属性信息对象
			var infoTemplate = new esri.InfoTemplate("弹出窗口的标题",
					"纬度属性: ${lat} <br/>经度属性: ${lng} <br/>场强:${db}");// 创建弹出窗口内容显示模板
			var graphic = new esri.Graphic(pt, symbol, attr, infoTemplate);// 创建图形对象
			roadtest.add(graphic);// 将图形对象添加到图形显示图层
		}
	});
	
}
//基站图标创建
function layerCreate(data){
	// 小图标图层
	gLayer.clear();
	var i;
	for (i = 0; i < data.length; i++) {
		var temp = 0;
		// 判断基站是连接还是断开
		if (data[i].status == 0) {
			temp = "bluesky/break_small.png";
		} else if (data[i].status == 1) {
			// 判断基站告警的级别
/*			if (typeof (data[i].alarmLevel) === "undefined") {
				temp = "bluesky/contact_small.png";
			} else if (data[i].alarmLevel == 1) {
				temp = "bluesky/normal_small.gif";
			} else if (data[i].alarmLevel == 2) {
				temp = "bluesky/warning_small.gif";
			} else if (data[i].alarmLevel == 3) {
				temp = "bluesky/urgent_small.gif";
			}*/
			temp = "bluesky/contact_small.png";
		} else if (data[i].status == 2) {
			temp = "bluesky/unuse_small.png";
		}

		var symbol = new esri.symbol.PictureMarkerSymbol(temp, parseInt(32), parseInt(32));
		var pt = new esri.geometry.Point(data[i].lng*1, data[i].lat*1);// 创建点对象
		var attr = {
			"Xcoord" : data[i].lng,
			"Ycoord" : data[i].lat,
			"Plant" : data[i].name
		};// 设置相关的属性信息对象
		var infoTemplate = new esri.InfoTemplate("弹出窗口的标题",
				"纬度属性: ${Ycoord} <br/>经度属性: ${Xcoord} <br/>基站名称................:${Plant}");// 创建弹出窗口内容显示模板
		var graphic = new esri.Graphic(pt, symbol, attr, infoTemplate);// 创建图形对象
		gLayer.add(graphic);// 将图形对象添加到图形显示图层
	}

	// 中图标图层
	gLayermiddle.clear();
	var j;
	for (j = 0; j < data.length; j++) {
		var temp = 0;
		// 判断基站是连接还是断开
		if (data[j].status == 0) {
			temp = "bluesky/break_middle.png";
		} else if (data[j].status == 1) {
			// 判断基站告警的级别
			/*if (typeof (data[j].alarmLevel) === "undefined") {
				temp = "bluesky/contact_middle.png";
			} else if (data[j].alarmLevel == 1) {
				temp = "bluesky/normal_middle.gif";
			} else if (data[j].alarmLevel == 2) {
				temp = "bluesky/warning_middle.gif";
			} else if (data[j].alarmLevel == 3) {
				temp = "bluesky/urgent_middle.gif";
			}*/
			temp = "bluesky/contact_middle.png";
		} else if (data[j].status == 2) {
			temp = "bluesky/unuse_middle.png";
		}

		var symbol = new esri.symbol.PictureMarkerSymbol(temp, parseInt(48), parseInt(48));
		var pt = new esri.geometry.Point(data[j].lng*1, data[j].lat*1);// 创建点对象
		var attr = {
			"Xcoord" : data[j].lng,
			"Ycoord" : data[j].lat,
			"Plant" : data[j].name
		};// 设置相关的属性信息对象
		var infoTemplate = new esri.InfoTemplate("弹出窗口的标题",
				"纬度属性: ${Ycoord} <br/>经度属性: ${Xcoord} <br/>基站名称................:${Plant}");// 创建弹出窗口内容显示模板
		var graphic = new esri.Graphic(pt, symbol, attr, infoTemplate);// 创建图形对象
		gLayermiddle.add(graphic);
	}

	// 大图标图层
	gLayerbig.clear();
	var x;
	for (x = 0; x < data.length; x++) {
		var temp = 0;
		// 判断基站是连接还是断开
		if (data[x].status == 0) {
			temp = "bluesky/break_big.png";
		} else if (data[x].status == 1) {
			// 判断基站告警的级别
			/*if (typeof (data[x].alarmLevel) === "undefined") {
				temp = "bluesky/contact_big.png";
			} else if (data[x].alarmLevel == 1) {
				temp = "bluesky/normal_big.gif";
			} else if (data[x].alarmLevel == 2) {
				temp = "bluesky/warning_big.gif";
			} else if (data[x].alarmLevel == 3) {
				temp = "bluesky/urgent_big.gif";
			}*/
			temp = "bluesky/contact_big.png";
		} else if (data[x].status == 2) {
			temp = "bluesky/unuse_big.png";
		}

		var symbol = new esri.symbol.PictureMarkerSymbol(temp, parseInt(64), parseInt(64));
		var pt = new esri.geometry.Point(data[x].lng*1, data[x].lat*1);// 创建点对象
		var attr = {
			"Xcoord" : data[x].lng,
			"Ycoord" : data[x].lat,
			"Plant" : data[x].name
		};// 设置相关的属性信息对象
		var infoTemplate = new esri.InfoTemplate("弹出窗口的标题",
				"纬度属性: ${Ycoord} <br/>经度属性: ${Xcoord} <br/>基站名称................:${Plant}");// 创建弹出窗口内容显示模板
		var graphic = new esri.Graphic(pt, symbol, attr, infoTemplate);// 创建图形对象
		gLayerbig.add(graphic);
	}

}

var overlay,option
function init(data,markData) {
	require([ "esri/map", "src/EchartsLayer", "dojo/domReady!" ], function(Map,
			EchartsLayer) {
		floor(data);
		// 处理data数据
		var i;
		var obj = {};
		for (i = 0; i < data.length; i++) {
			var t = [];
			t[0] = data[i].lng;
			t[1] = data[i].lat;
			var key = data[i].name;
			obj[key] = t;
		}
		// 闪烁提示基站显示数据	
		var objTemp = [];
		if(markData!=null){
			var j;
			for (j = 0; j < markData.length; j++) {
				var x = {
					name : markData[j].name,
					value : markData[j].bsId
				};
				objTemp.push(x);
			}
		}
		
		// 所有基站显示数据
		var k;
		var objAll = [];
		for (k = 0; k < data.length; k++) {
			var y = {
				name : data[k].name,
				value : data[k].bsId
			};
			objAll.push(y);
		}
		/*console.log("data->"+objTemp[0].name);*/
		overlay = new EchartsLayer(myMap, echarts);
		var chartsContainer = overlay.getEchartsContainer();
		var myChart = overlay.initECharts(chartsContainer);
		window.onresize = myChart.onresize;
		// 为echarts绑定事件
		myChart.on('click', function(params) {
			/* 基站图标设置模态框并获取显示数据 */
			$('#myModal').modal();
			$.ajax({
				type : "GET",
				url : "bs/map/dataById?bsId=" + params.value,
				dataType : "json",
				success : function(dataById) {
					var dataTemp = dataById.items;
					var data = dataTemp[0];
					$('#bsId').val(data.bsId);
					$('#bsName').val(data.name);
					$('#lat').val(data.lat);
					$('#lng').val(data.lng);
					$('#address').val(data.address);
					$('#ip').val(data.ip);
					if (data.contact == '') {
						$('#contact').val("暂无相关信息");
					} else if (data.tel == 0) {
						$('#contact').val(data.contact + " 暂无电话");
					} else {
						$('#contact').val(data.contact + " " + data.tel);
					}
					$('#chnumber').val(data.chnumber);
					$('#gpsLineNum').val(data.gpsLineNum);
					$('#power').val(data.power);
					$('#carrier').val(data.carrier);
					$('#height').val(data.height);
					$('#lineHeight').val(data.lineHeight);
					if (data.status == '') {
						$('#status').val("暂无相关信息");
					} else if (data.status == 0) {
						$('#status').val("断开");
					} else if (data.status == 1) {
						$('#status').val("正常");
					} else if (data.status == 2) {
						$('#status').val("未启用");
					}
					// 动环数据展示
					var move = dataById.moveController;

				}
			});
		});
		//地图加载时执行
		option = {
				color : [ 'gold', 'aqua', 'lime' ],
				tooltip : {
					trigger : 'item',
					formatter : '{b}'
				},
				/*dataRange : {
					min : 0,
			        max : 500,
			        calculable : true,
			        color: ['maroon','purple','red','orange','yellow','lightgreen']
			        color:['red','red']
				},*/
				series : [ {
					name : '四川',
					type : 'map',
					mapType : 'none',			
					data : [],
					geoCoord : obj,
					markPoint : {
						symbol : 'image://',
						symbolSize: 16,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
		                itemStyle: {
		                    normal: {
		                        borderColor: '#87cefa',
		                        borderWidth: 1,            // 标注边线线宽，单位px，默认为1
		                        label: {
		                            show: false
		                        }
		                    },
		                    emphasis: {
		                        borderColor: '#1e90ff',
		                        borderWidth: 5,
		                        label: {
		                            show: false
		                        }
		                    }
		                },
		                data:objAll
					}
				}, {
					name : '基站',
					type : 'map',
					mapType : 'none',
					data : [],
					markPoint : {
						symbol : 'emptyCircle',
						
						symbolSize : function(v) {
							return 16
						},
						effect : {
							show : true,
							shadowBlur : 0
						},
						itemStyle : {
							normal : {
								label : {
									show : false
								}
							},
							emphasis : {
								label : {
									position : 'top'
								}
							}
						},
						data : objTemp
					}
				} ]
			};
		myMap.on('load', function() {
			overlay.setOption(option);
		});
		
		myMap.on('zoom-end', function() {
			if ($("#bsInfo").prop("checked") == true){
				if (myMap.getZoom() >= 10) {
					myMap.removeLayer(gLayer);
					myMap.removeLayer(gLayermiddle);
					myMap.addLayer(gLayerbig);
					option.series[0].markPoint.symbolSize=32;
					overlay.setOption(option);
				} else if (myMap.getZoom() >= 7 && myMap.getZoom() <= 9) {
					myMap.removeLayer(gLayer);
					myMap.removeLayer(gLayerbig);
					myMap.addLayer(gLayermiddle);
					option.series[0].markPoint.symbolSize=24;
					overlay.setOption(option);
				} else {
					myMap.removeLayer(gLayermiddle);
					myMap.removeLayer(gLayerbig);
					myMap.addLayer(gLayer);
					option.series[0].markPoint.symbolSize=16;
					overlay.setOption(option);
				}
			}
			
		});
		
		//左侧图层选择事件
		$(function() {
			$("#bsInfo").click(function() {
				if ($(this).prop("checked") == true) {
					if (myMap.getZoom() >= 10) {
						myMap.removeLayer(gLayer);
						myMap.removeLayer(gLayermiddle);
						myMap.addLayer(gLayerbig);
						option.series[0].markPoint.symbolSize=32;
						option.series[0].markPoint.data=objAll;
						option.series[1].markPoint.data=objTemp;
						overlay.setOption(option);
					} else if (myMap.getZoom() >= 7 && myMap.getZoom() <= 9) {
						myMap.removeLayer(gLayer);
						myMap.removeLayer(gLayerbig);
						myMap.addLayer(gLayermiddle);
						option.series[0].markPoint.symbolSize=24;
						option.series[0].markPoint.data=objAll;
						option.series[1].markPoint.data=objTemp;
						overlay.setOption(option);
					} else {
						myMap.removeLayer(gLayermiddle);
						myMap.removeLayer(gLayerbig);
						myMap.addLayer(gLayer);
						option.series[0].markPoint.symbolSize=16;
						option.series[0].markPoint.data=objAll;
						option.series[1].markPoint.data=objTemp;
						overlay.setOption(option);
					}
				} else {
					var point = new esri.geometry.Point(104.06340378079395, 30.66016766815829);
					myMap.centerAndZoom(point, 6);
					myMap.removeLayer(gLayer);
					myMap.removeLayer(gLayermiddle);
					myMap.removeLayer(gLayerbig);
					option.series[0].markPoint.data=[];
					option.series[1].markPoint.data=[];
					overlay.setOption(option);
				}
			});
			
			$("#roadtestInfo").click(function() {
				if ($(this).prop("checked") == true) {
					myMap.addLayer(roadtest);
					var point = new esri.geometry.Point(104.53166109531142*1, 30.36990593840218*1);
					myMap.centerAndZoom(point,10*1);
					 
				} else {
					myMap.removeLayer(roadtest);
				}
			});
			
		});
		
	});
}

function getData() {
	// 使用ajax获取后台所有基站数据
	$.ajax({
		type : "GET",
		url : "bs/map/data",
		dataType : "json",
		success : function(dataMap) {
			var data = dataMap.items;
			//减少部分数据
			/*var dataTemp=[];
			var i;
			for(i=0;i<data.length;i++){
				if(i%2==0){
					dataTemp.push(data[i]);
				}
			}*/
			var markData=[];
			init(data,markData);
		}
	});
}

dojo.addOnLoad(getData);// 页面加载完毕后自动调用getData方法

/* 拖拽模态框 */
var mouseStartPoint = {
	"left" : 0,
	"top" : 0
};
var mouseEndPoint = {
	"left" : 0,
	"top" : 0
};
var mouseDragDown = false;
var oldP = {
	"left" : 0,
	"top" : 0
};
var moveTartet;
$(document).ready(
		function() {
			$(document).on("mousedown", ".modal-header", function(e) {
				if ($(e.target).hasClass("close"))// 点关闭按钮不能移动对话框
					return;
				mouseDragDown = true;
				moveTartet = $(this).parent();
				mouseStartPoint = {
					"left" : e.clientX,
					"top" : e.clientY
				};
				oldP = moveTartet.offset();
			});
			$(document).on("mouseup", function(e) {
				mouseDragDown = false;
				moveTartet = undefined;
				mouseStartPoint = {
					"left" : 0,
					"top" : 0
				};
				oldP = {
					"left" : 0,
					"top" : 0
				};
			});
			$(document).on(
					"mousemove",
					function(e) {
						if (!mouseDragDown || moveTartet == undefined)
							return;
						var mousX = e.clientX;
						var mousY = e.clientY;
						if (mousX < 0)
							mousX = 0;
						if (mousY < 0)
							mousY = 25;
						mouseEndPoint = {
							"left" : mousX,
							"top" : mousY
						};
						var width = moveTartet.width();
						var height = moveTartet.height();
						mouseEndPoint.left = mouseEndPoint.left
								- (mouseStartPoint.left - oldP.left);// 移动修正，更平滑
						mouseEndPoint.top = mouseEndPoint.top
								- (mouseStartPoint.top - oldP.top);
						moveTartet.offset(mouseEndPoint);
					});
		});

//基本效果数据处理调用的函数
function baseMark(data){
	var k;
	var objAll = [];
	for (k = 0; k < data.length; k++) {
		var y = {
			name : data[k].name,
			value : data[k].bsId
		};
		objAll.push(y);
	}
	return objAll;
}

//闪烁效果需求数据处理调用的函数
function flashMark(markData){
	// 闪烁提示基站显示数据	
	var objTemp = [];
	if(markData!=null){
		var j;
		for (j = 0; j < markData.length; j++) {
			var x = {
				name : markData[j].name,
				value : markData[j].bsId
			};
			objTemp.push(x);
		}
	}
	return objTemp;
}

//滚动效果
$('.info_right').mouseover(function(){
	clearInterval(temptimer);//停止计时器
});

$('.info_right').mouseout(function(){
	temptimer = setInterval("tableInterval()", 2000);//每隔2秒执行一次change函数，相当于table在向上滚动一样
});

var temptimer;

//先在table的最后增加一行，然后再把第一行中的数据填充到新增加的行中，最后再删除table的第一行
function change(table) {
	var row = table.insertRow(table.rows.length);//在table的最后增加一行,table.rows.length是表格的总行数
	for (j = 0; j < table.rows[0].cells.length; j++) {//循环第一行的所有单元格的数据，让其加到最后新加的一行数据中（注意下标是从0开始的）
		var cell = row.insertCell(j);//给新插入的行中添加单元格
		cell.height = "19px";//一个单元格的高度，跟css样式中的line-height高度一样
		cell.width = "70%";
		cell.innerHTML = table.rows[0].cells[j].innerHTML;//设置新单元格的内容，这个根据需要，自己设置
	}
	table.deleteRow(0);//删除table的第一行 
};
function tableInterval() {
	/* var table0 = document.getElementById("table0");//获得表格
	var table1 = document.getElementById("table1");
	var table2 = document.getElementById("table2");
	change(table0);//执行表格change函数，删除第一行，最后增加一行，类似行滚动
	change(table1);
	change(table2); */
	var appElement = document.querySelector('[ng-controller=map]');
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.test();
	
};
temptimer=setInterval("tableInterval()", 2000);//每隔2秒执行一次change函数，相当于table在向上滚动一样
