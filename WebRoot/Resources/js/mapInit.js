
var app = angular.module("app", []);
var appElement = document.querySelector('[ng-controller=map]');
app.controller("map", function($scope, $http) {
	/**
	 * 首页弹出模态框数据获取和设置start
	 */
	$scope.count = "10";
	$scope.bsInformation = function() {
		var bsId = $scope.bsId;
		$http.get("bs/map/dataById?bsId=" + bsId).success(
				function(response) {
					$scope.bsinfoData = response.items[0];
				});
	};
	
	// 基站下的bsc状态
	$scope.bsc = function() {
		var bsId = $scope.bsId;
		$http.get("bsstatus/bsc?bsId=" + bsId).success(
				function(response) {
					$scope.bscData = response.items;
					$scope.bscTotals = response.totals;
				});
	};
	// 基站下的bsr状态
	$scope.bsr = function() {
		var bsId = $scope.bsId;
		$http.get("bsstatus/bsr?bsId=" + bsId).success(
				function(response) {
					$scope.bsrData = response.items;
					$scope.bsrTotals = response.totals;
				});
	};
	// 基站下的dpx状态
	$scope.dpx = function() {
		var bsId = $scope.bsId;
		$http.get("bsstatus/dpx?bsId=" + bsId).success(
				function(response) {
					$scope.dpxData = response.items;
					$scope.dpxTotals = response.totals;
				});
	};
	// 基站下的psm状态
	$scope.psm = function() {
		var bsId = $scope.bsId;
		$http.get("bsstatus/psm?bsId=" + bsId).success(
				function(response) {
					$scope.psmData = response.items;
					$scope.psmTotals = response.totals;
				});
	};
	// 根据基站ID查找基站相邻小区
	$scope.neighborByBsId = function() {
		var bsId = $scope.bsId;
		$http.get("bs/neighborByBsId?bsId=" + bsId).success(
				function(response) {
					$scope.neighborData = response.items;
					$scope.neighborTotals = response.totals;
				});
	};
	// 根据基站ID查找基站切换参数
	$scope.handoverByBsId = function() {
		var bsId = $scope.bsId;
		$http.get("bs/handoverByBsId?bsId=" + bsId).success(
				function(response) {
					$scope.handoverData = response.items;
					$scope.handoverTotals = response.totals;
				});
	};
	// 根据基站ID查找基站BSR配置信息
	$scope.bsrconfigByBsId= function() {
		var bsId = $scope.bsId;
		$http.get("bs/bsrconfigByBsId?bsId=" + bsId).success(
				function(response) {
					$scope.bsrconfigData = response.items;
					$scope.bsrconfigTotals = response.totals;
				});
	};
	// 根据基站ID查找基站传输配置信息
	$scope.linkconfigByBsId = function() {
		var bsId = $scope.bsId;
		$http.get("bs/linkconfigByBsId?bsId=" + bsId).success(
				function(response) {
					$scope.linkconfigData = response.items;
					$scope.linkconfigTotals = response.totals;
				});
	};

	$scope.equip = function() {
		$scope.bsc();
		$scope.bsr();
		$scope.dpx();
		$scope.psm();
	};
	//基站配置参数
	$scope.config = function() {
		$scope.neighborByBsId();
		$scope.handoverByBsId();
		$scope.bsrconfigByBsId();
		$scope.linkconfigByBsId();
	};
	// 基站下的注册终端
	$scope.radioUser = function() {
		var bsId = $scope.bsId;
		frist = 0;
		var pageSize = $("#page-limit").val();
		$http.get("radio/status/oneBsRadio?bsId=" + bsId
						+ "&start=0&limit=" + pageSize).success(
				function(response) {
					$scope.radioData = response.items;
					$scope.radioTotals = response.totals;
					xh.pagging(1, parseInt($scope.radioTotals), $scope);
				});
	};
	// 基站下的注册组
	$scope.bsGroup = function() {
		var bsId = $scope.bsId;
		var pageSize = $("#page-limit").val();
		$http.get("radio/status/oneBsGroup?bsId=" + bsId
						+ "&start=0&limit=" + pageSize).success(
				function(response) {
					$scope.groupData = response.items;
					$scope.groupTotals = response.totals;
				});
	};
	
	/**
	 * 首页弹出模态框数据获取和设置end
	 */
	
		
	//设置圈选的默认显示条数
	$scope.countChoose = "10";
	$http.get("bs/map/area").success(
			function(response) {
				$scope.data = response.items;
			});
	$http.get("bs/map/level").success(
			function(response) {
				$scope.levelData = response.items;
			});
	/* 级别选择 */
	var level={
			"1":{"lat":"30.6670418358257","lng":"104.07508986582853","zoom":"6"},
			"2":{"lat":"30.819648358042055","lng":"104.08952561793008","zoom":"6"},
			"3":{"lat":"30.680790171160506","lng":"103.91492175917804","zoom":"5"}
	}
	$scope.levelChoose=function(params,index){
		$scope.clickLevel=index;
		$http.get("bs/map/bsByLevel?level="+params).success(
				function(response) {
					var tempData = response.items;					
					var point = new esri.geometry.Point(level[params].lng*1, level[params].lat*1);
					myMap.centerAndZoom(point,level[params].zoom*1);
					layerCreate(tempData);
					option.series[0].markPoint.data=baseMark(tempData);
					option.series[1].markPoint.data=flashMark(tempData);
					myMap.removeLayer(gLayer);
					option.series[0].markPoint.symbol='pin';
					option.series[0].markPoint.symbolSize=8;
					overlay.setOption(option);
				});
	} 
	/* 区域选择 */
	var area={
			"简阳":{"lat":"30.425071133933102","lng":"104.55696901931353","zoom":"7"},
			"双流":{"lat":"30.52955848247763","lng":"103.93520055379688","zoom":"7"},
			"都江堰":{"lat":"31.041683973699183","lng":"103.62070738301315","zoom":"7"},
			"天府新区":{"lat":"30.406854589614476","lng":"104.08540111732965","zoom":"7"},
			"温江":{"lat":"30.665323293908852","lng":"103.83758737291974","zoom":"8"},
			"龙泉驿":{"lat":"30.60345578490222","lng":"104.29884402340252","zoom":"8"},
			"金堂":{"lat":"30.73097159513254","lng":"104.59133985765051","zoom":"7"},
			"青白江":{"lat":"30.79112056222232","lng":"104.34421353000737","zoom":"8"},
			"新都":{"lat":"30.837177485593934","lng":"104.10602362033183","zoom":"8"},
			"彭州":{"lat":"31.13998457134305","lng":"103.89636150647604","zoom":"7"},
			"郫都区":{"lat":"30.838552319127412","lng":"103.88123833760777","zoom":"8"},
			"崇州":{"lat":"30.644357082523264","lng":"103.64957888721625","zoom":"7"},
			"大邑":{"lat":"30.60998624418625","lng":"103.36705059608597","zoom":"7"},
			"邛崃":{"lat":"30.39070029559608","lng":"103.38079893142077","zoom":"7"},
			"蒲江":{"lat":"30.23775006499635","lng":"103.48941078056576","zoom":"8"},
			"新津":{"lat":"30.427820801000056","lng":"103.81799599506768","zoom":"8"},
			"高新区":{"lat":"30.56891309237351","lng":"104.07165278199483","zoom":"9"},
			"成华区":{"lat":"30.693163672961827","lng":"104.14314412573583","zoom":"9"},
			"武侯区":{"lat":"30.62682795497139","lng":"104.0120193774801","zoom":"9"},
			"金牛区":{"lat":"30.728737490640643","lng":"104.09227528499704","zoom":"9"},
			"锦江区":{"lat":"30.60553707749492","lng":"104.11306964219092","zoom":"9"},
			"青羊区":{"lat":"30.68010275439376","lng":"103.98581161324812","zoom":"9"},
			"金牛区":{"lat":"30.73784576279995","lng":"104.05910742600184","zoom":"9"}		
	};
	$scope.areaChoose=function(params){
		$scope.clickLevel=0;
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
						areaRingsData(params);
					});
		} else {
			var t=[];
			$(".areaChoose input:checked").each(function(i){
				t.push($(this).val());
			});
			if(t.length==0){
				$http.get("bs/map/data").success(
						function(response) {
							var tempData = response.items;
							layerCreate(tempData);
							option.series[0].markPoint.data=baseMark(tempData);
							option.series[1].markPoint.data=[];
							overlay.setOption(option);
							areaRingsClear(params);
						});
			}else{
				$http.get("bs/map/bsByArea?zone="+t).success(
						function(response) {
							var tempData = response.items;
							layerCreate(tempData);
							option.series[0].markPoint.data=baseMark(tempData);
							option.series[1].markPoint.data=flashMark(tempData);
							overlay.setOption(option);
							areaRingsClear(params);
						});
			}
		}
		
	} 
	
	$scope.test=function(){
		var t = $scope.top5Calllist;
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
	/* 刷新数据  业务*/
	$scope.refresh = function() {
		$scope.search(1);
	};
	/* 查询数据  业务*/
	$scope.search = function(page) {
		var $scope = angular.element(appElement).scope();
		var pageSize = $("#page-limit").val();
		var start = 1, limit = pageSize;
		frist = 0;
		page = parseInt(page);
		if (page <= 1) {
			start = 0;

		} else {
			start = (page - 1) * pageSize;
		}
		xh.maskShow();
		$http.get("radio/status/oneBsRadio?bsId=" + $scope.bsId
						+ "&start=" + start + "&limit=" + pageSize)
				.success(function(response) {
					xh.maskHide();
					$scope.radioData = response.items;
					$scope.radioTotals = response.totals;
					xh.pagging(page, parseInt($scope.radioTotals), $scope);
				});
	};
	
	
	/* 查询数据  圈选*/
	$scope.searchChoose = function(page,params) {
		var pageSize = $("#page-limitChoose").val();
		var start = 1, limit = pageSize;
		frist = 0;
		page = parseInt(page);
		if (page <= 1) {
			start = 0;

		} else {
			start = (page - 1) * pageSize;
		}
		
		$http.get("bs/rectangle?params="+params+"&start="+start+"&limit="+limit).
		success(function(response){
			var tempData=response.items;	
			//添加模拟数据 start
			for(var i=0;i<tempData.length;i++){
				tempData[i].testnum1=parseInt(Math.random()*(99-5+1) + 5);
				tempData[i].testnum2=parseInt(Math.random()*(99-5+1) + 5);
				tempData[i].testnum3=parseInt(Math.random()*(65-16+1) + 11)+"%";
			}
			//添加模拟数据 end
			$scope.dataRectangle = tempData;		
			$scope.totalsChoose = response.totals;
			xh.paggingChoose(page, parseInt($scope.totalsChoose), $scope,params);
		});
	};
	//分页点击，业务
	$scope.pageClick = function(page, totals, totalPages) {
		var pageSize = $("#page-limit").val();
		var start = 1, limit = pageSize;
		page = parseInt(page);
		if (page <= 1) {
			start = 0;
		} else {
			start = (page - 1) * pageSize;
		}
		xh.maskShow();
		$http.get("radio/status/oneBsRadio?bsId=" + $scope.bsId
						+ "&start=" + start + "&limit=" + pageSize)
				.success(function(response) {
					xh.maskHide();

					$scope.start = (page - 1) * pageSize + 1;
					$scope.lastIndex = page * pageSize;
					if (page == totalPages) {
						if (totals > 0) {
							$scope.lastIndex = totals;
						} else {
							$scope.start = 0;
							$scope.lastIndex = 0;
						}
					}
					$scope.radioData = response.items;
					$scope.radioTotals = response.totals;
				});

	};
	//分页点击,圈选
	$scope.pageClickChoose = function(page, totals, totalPages, params) {		
		var pageSize = $("#page-limitChoose").val();
		var start = 1, limit = pageSize;
		page = parseInt(page);
		if (page <= 1) {
			start = 0;
		} else {
			start = (page - 1) * pageSize;
		}
		$http.get("bs/rectangle?params="+params+"&start="+start+"&limit="+pageSize).
		success(function(response){
			$scope.startChoose = (page - 1) * pageSize + 1;
			$scope.lastIndexChoose = page * pageSize;
			if (page == totalPages) {
				if (totals > 0) {
					$scope.lastIndexChoose = totals;
				} else {
					$scope.startChoose = 0;
					$scope.lastIndexChoose = 0;
				}
			}
			var tempData=response.items;
			//添加模拟数据 start			
			for(var i=0;i<tempData.length;i++){
				tempData[i].testnum1=parseInt(Math.random()*(99-5+1) + 5);
				tempData[i].testnum2=parseInt(Math.random()*(99-5+1) + 5);
				tempData[i].testnum3=parseInt(Math.random()*(65-16+1) + 11)+"%";
			}
			//添加模拟数据 end
			$scope.dataRectangle = tempData;
			$scope.totalsChoose = response.totals;
		});
		
	};
	
});

