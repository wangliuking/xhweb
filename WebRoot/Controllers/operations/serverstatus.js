/**
 * 终端状态
 */
if (!("xh" in window)) {
	window.xh = {};
};
var background="#fff";
var frist = 0;
var wheight=document.documentElement.clientHeight;
var wwidth=document.documentElement.clientWidth;
var appElement = document.querySelector('[ng-controller=xhcontroller]');
//画布
var canvas = document.getElementById('server-canvas');
canvas.width = document.documentElement.clientWidth-42;
canvas.height = document.documentElement.clientHeight-60;
var stage = new JTopo.Stage(canvas);
var scene = new JTopo.Scene();
stage.add(scene);
/*scene.background = '../../Resources/images/img/bg.jpg';80,100,80*/
scene.alpha=1;
scene.backgroundColor='53,63,79';
/*stage.eagleEye.visible = true;*/
stage.wheelZoom = 0.85;

$(window).resize(function(){
	canvas.width = document.documentElement.clientWidth-22;
	canvas.height = document.documentElement.clientHeight-38;
})


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
		xh.maskShow();
		$scope.accessCurrentNode = null;
		$http.get("../../status/dispatch").
		success(function(response){
			xh.maskHide();
			$scope.dispatch = response.items;
			$scope.dTotals = response.totals;
		});
		 
		 $scope.emh = function() {
 			$http.get("../../bsstatus/bsEmh?siteId=200&period=3").success(function(response) {
 				$scope.emhData = response;
 				
 				
 				
 			});
		 }
            $scope.serverCanvas=function(){
    			scene.clear();
    			var point1 =xh.createNewNode(wwidth/2,0,10,10,"");
    			var point2 =xh.createNewNode(wwidth/2,wheight-70,10,10,"");
    			
    			var point3 =xh.createNewNode(0,wheight/10+160,10,10,"");
    			var point4 =xh.createNewNode(wwidth-50,wheight/10+160,10,10,"");
    			
    			var point5 =xh.createNewNode(0,wheight/2+150,10,10,"");
    			var point6 =xh.createNewNode(wwidth-50,wheight/2+150,10,10,10,"");
    			
    			var link_point = xh.createNewLink(point1, point2,5);
    			link_point.strokeColor = '169,169,169';
    			link_point.dashedPattern =15; // 虚线
    			
    			var link_point2 = xh.createNewLink(point3, point4,5);
    			link_point2.strokeColor = '169,169,169';
    			link_point2.dashedPattern =15; // 虚线
    			
    			var link_point3 = xh.createNewLink(point5, point6,5);
    			link_point3.strokeColor = '169,169,169';
    			link_point3.dashedPattern =15; // 虚线
    			
    			//交换中心1
    			//结点
    			var emh1 =xh.createNode(400, wheight/10,"emh.png","动环设备");
    			var server1=xh.createNode(500, wheight/10,"xh.png","综合应用与平台");
    			
    			
    			var sw1 =xh.createNode(300, wheight/10+200,"switch.png","交换机");
    			var sw2 =xh.createNode(400, wheight/10+200,"switch.png","交换机");
    			var sw3 =xh.createNode(500, wheight/10+200,"switch.png","交换机");
    			
    			var hsw1 =xh.createNode(400, wheight/2+30,"hsw.png","核心交换机");
    			
    			var sw4 =xh.createNode(200, wheight/2+100,"switch.png","交换机");
    			var sw5 =xh.createNode(300, wheight/2+100,"switch.png","交换机");
    			var sw6 =xh.createNode(400, wheight/2+100,"switch.png","交换机");
    			var sw7 =xh.createNode(500, wheight/2+100,"switch.png","交换机");
    			
    			var poc1 =xh.createNode(200,wheight/2+200,"poc.png","POC系统");
                var bridging1 =xh.createNode(300, wheight/2+200,"bridging.png","桥接系统");
                var dispatch1 =xh.createNode(400, wheight/2+200,"dispatch.png","调度台");
                var bs1 =xh.createNode(500, wheight/2+200,"bs.png","基站");
    			
    			
    			//连线
                var link1 = xh.createNewLink(server1, sw3,5);
                link1.strokeColor = '0,255,0';
                var link2 = xh.createNewLink(emh1, sw2,5);
                link2.strokeColor = '0,255,0';
                
                var link3 = xh.createNewLink(hsw1, sw1,5);
                link3.strokeColor = '0,255,0';
                var link4 = xh.createNewLink(hsw1, sw2,5);
                link4.strokeColor = '0,255,0';
                var link5 = xh.createNewLink(hsw1, sw3,5);
                link5.strokeColor = '0,255,0';
                
                
                var link6 = xh.createNewLink(hsw1, sw4,5);
                link6.strokeColor = '0,255,0';
                var link7 = xh.createNewLink(hsw1, sw5,5);
                link7.strokeColor = '0,255,0';
                var link8 = xh.createNewLink(hsw1, sw6,5);
                link8.strokeColor = '0,255,0';
                var link9 = xh.createNewLink(hsw1, sw7,5);
                link9.strokeColor = '0,255,0';
                
                var link10 = xh.createNewLink(sw4, poc1,5);
                link10.strokeColor = '0,255,0';
                var link11 = xh.createNewLink(sw5, bridging1,5);
                link11.strokeColor = '0,255,0';
                var link12 = xh.createNewLink(sw6, dispatch1,5);
                link12.strokeColor = '0,255,0';
                var link13 = xh.createNewLink(sw7, bs1,5);
                link13.strokeColor = '0,255,0';
                
                
                //交换中心2
                var server2=xh.createNode(wwidth-600, wheight/10,"xh.png","综合应用与平台");
    			var emh2 =xh.createNode(wwidth-500, wheight/10,"emh.png","动环设备");
    			var sw21 =xh.createNode(wwidth-600, wheight/10+200,"switch.png","交换机");
    			var sw22 =xh.createNode(wwidth-500, wheight/10+200,"switch.png","交换机");
    			var sw23 =xh.createNode(wwidth-400, wheight/10+200,"switch.png","交换机");
    			
    			var hsw2 =xh.createNode(wwidth-500, wheight/2+30,"hsw.png","核心交换机");
    			
    			//var sw24 =xh.createNode(wwidth-500, wheight/2+100,"switch.png","交换机");
    			var sw25 =xh.createNode(wwidth-600, wheight/2+100,"switch.png","交换机");
    			var sw26 =xh.createNode(wwidth-500, wheight/2+100,"switch.png","交换机");
    			var sw27 =xh.createNode(wwidth-400, wheight/2+100,"switch.png","交换机");
    			
    			//var poc2 =xh.createNode(wwidth-500,wheight/2+200,"poc.png","POC系统");
                var bridging2 =xh.createNode(wwidth-600, wheight/2+200,"bridging.png","桥接系统");
                var dispatch2 =xh.createNode(wwidth-500, wheight/2+200,"dispatch.png","调度台");
                var bs2 =xh.createNode(wwidth-400, wheight/2+200,"bs.png","基站");
                
              //连线
                var link20 = xh.createNewLink(hsw1, hsw2,5);
                link20.strokeColor = '0,255,0';
                
                var link21 = xh.createNewLink(server2, sw21,5);
                link21.strokeColor = '0,255,0';
                var link22 = xh.createNewLink(emh2, sw22,5);
                link22.strokeColor = '0,255,0';
                
                var link23 = xh.createNewLink(hsw2, sw21,5);
                link23.strokeColor = '0,255,0';
                var link24 = xh.createNewLink(hsw2, sw22,5);
                link24.strokeColor = '0,255,0';
                var link25 = xh.createNewLink(hsw2, sw23,5);
                link25.strokeColor = '0,255,0';
                
                
               /* var link26 = xh.createNewLink(hsw2, sw24,5);
                link26.strokeColor = '0,255,0';*/
                var link27 = xh.createNewLink(hsw2, sw25,5);
                link27.strokeColor = '0,255,0';
                var link28 = xh.createNewLink(hsw2, sw26,5);
                link28.strokeColor = '0,255,0';
                var link29 = xh.createNewLink(hsw2, sw27,5);
                link29.strokeColor = '0,255,0';
                
               /* var link30 = xh.createNewLink(sw24, poc2,5);
                link30.strokeColor = '0,255,0';*/
                var link31 = xh.createNewLink(sw25, bridging2,5);
                link31.strokeColor = '0,255,0';
                var link32 = xh.createNewLink(sw26, dispatch2,5);
                link32.strokeColor = '0,255,0';
                var link33 = xh.createNewLink(sw27, bs2,5);
                link33.strokeColor = '0,255,0';
    		
                             
                
                //交换中心1
                

                
                $scope.emh();
               //状态监测
                emh1.addEventListener('mouseover', function(event){
              	  var html="<table>";
              	  if($scope.emhData.door==0){
              		html+="<tr style='color:#fff;background:green'><td>门禁</td><td>关闭</td></tr>";
              	  }else{
              		html+="<tr style='color:#fff;background:red'><td>门禁</td><td>打开</td></tr>";
              	  }
              	 if($scope.emhData.smoke==0){
              		html+="<tr style='color:#fff;background:green'><td>烟感</td><td>正常</td></tr>";
              	  }else{
              		html+="<tr style='color:#fff;background:red'><td>烟感</td><td>异常</td></tr>";
              	  }
              	 if($scope.emhData.red==0){
               		html+="<tr style='color:#fff;background:green'><td>红外</td><td>正常</td></tr>";
               	  }else{
               		html+="<tr style='color:#fff;background:red'><td>红外</td><td>异常</td></tr>";
               	  }
              	 if($scope.emhData.water==0){
               		html+="<tr style='color:#fff;background:green'><td>水浸</td><td>正常</td></tr>";
               	  }else{
               		html+="<tr style='color:#fff;background:red'><td>水浸</td><td>异常</td></tr>";
               	  }
              	html+="<tr><td>温度</td><td>"+$scope.emhData.temp+"</td></tr>";
          	    html+="<tr><td>湿度</td><td>"+$scope.emhData.damp+"</td></tr>";
          	    html+="<tr><td>交流电压</td><td>"+$scope.emhData.jv+"</td></tr>";
       	        html+="<tr><td>交流电流</td><td>"+$scope.emhData.ji+"</td></tr>";
       	        html+="<tr><td>直流电压</td><td>"+$scope.emhData.lv+"</td></tr>";
       	        html+="<tr><td>直流电流</td><td>"+$scope.emhData.li+"</td></tr>";
              	  html+="<table>";              	 
              	  $(".server-emh").html(html);            	  
              	  var top=event.pageY,left=event.pageX+20;
              	  var clientW=document.documentElement.clientWidth;
              	  var clientH=document.documentElement.clientHeight;
              	  if(clientW-left<200){
              		  left-=250;
              	  }
              	  if(clientH-top<250){
              		  top-=160;
              	  } 
              	  $(".server-emh").css({
              	        top: top,
              	        left: left
              	    }).show();
                });              
                emh1.addEventListener('mouseout', function(event){
              	  $(".server-emh").hide();
                });               
                $http.get("../../server/list").
        		success(function(response){
        			xh.maskHide();
        			var data = response.items;
        			var totals = response.totals;
        			var top1=wheight/10,top2=wheight/10;
   				    var left1=380,left2=wwidth-420;
   				    var a=0;var b=0;
        			for(var j=0; j<totals; j++){
        				 
        				 
        				 if(data[j].ID==1){
        				
        					/* left1+=40; 
        					 top1-=30;*/
                            if(j<4){
                        		 
                            	 left1-=80; 
                        	 }else{
                        		 top1+=60;
                        	 }
        				  
        				 
        				 
                         var vmNode=xh.createNode(left1,top1,"server1.png",data[j].name);
                         vmNode.index=j;
                         if(data[j].status!=0){
                   			 vmNode.alarm = '告警';
                   			
                   	      }else{
                   	    	  if(data[j].cpuLoad>=95 || data[j].diskResidue<10 ){
                   	    		vmNode.alarm = '警告！';
                   	    	  }
                   	      }
                         scene.add(vmNode); 
                         var link=new JTopo.Link(sw1, vmNode);
                         link.lineWidth = 1; // 线宽
                         link.bundleOffset = 60; // 折线拐角处的长度
                         link.bundleGap = 20; // 线条之间的间隔
                         if(data[j].status!=0){
                        	 link.strokeColor = '255,0,0';
                   			
                   	      }else{
                   	    	  if(data[j].cpuLoad>=95 || data[j].diskResidue<10 ){
                   	    		link.strokeColor = '255,0,0';
                   	    	  }else{
                   	    		link.strokeColor = '0,255,0';
                   	    	  }
                   	      }
                        
                         
                         scene.add(link);
                         vmNode.addEventListener('mouseover', function(event){
    	                	  var d=data[this.index];
    	                	  var html="<table>";
    	                	  html+="<tr><td>服务器名称</td><td>"+d.name+"</td></tr>";
    	                	  if(d.cpuLoad>=95){
    	                		  html+="<tr style='color:#fff;background:red;'><td>CPU使用率</td><td>"+d.cpuLoad+"%</td></tr>";
    	                	  }else{
    	                		  html+="<tr><td>CPU使用率</td><td>"+d.cpuLoad+"%</td></tr>";
    	                	  }
    	                	  
    	                	  html+="<tr><td>内存大小</td><td>"+d.memSize+"G</td></tr>";
    	                	  html+="<tr><td>剩余内存</td><td>"+d.memResidue+"G</td></tr>";
    	                	  html+="<tr><td>磁盘空间</td><td>"+d.diskSize+"G</td></tr>";
    	                	  if(d.diskResidue<10 ){
    	                		  html+="<tr style='color:#fff;background:red;'><td>剩余空间</td><td>"+d.diskResidue+"G</td></tr>";
    	               	      }else{
    	               	    	 html+="<tr><td>剩余空间</td><td>"+d.diskResidue+"G</td></tr>";
    	               	      }
    	                	  html+="<tr><td>更新时间</td><td>"+d.time+"</td></tr>";
    	                	 
    	                	  html+="<table>";
    	                	 
    	                	  $(".server-info1").html(html);
    	                	  
    	                	  var top=event.pageY,left=event.pageX+20;
    	                	  var clientW=document.documentElement.clientWidth;
    	                	  var clientH=document.documentElement.clientHeight;
    	                	  if(clientW-left<200){
    	                		  left-=250;
    	                	  }
    	                	  if(clientH-top<250){
    	                		  top-=160;
    	                	  }
    	                	  
    	                	  $(".server-info1").css({
    	                	        top: top,
    	                	        left: left
    	                	    }).show();
    	                  });
                         
    	                  vmNode.addEventListener('mouseout', function(event){
    	                	  $(".server-info1").hide();
    	                  });
                      
                       
                         }else{
                        	 if(j<10){
                        		 
                            	 left2+=80; 
                        	 }else{
                        		 top2+=60;
                        	 }
                        	 
                        	 
                        	 var vmNode=xh.createNode(left2,top2,"server1.png",data[j].name);
                             vmNode.index=j;
                             if(data[j].status!=0){
                       			 vmNode.alarm = '告警';
                       			
                       	      }else{
                       	    	  if(data[j].cpuLoad>=95 || data[j].diskResidue<10 ){
                       	    		vmNode.alarm = '警告！';
                       	    	  }
                       	      }
                             scene.add(vmNode); 
                             var link=new JTopo.Link(sw23, vmNode);
                             link.lineWidth = 1; // 线宽
                             link.bundleOffset = 60; // 折线拐角处的长度
                             link.bundleGap = 20; // 线条之间的间隔
                             if(data[j].status!=0){
                            	 link.strokeColor = '255,0,0';
                       			
                       	      }else{
                       	    	  if(data[j].cpuLoad>=95 || data[j].diskResidue<10 ){
                       	    		link.strokeColor = '255,0,0';
                       	    	  }else{
                       	    		link.strokeColor = '0,255,0';
                       	    	  }
                       	      }
                             
                             scene.add(link); 
                             vmNode.addEventListener('mouseover', function(event){
       	                	  var d=data[this.index];
       	                	  var html="<table>";
       	                	  html+="<tr><td>服务器名称</td><td>"+d.name+"</td></tr>";
       	                	  if(d.cpuLoad>=95){
       	                		  html+="<tr style='color:#fff;background:red;'><td>CPU使用率</td><td>"+d.cpuLoad+"%</td></tr>";
       	                	  }else{
       	                		  html+="<tr><td>CPU使用率</td><td>"+d.cpuLoad+"%</td></tr>";
       	                	
       	                	  }
       	                	  
       	                	  html+="<tr><td>内存大小</td><td>"+d.memSize+"G</td></tr>";
       	                	  html+="<tr><td>剩余内存</td><td>"+d.memResidue+"G</td></tr>";
       	                	  html+="<tr><td>磁盘空间</td><td>"+d.diskSize+"G</td></tr>";
       	                	  if(d.diskResidue<10 ){
       	                		  html+="<tr style='color:#fff;background:red;'><td>剩余空间</td><td>"+d.diskResidue+"G</td></tr>";
       	               	      }else{
       	               	    	 html+="<tr><td>剩余空间</td><td>"+d.diskResidue+"G</td></tr>";
       	               	      }
       	                	html+="<tr><td>更新时间</td><td>"+d.time+"</td></tr>";
       	                	 
       	                	  html+="<table>";
       	                	 
       	                	  $(".server-info1").html(html);
       	                	  
       	                	  var top=event.pageY,left=event.pageX+20;
       	                	  var clientW=document.documentElement.clientWidth;
       	                	  var clientH=document.documentElement.clientHeight;
       	                	  if(clientW-left<200){
       	                		  left-=250;
       	                	  }
       	                	  if(clientH-top<250){
       	                		  top-=160;
       	                	  }
       	                	  
       	                	  $(".server-info1").css({
       	                	        top: top,
       	                	        left: left
       	                	    }).show();
       	                  });
                            
       	                  vmNode.addEventListener('mouseout', function(event){
       	                	  $(".server-info1").hide();
       	                  });
                            
                           
                          }
        			 }
        			
        			 JTopo.layout.layoutNode(scene, sw1, true);
        			 //JTopo.layout.layoutNode(scene, serverTwoNode, true);
        		});
         
		   
		}
            $scope.serverCanvas();
            
            $(window).resize(function(){
            	$scope.serverCanvas();
            })
	/*	setInterval(function(){
			$scope.drawCanvas();
        }, 10000);*/
	
		
		/*setInterval(function(){
			$scope.refresh();
			
			}, 10000);*/ //每隔 10 秒 
		
	});
};

