var app = angular.module("app", []);
app.controller("map", function($scope, $http) {
	
});
/**
 * 本示例使用arcgis api for javascript 技术显示地图。 相关官方API文档地址为:
 * https://developers.arcgis.com/javascript/jsapi/ 所有示例代码访问地址为：
 * https://developers.arcgis.com/javascript/jssamples/
 */
// dojo.require("esri.map");
var myMap;
var draw;
var gLayer;
var gLayermiddle;
var gLayerbig;
function floor(data) {
	var options = {
		logo : false
	};
	// esri.symbols = esri.symbol;
	myMap = new esri.Map("mapDiv", options);// 在mapDiv中创建map地图对象
	// 创建底图图层对象,http://10.190.230.165/arcgisditu/rest/services/NEWMAP/MapServer为政务外网底图服务地址,
	var myTiledMapServiceLayer = new
	// esri.layers.ArcGISDynamicMapServiceLayer("动态服务地址");//动态服务
	esri.layers.ArcGISTiledMapServiceLayer(
			"http://125.70.9.194:6080/common/rest/services/NEWMAP/MapServer");// 切片服务
	myMap.addLayer(myTiledMapServiceLayer);// 将底图图层对象添加到地图中
	gLayer = new esri.layers.GraphicsLayer({id:"小图标"}); // 创建图形显示图层，图形显示图层专门用于在地图上显示点，线，面图形数据
	gLayermiddle = new esri.layers.GraphicsLayer({id:"中图标"});// 创建中图标图层
	gLayerbig = new esri.layers.GraphicsLayer({id:"大图标"});// 创建大图形显示图层
	var point = new esri.geometry.Point(104.06340378079395, 30.66016766815829);
	myMap.centerAndZoom(point, 6);// 地图首次加载显示的位置和放大级别
	myMap.addLayer(gLayer);// 将图形显示图层添加到地图中
	myMap.addLayer(gLayermiddle);
	gLayermiddle.hide();
	myMap.addLayer(gLayerbig);
	gLayerbig.hide();
	myMap.setInfoWindowOnClick(true);
	// 创建点的显示样式对象
	/*
	 * var pSymbol = new esri.symbols.SimpleMarkerSymbol(); pSymbol.style =
	 * esri.symbols.SimpleMarkerSymbol.STYLE_CIRCLE; //设置点的类型为圆形
	 * pSymbol.setSize(12); //设置点的大小为12像素 pSymbol.setColor(new
	 * dojo.Color("#FFFFCC"));
	 */
	// 小图标图层
	var i;
	for (i = 0; i < data.length; i++) {
		var temp = 0;
		// 判断基站是连接还是断开
		if (data[i].status == 0) {
			temp = "bluesky/break_small.png";
		} else if (data[i].status == 1) {
			// 判断基站告警的级别
			if (typeof (data[i].alarmLevel) === "undefined") {
				temp = "bluesky/contact_small.png";
			} else if (data[i].alarmLevel == 1) {
				temp = "bluesky/normal_small.gif";
			} else if (data[i].alarmLevel == 2) {
				temp = "bluesky/warning_small.gif";
			} else if (data[i].alarmLevel == 3) {
				temp = "bluesky/urgent_small.gif";
			}
		} else if (data[i].status == 2) {
			temp = "bluesky/unuse_small.png";
		}

		var symbol = new esri.symbol.PictureMarkerSymbol(temp, 32, 32);
		var pt = new esri.geometry.Point(data[i].lng, data[i].lat);// 创建点对象
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
	var j;
	for (j = 0; j < data.length; j++) {
		var temp = 0;
		// 判断基站是连接还是断开
		if (data[j].status == 0) {
			temp = "bluesky/break_middle.png";
		} else if (data[j].status == 1) {
			// 判断基站告警的级别
			if (typeof (data[j].alarmLevel) === "undefined") {
				temp = "bluesky/contact_middle.png";
			} else if (data[j].alarmLevel == 1) {
				temp = "bluesky/normal_middle.gif";
			} else if (data[j].alarmLevel == 2) {
				temp = "bluesky/warning_middle.gif";
			} else if (data[j].alarmLevel == 3) {
				temp = "bluesky/urgent_middle.gif";
			}
		} else if (data[j].status == 2) {
			temp = "bluesky/unuse_middle.png";
		}

		var symbol = new esri.symbol.PictureMarkerSymbol(temp, 48, 48);
		var pt = new esri.geometry.Point(data[j].lng, data[j].lat);// 创建点对象
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
	var x;
	for (x = 0; x < data.length; x++) {
		var temp = 0;
		// 判断基站是连接还是断开
		if (data[x].status == 0) {
			temp = "bluesky/break_big.png";
		} else if (data[x].status == 1) {
			// 判断基站告警的级别
			if (typeof (data[x].alarmLevel) === "undefined") {
				temp = "bluesky/contact_big.png";
			} else if (data[x].alarmLevel == 1) {
				temp = "bluesky/normal_big.gif";
			} else if (data[x].alarmLevel == 2) {
				temp = "bluesky/warning_big.gif";
			} else if (data[x].alarmLevel == 3) {
				temp = "bluesky/urgent_big.gif";
			}
		} else if (data[x].status == 2) {
			temp = "bluesky/unuse_big.png";
		}

		var symbol = new esri.symbol.PictureMarkerSymbol(temp, 64, 64);
		var pt = new esri.geometry.Point(data[x].lng, data[x].lat);// 创建点对象
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

	// 添加控件
	// 比例尺
	require([ "esri/dijit/Scalebar" ], function(Scalebar) {
		var scalebar = new esri.dijit.Scalebar({
			map : myMap,
			// "dual" displays both miles and kilmometers
			// "english" is the default, which displays miles
			// use "metric" for kilometers
			scalebarUnit : "metric"
		});
	});
	// 鹰眼
	require([ "esri/dijit/OverviewMap" ], function(OverviewMap) {
		var overviewMapDijit = new esri.dijit.OverviewMap({
			map : myMap,
			visible : false
		});
		overviewMapDijit.startup();
	});
	// 图层组
	require([ "esri/dijit/LayerList" ], function(LayerList) {
		var layerList = new esri.dijit.LayerList({
			map: myMap,
		    showLegend: false,
		    showSubLayers: false,
		    showOpacitySlider: false,
		    layers: []
		},"test");
		layerList.startup();
	});
/*	require([ "esri/dijit/HeatmapSlider" ], function(HeatmapSlider) {
		var heatmapSlider = new esri.dijit.HeatmapSlider({
			colors: ["rgba(0, 0, 255, 0)","rgb(0, 0, 255)","rgb(255, 0, 255)", "rgb(255, 0, 0)"],
		    blurRadius: 12,
		    maxPixelIntensity: 250,
		    minPixelIntensity: 10
		});
	});*/
	//绘制图形
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
      parser.parse();
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
	/*
	 * myMap.on("mouse-move", function(e){ console.log(e.mapPoint.x ,
	 * e.mapPoint.y); });
	 */

	// 设置文字显示样式
	/*
	 * var textSymbol = new esri.symbols.TextSymbol().setColor( new
	 * esri.Color([0xFF,0,0])).setAlign(esri.symbols.Font.ALIGN_START).setFont(
	 * new esri.symbols.Font("12pt").setWeight(esri.symbols.Font.WEIGHT_BOLD)).
	 * setHaloColor(new esri.Color([0xFF,0xFF,0xFF])).setHaloSize(5) ; graphic =
	 * new esri.Graphic(pt,textSymbol,attr,infoTemplate); gLayer.add(graphic);
	 */// 将图形对象添加到图形显示图层
	// 创建面状图形填充样式对象
	/*
	 * var sfs = new
	 * esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID,
	 * new
	 * esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_DASHDOT,
	 * new dojo.Color([255,0,0]), 2),new dojo.Color([255,0,0,0.25]) );
	 */
	// json格式的面状图形数据，这里可以使用AJAX技术从后端获得
}
function init(data) {
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
		var j;
		var objTemp = [];
		for (j = 0; j < 10; j++) {
			var x = {
				name : data[j].name,
				value : data[j].bsId
			};
			objTemp.push(x);
		}
		/*console.log("data->"+objTemp[0].name);*/
		var overlay = new EchartsLayer(myMap, echarts);
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

		myMap.on('load', function() {
			var option = {
				color : [ 'gold', 'aqua', 'lime' ],
				tooltip : {
					trigger : 'item',
					formatter : '{b}'
				},
				dataRange : {
					min : 0,
					max : 1,
					calculable : true,
					color : [ 'red' ],
					textStyle : {
						color : '#fff'
					}
				},
				series : [ {
					name : '四川',
					type : 'map',
					roam : true,
					hoverable : false,
					mapType : 'none',
					itemStyle : {
						normal : {
							borderColor : 'rgba(100,149,237,1)',
							borderWidth : 0.5,
							areaStyle : {
								color : '#1b1b1b'
							}
						}
					},
					data : [],
					geoCoord : obj
				}, {
					name : '基站',
					type : 'map',
					mapType : 'none',
					data : [],
					markPoint : {
						symbol : 'emptyCircle',
						symbolSize : function(v) {
							return 10 + v / 10
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
			overlay.setOption(option);
			myMap.on('zoom-end', function() {
				if (myMap.getZoom() >= 10) {
					gLayer.hide();
					gLayermiddle.hide();
					gLayerbig.show();
				} else if (myMap.getZoom() >= 7 && myMap.getZoom() <= 9) {
					gLayer.hide();
					gLayerbig.hide();
					gLayermiddle.show();
				} else {
					gLayermiddle.hide();
					gLayerbig.hide();
					gLayer.show();
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
			init(data);
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