//刷新数据 业务
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();

};

/* 数据分页 业务 */
xh.pagging = function(currentPage, totals, $scope) {
	var pageSize = $("#page-limit").val();
	var totalPages = (parseInt(totals, 10) / pageSize) < 1 ? 1 : Math
			.ceil(parseInt(totals, 10) / pageSize);
	var start = (currentPage - 1) * pageSize + 1;
	var end = currentPage * pageSize;
	if (currentPage == totalPages) {
		if (totals > 0) {
			end = totals;
		} else {
			start = 0;
			end = 0;
		}
	}
	$scope.start = start;
	$scope.lastIndex = end;
	$scope.totals = totals;
	if (totals > 0) {
		$(".page-paging").html('<ul class="pagination"></ul>');
		$('.pagination').twbsPagination({
			totalPages : totalPages,
			visiblePages : 3,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.pageClick(page, totals, totalPages);
				}
				frist = 1;

			}
		});
	}
};

/* 数据分页 圈选 */
xh.paggingChoose = function(currentPage, totals, $scope, params) {
	var pageSize = $("#page-limitChoose").val();
	var totalPages = (parseInt(totals, 10) / pageSize) < 1 ? 1 : Math
			.ceil(parseInt(totals, 10) / pageSize);
	var start = (currentPage - 1) * pageSize + 1;
	var end = currentPage * pageSize;
	if (currentPage == totalPages) {
		if (totals > 0) {
			end = totals;
		} else {
			start = 0;
			end = 0;
		}
	}
	$scope.startChoose = start;
	$scope.lastIndexChoose = end;
	$scope.totalsChoose = totals;
	if (totals > 0) {
		$(".page-pagingChoose").html('<ul class="paginationChoose"></ul>');
		$('.paginationChoose').twbsPagination({
			totalPages : totalPages,
			visiblePages : 10,
			version : '1.1',
			startPage : currentPage,
			onPageClick : function(event, page) {
				if (frist == 1) {
					$scope.pageClickChoose(page, totals, totalPages, params);
				}
				frist = 1;

			}
		});
	}

};