//创建结点
xh.createNode=function(x, y, img,text){
    var node = new JTopo.Node(text);
    node.setLocation(x, y);
    node.setImage('../../Resources/images/top/' + img, true);  
    scene.add(node);
    return node;
}
xh.createNewNode=function(x, y, w, h,text){
    var node = new JTopo.Node(text);
    node.setLocation(x, y);
    node.setSize(w, h);
    scene.add(node);
    return node;
}

//简单连线
xh.createNewLink=function(nodeA, nodeZ, text,lineType){
    var link = new JTopo.Link(nodeA, nodeZ, text);        
    link.lineWidth = 1; // 线宽
    link.bundleOffset = 60; // 折线拐角处的长度
    link.bundleGap = 20; // 线条之间的间隔
    link.textOffsetY = 3; // 文本偏移量（向下3个像素）
    link.strokeColor = '248,248,255';
   
    
    scene.add(link);
    return link;
}
//折线
xh.createNewFoldLink=function(nodeA, nodeZ, text, direction){
    var link = new JTopo.FoldLink(nodeA, nodeZ, text);
    link.direction = direction || 'horizontal';
    link.arrowsRadius = 10; //箭头大小
    link.lineWidth = 1; // 线宽
    link.bundleOffset = 60; // 折线拐角处的长度
    link.bundleGap = 20; // 线条之间的间隔
    link.textOffsetY = 2; // 文本偏移量（向下3个像素）
    link.strokeColor = '248,248,255';
    scene.add(link);
    return link;
}