/**
 * 本示例使用arcgis api for javascript 技术显示地图。 相关官方API文档地址为:
 * https://developers.arcgis.com/javascript/jsapi/ 所有示例代码访问地址为：
 * https://developers.arcgis.com/javascript/jssamples/
 */
dojo.require("esri.map");
var myTiledMapServiceLayer;
var myMap;
var scalebar;
var draw;
var gLayer;
var gLayermiddle;
var gLayerbig;
var levelLayer,areaLayer
var roadtest;
var areaRings;
var rectangle;
var test;
var testDemo;
var chooseLayer=0;
function floor(data) {
	var options = {
		logo : false
	};
	esri.symbols = esri.symbol;
	myMap = new esri.Map("mapDiv", options);// 在mapDiv中创建map地图对象
	// 创建底图图层对象,http://10.190.230.165/arcgisditu/rest/services/NEWMAP/MapServer为政务外网底图服务地址,
	console.log(chooseLayer);
	if(chooseLayer==0){
		myTiledMapServiceLayer = new
		esri.layers.ArcGISTiledMapServiceLayer(
				"http://125.70.9.194:801/services/MapServer/map2d");// 底图切片服务 http://125.70.9.194:801/services/MapServer/map2d
		myMap.addLayer(myTiledMapServiceLayer);// 将底图图层对象添加到地图中
	}else if(chooseLayer==1){
		testDemo = new
		esri.layers.ArcGISTiledMapServiceLayer(
				"http://125.70.9.194:6080/common/rest/services/800M/800M_20160823/MapServer");// 仿真图切片服务
		myMap.addLayer(testDemo);// 将图层对象添加到地图中
	}
	/*test = new
	esri.layers.ArcGISDynamicMapServiceLayer("http://125.70.9.194:6080/arcgis/rest/services/800M/Feature/MapServer");*///动态服务
	//myMap.addLayer(test);// 将底图图层对象添加到地图中
	
	gLayer = new esri.layers.GraphicsLayer({id:"小图标"}); // 创建图形显示图层，图形显示图层专门用于在地图上显示点，线，面图形数据
	gLayermiddle = new esri.layers.GraphicsLayer({id:"中图标"});// 创建中图标图层
	gLayerbig = new esri.layers.GraphicsLayer({id:"大图标"});// 创建大图形显示图层
	levelLayer = new esri.layers.GraphicsLayer({id:"基站级别"});
	areaLayer = new esri.layers.GraphicsLayer({id:"基站区域"});
	roadtest = new esri.layers.GraphicsLayer({id:"路测数据"});
	areaRings = new esri.layers.GraphicsLayer({id:"区域边界"});
	rectangle = new esri.layers.GraphicsLayer({id:"圈选功能"});
	var point = new esri.geometry.Point(104.06340378079395, 30.66016766815829);
	myMap.centerAndZoom(point, 6);// 地图首次加载显示的位置和放大级别
	myMap.addLayer(gLayer);// 将图形显示图层添加到地图中
	myMap.setInfoWindowOnClick(true);
	myMap.addLayer(areaRings);
	myMap.addLayer(rectangle);
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
		    layers: [areaRings]
		},"test");
		layerList.startup();
	});
	//区域圈选
	var toolbar, symbol, geomTask;
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
      // loop through all dijits, connect onClick event
      // listeners for buttons to activate drawing tools
      registry.forEach(function(d) {
          // d is a reference to a dijit
          // could be a layout container or a button
          if ( d.declaredClass === "dijit.form.Button" ) {
            d.on("click", activateTool);
          }
        });
      var params;
      function activateTool() {
    	  toolbar = new Draw(myMap);
    	  //开启鼠标监听
    	  params = mouseEvents();
          toolbar.on("draw-end", addToMap);
          var temp = "RECTANGLE";
    	  var tool = temp.replace(/ /g, "_");
          toolbar.activate(Draw[tool]);
          myMap.hideZoomSlider();
      }

      function addToMap(evt) {
    	  var symbol;
          toolbar.deactivate();
          myMap.showZoomSlider();
          switch (evt.geometry.type) {
            case "point":
            case "multipoint":
              symbol = new SimpleMarkerSymbol();
              break;
            case "polyline":
              symbol = new SimpleLineSymbol();
              break;
            default:
              symbol = new SimpleFillSymbol();
              break;
          }
          var graphic = new Graphic(evt.geometry, symbol);
          graphic.attributes=params;
          rectangle.add(graphic);
          $('#rectangle').modal();
          var appElement = document.querySelector('[ng-controller=map]');
      	  var $scope = angular.element(appElement).scope();
      	  $scope.searchChoose(1,params);
      }
      
    });
}

//圈选模态框消失后清除图层
$("#rectangle").on("hide.bs.modal",function(){
	rectangle.clear();
});

function mouseEvents(){
	var params=[];
	// 鼠标获取经纬度
    var mouseDown = myMap.on("mouse-down", function(e){
    	params.push(e.mapPoint.x);
    	params.push(e.mapPoint.y);
    	console.log(e.mapPoint.x ,e.mapPoint.y);
    	mouseDown.remove();
  	  });
    var mouseUp = myMap.on("mouse-up", function(e){
    	params.push(e.mapPoint.x);
    	params.push(e.mapPoint.y);
    	console.log(e.mapPoint.x ,e.mapPoint.y);
    	mouseUp.remove();
  	  }); 
    return params;
}

var areaRef={	
		"简阳":{name:"简阳",color:"#000000"},
		"双流":{name:"双流",color:"#000000"},
		"都江堰":{name:"都江堰",color:"#191970"},
		"天府新区":{name:"天府新区",color:"#0000FF"},
		"温江":{name:"温江",color:"#458B00"},
		"龙泉驿":{name:"龙泉驿",color:"#006400"},
		"金堂":{name:"金堂",color:"#228B22"},
		"青白江":{name:"青白江",color:"#8B7500"},
		"新都":{name:"新都",color:"#8B658B"},
		"彭州":{name:"彭州",color:"#A52A2A"},
		"郫都区":{name:"郫都区",color:"#FF0000"},
		"崇州":{name:"崇州",color:"#008B8B"},
		"大邑":{name:"大邑",color:"#8B008B"},
		"邛崃":{name:"邛崃",color:"#8B0000"},
		"蒲江":{name:"蒲江",color:"#F4A460"},
		"新津":{name:"新津",color:"#FF1493"},
		"高新区":{name:"高新区",color:"#191970"},
		"成华区":{name:"成华区",color:"#0000FF"},
		"武侯区":{name:"武侯区",color:"#006400"},
		"金牛区":{name:"金牛区",color:"#FF00FF"},
		"锦江区":{name:"锦江区",color:"#8B658B"},
		"青白江":{name:"青白江",color:"#8B008B"},
		"青羊区":{name:"青羊区",color:"#FF0000"}
}
//区域边界图层数据 
function areaRingsData(param){
	var areaInfo=areaRef[param].name;
	var color=areaRef[param].color;
	/*var areaSearch="http://125.70.9.194:6080/common/rest/services/YingJiBan/Region/MapServer/find?searchText="+areaInfo+"&contains=true&searchFields=&sr=&layers=0&layerDefs=&returnGeometry=true&maxAllowableOffset=&geometryPrecision=&dynamicLayers=&returnZ=false&returnM=false&gdbVersion=&f=pjson";*/
	/*$.ajax({
		type : "GET",
		url : areaSearch,
		dataType : "json",
		success : function(dataMap) {
			var data = dataMap.results[0].geometry.rings[0];
			var temp=[];
			var tempData=[];
			var i;
			for(i=0;i<data.length;i+=10){
				temp.push(data[i]);
			}
			tempData.push(temp);
			var params=[param,color];
			areaRingsCreate(tempData,params);
		}
	});*/
	$.getJSON("Resources/js/mapArea.json", function (dataMap){
		var data = dataMap[areaInfo].results[0].geometry.rings[0];
		var temp=[];
		var tempData=[];
		var i;
		for(i=0;i<data.length;i+=10){
			temp.push(data[i]);
		}
		tempData.push(temp);
		var params=[param,color];
		areaRingsCreate(tempData,params);
	});
}