xh.handler=function(event){
   /* if(event.button == 2){// 左键
        // 当前位置弹出菜单（div）
            
    }*/
	$("#contextmenu").css({
        top: event.pageY,
        left: event.pageX
    }).show();
}

/* 右键菜单处理 */    
/*$("#contextmenu a").click(function(){
    var text = $(this).text();
    
    if(text == '删除该节点'){
        scene.remove(currentNode);
        currentNode = null;
    }if(text == '撤销上一次操作'){
        currentNode.restore();
    }else{
        currentNode.save();
    }
    
    if(text == '更改颜色'){
        currentNode.fillColor = JTopo.util.randomColor();
    }else if(text == '顺时针旋转'){
        currentNode.rotate += 0.5;
    }else if(text == '逆时针旋转'){
        currentNode.rotate -= 0.5;
    }else if(text == '放大'){
        currentNode.scaleX += 0.2;
        currentNode.scaleY += 0.2;
    }else if(text == '缩小'){
        currentNode.scaleX -= 0.2;
        currentNode.scaleY -= 0.2;
    }
    $("#contextmenu").hide();
});*/

// 刷新数据
xh.drawCanvas = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.drawCanvas();

};
/*$scope.drawCanvas=function(){
	
    scene.backgroundColor='green';
    

    var cloudNode = new JTopo.Node('交换中心');
    cloudNode.setSize(30, 26);
    cloudNode.setLocation(360,300);            
    cloudNode.layout = {type: 'circle',radius: 150};
    
    scene.add(cloudNode);
    
    for(var i=0; i<2; i++){
        var node =null;
        if(i==0){
        	node = new JTopo.CircleNode("接入应用");
        }else{
        	node = new JTopo.CircleNode("调度台");
        }   
        node.fillStyle = '200,255,0';
        node.radius = 15;
        //node.setLocation(scene.width * Math.random(), scene.height * Math.random());
        if(i == 0){
            node.layout = {type: 'tree', direction: 'bottom', width: 50, height: 90};
        }else{
            node.layout = {type: 'circle', radius: 60};
        }
        scene.add(node);                                
        var link = new JTopo.Link(cloudNode, node);
        link.lineWidth = 3; // 线宽
        link.bundleOffset = 60; // 折线拐角处的长度
        link.bundleGap = 20; // 线条之间的间隔
        link.strokeColor('200,255,0')
        scene.add(link);
        
        if(i==0){
        	for(var j=0; j<6; j++){
                var vmNode = new JTopo.CircleNode('用户343435355-' + j);
                vmNode.radius = 5;
                vmNode.fillColor='220,20,60';
                vmNode.fillStyle = '255,255,0';
                vmNode.setLocation(scene.width * Math.random(), scene.height * Math.random());
                vmNode.fontColor = "255,255,0";
                vmNode.scaleY=0;
                vmNode.rotate=12
                scene.add(vmNode);                                
                scene.add(new JTopo.Link(node, vmNode));                            
            }
        }else{
        	for(var j=0; j<24; j++){
                var vmNode = new JTopo.CircleNode('dispctch' + i + '-' + j);
                vmNode.radius = 10;
                vmNode.fillStyle = '255,255,0';
                vmNode.setLocation(scene.width * Math.random(), scene.height * Math.random());
                scene.add(vmNode);                                
                scene.add(new JTopo.Link(node, vmNode));                            
            }
        }
        
        
    }
    
   
    
    
    
    JTopo.layout.layoutNode(scene, cloudNode, true);
    
    scene.addEventListener('mouseup', function(e){
        if(e.target && e.target.layout){
            JTopo.layout.layoutNode(scene, e.target, true);    
        }                
    });

}*/
xh.timeStamp=function(start,end){ 
	
	var time1=new Date(start);
	var time2=new Date(end);
	var second_time=(time2.getTime()-time1.getTime())/1000;
	
	var time = parseInt(second_time) + "秒";  
	if( parseInt(second_time )> 60){  	  
	    var second = parseInt(second_time) % 60;  
	    var min = parseInt(second_time / 60);  
	    time = min + "分" + second + "秒";  
	      
	    if( min > 60 ){  
	        min = parseInt(second_time / 60) % 60;  
	        var hour = parseInt( parseInt(second_time / 60) /60 );  
	        time = hour + "小时" + min + "分" + second + "秒";  
	  
	        if( hour > 24 ){  
	            hour = parseInt( parseInt(second_time / 60) /60 ) % 24;  
	            var day = parseInt( parseInt( parseInt(second_time / 60) /60 ) / 24 );  
	            time = day + "天" + hour + "小时" + min + "分" + second + "秒";  
	        }  
	    }        
	  
	}    
	return time;          
} 