//区域边界图层创建
function areaRingsCreate(data,params){
	require(["esri/Color"], function(Color) {	
		/*var i;
		for(i=0;i<data.length;i++){
			var temp=[255,0,0];
			var symbol = new esri.symbol.SimpleMarkerSymbol({
				"color": temp,
				  "size": 4,
				  "type": "simplemarkersymbol"
			});
			var pt = new esri.geometry.Point(data[i][0]*1, data[i][1]*1);// 创建点对象
			var attr = {
					"db" : ""
			};// 设置相关的属性信息对象
			var infoTemplate = new esri.InfoTemplate("弹出窗口的标题",
					"");// 创建弹出窗口内容显示模板
			var graphic = new esri.Graphic(pt, symbol, attr, infoTemplate);// 创建图形对象
			areaRings.add(graphic);// 将图形对象添加到图形显示图层
		}*/
		var line = new esri.geometry.Polyline({
			   "paths": data,
			   "spatialReference": { "wkid": 4326 }
			});
		var lineSymbol = new esri.symbol.CartographicLineSymbol(
			  esri.symbol.CartographicLineSymbol.STYLE_SOLID,
			  new dojo.Color(params[1]), 3,
			  esri.symbol.CartographicLineSymbol.CAP_ROUND,
			  esri.symbol.CartographicLineSymbol.JOIN_MITER, 1
			);
		var polyline = new esri.Graphic(line, lineSymbol);
		polyline.attributes=params[0];
		areaRings.add(polyline);
	});
}

// 区域边界图层删除
function areaRingsClear(param){
	var i;
	for(i=0;i<areaRings.graphics.length;i++){
		if(areaRings.graphics[i].attributes==param){
			areaRings.remove(areaRings.graphics[i]);
		}
	}
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
		if(data[i].status == 0){
			temp = "bluesky/unuse_small.png";
		}else if (data[i].bsStatus == 0) {
			temp = "bluesky/contact_small.png";
		} else {
			// 判断基站告警的级别
			/*if (typeof (data[i].alarmLevel) === "undefined") {
				temp = "bluesky/contact_small.png";
			} else if (data[i].alarmLevel == 1) {
				temp = "bluesky/normal_small.gif";
			} else if (data[i].alarmLevel == 2) {
				temp = "bluesky/warning_small.gif";
			} else if (data[i].alarmLevel == 3) {
				temp = "bluesky/urgent_small.gif";
			}*/
			temp = "bluesky/break_small.png";
		}

		var symbol = new esri.symbol.PictureMarkerSymbol(temp, parseInt(24), parseInt(24));
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
		if(data[j].status == 0){
			temp = "bluesky/unuse_middle.png";
		}else if (data[j].bsStatus == 0) {
			temp = "bluesky/contact_middle.png";
		} else {
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
			temp = "bluesky/break_middle.png";
		}

		var symbol = new esri.symbol.PictureMarkerSymbol(temp, parseInt(32), parseInt(32));
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
		if(data[x].status == 0){
			temp = "bluesky/unuse_big.png";
		}else if (data[x].bsStatus == 0) {
			temp = "bluesky/contact_big.png";
		} else {
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
			temp = "bluesky/break_big.png";
		}

		var symbol = new esri.symbol.PictureMarkerSymbol(temp, parseInt(48), parseInt(48));
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
		overlay = new EchartsLayer(myMap, echarts);
		var chartsContainer = overlay.getEchartsContainer();
		var myChart = overlay.initECharts(chartsContainer);
		window.onresize = myChart.onresize;
		// 为echarts绑定事件
		myChart.on('click', function(params) {
			/* 基站图标设置模态框并获取显示数据 */
			$('#myModal').modal();
			var appElement = document.querySelector('[ng-controller=map]');
			var $scope = angular.element(appElement).scope();
			$scope.bsId=params.value;
			$scope.bsInformation();
			/*$.ajax({
				type : "GET",
				url : "bs/map/dataById?bsId=" + params.value,
				dataType : "json",
				success : function(dataById) {
					var dataTemp = dataById.items;
					var data = dataTemp[0];
					$('#bsId').val(data.bsId);
					$('#bsName').val(data.name);
					if(data.period==3){
						$('#period').val("三期基站");
					}else{
						$('#period').val("四期基站");
					}
					$('#type').val(data.type);
					$('#address').val(data.address);
					$('#ip').val(data.ip);
					if (data.contact == '') {
						$('#contact').val("暂无相关信息");
					} else if (data.tel == 0) {
						$('#tel').val(data.tel + " 暂无电话");
					} else {
						$('#contact').val(data.contact);
						$('#tel').val(data.tel);
					}
					
					$('#chnumber').val(data.chnumber);
					$('#gpsLineNum').val(data.gpsLineNum);
					$('#power').val(data.power);
					$('#carrier').val(data.carrier);
					$('#height').val(data.height);
					$('#lineHeight').val(data.lineHeight);
					if (data.bsStatus == '') {
						$('#status').val("暂无相关信息");
					} else if (data.status == 0) {
						$('#status').val("正常");
					}else{
						$('#status').val("未启用");
					}
					
					$('#business1').val(data.upload);
					$('#business2').val(data.download);
					$('#business3').val(data.queuedAllocReq);
					$('#business4').val(data.time);
					*//**
					 * start
					 * 业务部分模拟数据
					 *//*
					$('#business1').val(parseInt(Math.random()*(99-5+1) + 5));
					$('#business2').val(parseInt(Math.random()*(50-16+1) + 16)+"%");
					$('#business3').val(parseInt(Math.random()*(65-16+1) + 11)+"%");
					$('#business4').val(parseInt(Math.random()*(99-5+1) + 5));
					*//**
					 * end
					 *//*
					
					$('#temp_0').val(0.0);
					$('#temp_1').val(1.0);
					$('#temp_2').val(0.0);
					$('#temp_3').val(1.0);
					highChart(data);
				}
			});*/
			//gosuncn1
			/*$.ajax({
				type : "GET",
				url : "gonsuncn/oneBsEmh?bsId=" + params.value,
				dataType : "json",
				success : function(dataById) {
					// 动环数据展示
					var data = dataById.items;
					$('#temp_0').val(data[2]["017001"]);
					$('#temp_1').val(data[3]["017002"]);
					$('#temp_2').val(data[4]["017004"]);
					$('#temp_3').val(data[5]["017020"]);
					highChart(data);
					$("#movie").load("http://192.168.5.253/doc/page/simplepreview.asp"+" #movie"); 
				}
			});*/
		});
		//地图加载时执行
		option = {
				color : [ 'gold', 'aqua', 'lime' ],
				tooltip: {
					formatter: function (params) {
						$.ajax({
							type : "GET",
							url : "bs/map/data",
							dataType : "json",
							success : function(dataMap) {
								
							}
						});
						console.log(params);
                        var res;
                        var temp1 = parseInt(Math.random()*(99-5+1) + 5)+"%";
                        var temp2 = parseInt(Math.random()*(99-5+1) + 6);
                        var temp3 = parseInt(Math.random()*(99-5+1) + 7);
                        var temp4 = parseInt(Math.random()*(0-5+1) + 5);
                        res = '基站ID：'+params["2"]+
                        '<br/>'+'基站名称：'+params["1"]+
                        '<br/>'+'信道占用率：'+ temp1 +
                        '<br/>'+'注册组数：'+ temp2 +
                        '<br/>'+'注册用户数：'+ temp3 +
                        '<br/>'+'排队数：' + temp4;
                        return res;
                    },
                    show: true,
                    trigger: 'item',
                    //show: true,   //default true
                    showDelay: 600,//显示延时，添加显示延时可以避免频繁切换
                    hideDelay: 50,//隐藏延时
                    transitionDuration: 0,//动画变换时长
                    backgroundColor: 'rgba(0,0,0,0.7)',//背景颜色（此时为默认色）
                    borderRadius: 8,//边框圆角
                    padding: 10,    // [5, 10, 15, 20] 内边距
                    position: function (p) {
                        // 位置回调
                        // console.log && console.log(p);
                        return [p[0] + 10, p[1] - 10];
                    }  
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
						symbolSize: 12,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
		                
		                data:objAll
					}
				}, {
					name : '基站',
					type : 'map',
					mapType : 'none',
					data : [],
					markPoint : {
						symbol : 'image://',//'emptyCircle'
						
						symbolSize : function(v) {
							return 12
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
		/*addTest*/
		myMap.on("mouse-down", function(e){
	    	console.log(e.mapPoint.x ,e.mapPoint.y);
	  	  });
		myMap.on('zoom-end', function() {
			console.log(myMap.getZoom());
			if ($("#bsInfo").prop("checked") == true){
				if (myMap.getZoom() >= 10) {
					myMap.removeLayer(gLayer);
					myMap.removeLayer(gLayermiddle);
					myMap.addLayer(gLayerbig);
					option.series[0].markPoint.symbol='image://';
					option.series[0].markPoint.symbolSize=32;
					overlay.setOption(option);
				} else if (myMap.getZoom() >= 7 && myMap.getZoom() <= 9) {
					myMap.removeLayer(gLayer);
					myMap.removeLayer(gLayerbig);
					myMap.addLayer(gLayermiddle);
					option.series[0].markPoint.symbol='image://';
					option.series[0].markPoint.symbolSize=24;
					overlay.setOption(option);
				} else if(myMap.getZoom() <= 6){
					myMap.removeLayer(gLayermiddle);
					myMap.removeLayer(gLayerbig);
					myMap.removeLayer(gLayer);
					option.series[0].markPoint.symbol='pin';
					option.series[0].markPoint.symbolSize=8;
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
			
			$("#testDemo").click(function() {
				if ($(this).prop("checked") == true) {	
					myMap.destroy();
					chooseLayer=1;
					getData();
					setTimeout("tempCenterAndZoom()","3000");
									
				} else {
					
					window.location.href="map.html"; 
				}
			});
			
		});
		
	});
}

function tempCenterAndZoom(){
	var point = new esri.geometry.Point(103.99742132710227, 30.62468990456136);
	myMap.centerAndZoom(point,1);//定位地图位置
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

//highcharts
function highChart(data) {
	
	//温度计
    $('#temperature').highcharts({
    	credits: {
    		enabled:false
    	},
    	exporting: { 
    		enabled:false 
    	},
        chart: {
            type: 'gauge',
            plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: 0,
            plotShadow: false
        },
        title: {
            text: '温度仪'
        },
        pane: {
            startAngle: -150,
            endAngle: 150,
            background: [{
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#FFF'],
                        [1, '#333']
                    ]
                },
                borderWidth: 0,
                outerRadius: '109%'
            }, {
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#333'],
                        [1, '#FFF']
                    ]
                },
                borderWidth: 1,
                outerRadius: '107%'
            }, {
                // default background
            }, {
                backgroundColor: '#DDD',
                borderWidth: 0,
                outerRadius: '105%',
                innerRadius: '103%'
            }]
        },
        // the value axis
        yAxis: {
            min: 0,
            max: 80,
            minorTickInterval: 'auto',
            minorTickWidth: 1,
            minorTickLength: 10,
            minorTickPosition: 'inside',
            minorTickColor: '#666',
            tickPixelInterval: 30,
            tickWidth: 2,
            tickPosition: 'inside',
            tickLength: 10,
            tickColor: '#666',
            labels: {
                step: 2,
                rotation: 'auto'
            },
            title: {
                text: '℃'
            },
            plotBands: [{
                from: 0,
                to: 30,
                color: '#55BF3B' // green
            }, {
                from: 30,
                to: 60,
                color: '#DDDF0D' // yellow
            }, {
                from: 50,
                to: 80,
                color: '#DF5353' // red
            }]
        },
        series: [{
            name: '温度',
            data: [/*data[0]["017301"]*1*/28.32],
            tooltip: {
                valueSuffix: ' ℃'
            }
        }]
    });
    
    
    //湿度计
    $('#humidity').highcharts({
    	credits: {
    		enabled:false
    	},
    	exporting: { 
    		enabled:false 
    	},
        chart: {
            type: 'gauge',
            plotBackgroundColor: null,
            plotBackgroundImage: null,
            plotBorderWidth: 0,
            plotShadow: false
        },
        title: {
            text: '湿度计'
        },
        pane: {
            startAngle: -150,
            endAngle: 150,
            background: [{
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#FFF'],
                        [1, '#333']
                    ]
                },
                borderWidth: 0,
                outerRadius: '109%'
            }, {
                backgroundColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, '#333'],
                        [1, '#FFF']
                    ]
                },
                borderWidth: 1,
                outerRadius: '107%'
            }, {
                // default background
            }, {
                backgroundColor: '#DDD',
                borderWidth: 0,
                outerRadius: '105%',
                innerRadius: '103%'
            }]
        },
        // the value axis
        yAxis: {
            min: 0,
            max: 120,
            minorTickInterval: 'auto',
            minorTickWidth: 1,
            minorTickLength: 10,
            minorTickPosition: 'inside',
            minorTickColor: '#666',
            tickPixelInterval: 30,
            tickWidth: 2,
            tickPosition: 'inside',
            tickLength: 10,
            tickColor: '#666',
            labels: {
                step: 2,
                rotation: 'auto'
            },
            title: {
                text: '℃'
            },
            plotBands: [{
                from: 0,
                to: 40,
                color: '#DDDF0D' // yellow
            }, {
                from: 40,
                to: 80,
                color: '#55BF3B' // green
            }, {
                from: 80,
                to: 120,
                color: '#DF5353' // red
            }]
        },
        series: [{
            name: '湿度',
            data: [/*data[1]["017302"]*1*/37.63],
            tooltip: {
                valueSuffix: ' ℃'
            }
        }]
    });
    
    //伏压图
    $('#Current_voltage').highcharts({
    	credits: {
    		enabled:false
    	},
    	exporting: { 
    		enabled:false 
    	},
        chart: {
            type: 'gauge',
            plotBorderWidth: 1,
            plotBackgroundColor: {
                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1,x3: 0, y3: 1 },
                stops: [
                    [0, '#FFF4C6'],
                    [0.3, '#FFFFFF'],
                    [1, '#FFF4C6']
                ]
            },
            plotBackgroundImage: null,
            height: 200
        },
        title: {
            text: '电流/电压图'
        },
        pane: [{
            startAngle: -45,
            endAngle: 45,
            background: null,
            center: ['18%', '115%'],
            size: 200
        }, {
            startAngle: -45,
            endAngle: 45,
            background: null,
            center: ['52%', '115%'],
            size: 200
        },{
            startAngle: -45,
            endAngle: 45,
            background: null,
            center: ['85%', '115%'],
            size: 200
        }],
        yAxis: [{
            min: -20,
            max: 6,
            minorTickPosition: 'outside',
            tickPosition: 'outside',
            labels: {
                rotation: 'auto',
                distance: 20
            },
            plotBands: [{
                from: 0,
                to: 6,
                color: '#C02316',
                innerRadius: '100%',
                outerRadius: '105%'
            }],
            pane: 0,
            title: {
                text: 'V<br/><span style="font-size:8px">电压</span>',
                y: -40
            }
        }, {
            min: -20,
            max: 6,
            minorTickPosition: 'outside',
            tickPosition: 'outside',
            labels: {
                rotation: 'auto',
                distance: 20
            },
            plotBands: [{
                from: 0,
                to: 6,
                color: '#C02316',
                innerRadius: '100%',
                outerRadius: '105%'
            }],
            pane: 1,
            title: {
                text: 'V<br/><span style="font-size:8px">电压</span>',
                y: -40
            }
        },{
            min: -20,
            max: 6,
            minorTickPosition: 'outside',
            tickPosition: 'outside',
            labels: {
                rotation: 'auto',
                distance: 20
            },
            plotBands: [{
                from: 0,
                to: 6,
                color: '#C02316',
                innerRadius: '100%',
                outerRadius: '105%'
            }],
            pane: 2,
            title: {
                text: 'A<br/><span style="font-size:8px">电流</span>',
                y: -40
            }
        }],
        plotOptions: {
            gauge: {
                dataLabels: {
                    enabled: false
                },
                dial: {
                    radius: '100%'
                }
            }
        },
        series: [{
            data: [-20],
            yAxis: 0
        }, {
            data: [-20],
            yAxis: 1
        },{
            data: [-20],
            yAxis: 2
        }]
    });
    
    
}

