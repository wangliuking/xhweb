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
scene.background = '../../Resources/images/img/bg.jpg';
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
    			var rootNode =xh.createRootNode(300, wheight/2, 30, 30,"交换设备","tree");
    			var rootNode2 =xh.createRootNode(wwidth/2+200, wheight/2, 30, 30,"交换设备","tree");
                var serverOneNode =xh.createServerOneNode(100, 100, 30, 30,"服务器状态（主）","circle","left");
                serverOneNode.fontColor = "255,255,255"
                var serverTwoNode =xh.createServerTwoNode(wwidth-200, wheight-220, 30, 30,"服务器状态（备）","circle");
                var xhNode =xh.createNewNode(100, wheight-220, 30, 30,"综合应用与平台","circle","../../Resources/images/top/xh.png");
                var xhNode2 =xh.createNewNode(wwidth-600, 120, 30, 30,"综合应用与平台","circle","../../Resources/images/top/xh.png");
                var pocNode =xh.createNewNode(wwidth/2-300,wheight-180, 30, 30,"POC系统","circle","../../Resources/images/top/poc.png");
                var emhNode =xh.createNewNode(400, 100, 30, 30,"动环设备","tree","../../Resources/images/top/emh.png","top");
                var emh_status_Node =xh.createNewNode(300, 30, 30, 30,"状态监测","circle","../../Resources/images/top/emh-s.png");
                var emh_ws_Node =xh.createNewNode(400, 30, 30, 30,"温湿度监测","circle","../../Resources/images/top/emh-t.png");
                var emh_ups_Node =xh.createNewNode(500, 30, 30, 30,"UPS监测","circle","../../Resources/images/top/emh-ups.png");
                var bridgingNode =xh.createNewNode(500, 400, 30, 30,"桥接系统","circle","../../Resources/images/top/bridging.png");
                
                
                var emhNode2 =xh.createNewNode(wwidth-300, 100, 30, 30,"动环设备","tree","../../Resources/images/top/emh.png","top");
                var emh_status_Node2 =xh.createNewNode(wwidth-400, 30, 30, 30,"状态监测","circle","../../Resources/images/top/emh-s.png");
                var emh_ws_Node2 =xh.createNewNode(wwidth-300, 30, 30, 30,"温湿度监测","circle","../../Resources/images/top/emh-t.png");
                var emh_ups_Node2 =xh.createNewNode(wwidth-200, 30, 30, 30,"UPS监测","circle","../../Resources/images/top/emh-ups.png");
                var bridgingNode2 =xh.createNewNode(wwidth-500, 400, 30, 30,"桥接系统","circle","../../Resources/images/top/bridging.png");
                
                var link = xh.createNewLink(rootNode, serverOneNode,5);
                    link.strokeColor = '0,255,0';
                var link2= xh.createNewLink(rootNode2, serverTwoNode,5);
                link2.strokeColor = '0,255,0';
                var link_xh= xh.createNewLink(rootNode, xhNode,5);
                link_xh.strokeColor = '0,255,0';
                var link_xh2= xh.createNewLink(rootNode2, xhNode2,5);
                link_xh2.strokeColor = '0,255,0';
                var link_poc= xh.createNewLink(rootNode, pocNode,5);
                link_poc.strokeColor = '0,255,0';
                
                var link_bridging= xh.createNewLink(rootNode, bridgingNode,5);
                link_bridging.strokeColor = '0,255,0';
                var link_bridging2= xh.createNewLink(rootNode2, bridgingNode2,5);
                link_bridging2.strokeColor = '0,255,0';
                var link_root2= xh.createNewLink(rootNode, rootNode2,5);
                link_root2.strokeColor = '0,255,0';
                
                
                //环控
                var link_emh= xh.createNewLink(rootNode, emhNode,5);
                link_emh.strokeColor = '0,255,0';
                var link_status= xh.createNewLink(emhNode, emh_status_Node,5);
                link_status.strokeColor = '0,255,0';
                var link_ws= xh.createNewLink(emhNode, emh_ws_Node,5);
                link_ws.strokeColor = '0,255,0';
                var link_ups= xh.createNewLink(emhNode, emh_ups_Node,5);
                link_ups.strokeColor = '0,255,0';
                
                var link_emh2= xh.createNewLink(rootNode2, emhNode2,5);
                link_emh2.strokeColor = '0,255,0';
                var link_status2= xh.createNewLink(emhNode2, emh_status_Node2,5);
                link_status2.strokeColor = '0,255,0';
                var link_ws2= xh.createNewLink(emhNode2, emh_ws_Node2,5);
                link_ws2.strokeColor = '0,255,0';
                var link_ups2= xh.createNewLink(emhNode2, emh_ups_Node2,5);
                link_ups2.strokeColor = '0,255,0';
               
                $scope.emh();
                //状态监测
                emh_status_Node.addEventListener('mouseover', function(event){
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
                emh_status_Node.addEventListener('mouseout', function(event){
              	  $(".server-emh").hide();
                });
                //温湿度
                emh_ws_Node.addEventListener('mouseover', function(event){
                	  var html="<table>";
                	  html+="<tr><td>温度</td><td>"+$scope.emhData.temp+"</td></tr>";
                	  html+="<tr><td>湿度</td><td>"+$scope.emhData.damp+"</td></tr>";
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
                emh_ws_Node.addEventListener('mouseout', function(event){
                	  $(".server-emh").hide();
                  });
                //uPS
                emh_ups_Node.addEventListener('mouseover', function(event){
                	  var html="<table>";
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
                emh_ups_Node.addEventListener('mouseout', function(event){
                	  $(".server-emh").hide();
                  });
                
                
                
                $http.get("../../server/list").
        		success(function(response){
        			xh.maskHide();
        			var data = response.items;
        			var totals = response.totals;
        			 for(var j=0; j<totals; j++){
        				 
        				 if(data[j].ID==1){
                         var vmNode = new JTopo.Node(data[j].name);
                         vmNode.index=j;
                         vmNode.setLocation(scene.width * Math.random(), scene.height * Math.random());
                         vmNode.setImage("../../Resources/images/top/server-point.png",true);
                         vmNode.layout = {type: 'circle',radius: 40};
                         if(data[j].status!=0){
                   			 vmNode.alarm = '告警';
                   			
                   	      }else{
                   	    	  if(data[j].cpuLoad>=95 || data[j].diskResidue<10 ){
                   	    		vmNode.alarm = '警告！';
                   	    	  }
                   	      }
                         scene.add(vmNode); 
                         var link=new JTopo.Link(serverOneNode, vmNode);
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
                        /* for(var k=0;k<5;k++){
                        	 var cNode = new JTopo.Node(k);
                        	 cNode.setLocation(10, 10);
                             cNode.setImage("../../Resources/images/top/dispatch-point.png",true);
                             scene.add(cNode); 
                             var link2=new JTopo.Link(vmNode, cNode);
                             link2.lineWidth = 1; // 线宽
                             link2.bundleOffset = 60; // 折线拐角处的长度
                             link2.bundleGap = 20; // 线条之间的间隔
                             scene.add(link2);
                             
                         }
                         JTopo.layout.layoutNode(scene, vmNode, true);*/
                       
                         }else{
                             var vmNode = new JTopo.Node(data[j].name);
                             vmNode.index=j;
                             vmNode.setLocation(1, scene.height * Math.random());
                             vmNode.setImage("../../Resources/images/top/server-point2.png",true);
                             vmNode.layout = {type: 'circle',radius: 40};
                             if(data[j].status!=0){
                       			 vmNode.alarm = '告警';
                       			
                       	      }else{
                       	    	  if(data[j].cpuLoad>=95 || data[j].diskResidue<10 ){
                       	    		vmNode.alarm = '警告！';
                       	    	  }
                       	      }
                             scene.add(vmNode); 
                             var link=new JTopo.Link(serverTwoNode, vmNode);
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
        			
        			 JTopo.layout.layoutNode(scene, serverOneNode, true);
        			 JTopo.layout.layoutNode(scene, serverTwoNode, true);
        		});
         
		   
		}
            $scope.serverCanvas();
	/*	setInterval(function(){
			$scope.drawCanvas();
        }, 10000);*/
	
		
		/*setInterval(function(){
			$scope.refresh();
			
			}, 10000);*/ //每隔 10 秒 
		
	});
};

//创建结点
xh.createNewNode=function(x, y, w, h, text,type,icon,dir){
    var node = new JTopo.Node(text);
    node.setLocation(x, y);
    //node.setLocation(scene.width * Math.random(), scene.height * Math.random());
    node.setSize(w, h);
    node.setImage(icon,true);
    node.dragable=true;
    node.shadow =true;
    if(type=="circle"){
    	node.layout = {type: 'circle',radius: 150};
    }else{
    	node.layout = {type: 'tree', direction: dir, width: 50, height: 70};
    }
    
    scene.add(node);
    return node;
}
xh.createRootNode=function(x, y, w, h, text,type){
    var node = new JTopo.Node(text);
    node.setLocation(x, y);
    node.setSize(w, h);
    node.shadow =true;
    node.setImage("../../Resources/images/top/switch.png",true);
    scene.add(node);
    return node;
}
xh.createUserNode=function(x, y, w, h, text,type){
    var node = new JTopo.Node(text);
    node.dragable=false;
    node.setLocation(x, y);
    node.setSize(w, h);
    node.shadow =true;
    node.setImage("../../Resources/images/top/server1.png",true);
    /*node.layout = {type: 'tree', direction: 'left', width: 50, height: 90};*/
    node.layout = {type: 'circle',radius: 100};
    scene.add(node);
    return node;
}

xh.createServerOneNode=function(x, y, w, h, text,type,dir){
    var node = new JTopo.Node(text);
    node.dragable=false;
    node.setLocation(x, y);
    node.setSize(w, h);
    node.shadow =true;
    node.setImage("../../Resources/images/top/server1.png",true);
    if(type=="circle"){
    	 node.layout = {type: 'circle',radius: 90};
    }else{
    	node.layout = {type: 'tree', direction: dir, width: 50, height: 90};
    }
   
    scene.add(node);
    return node;
}
xh.createServerTwoNode=function(x, y, w, h, text,type){
    var node = new JTopo.Node(text);
    node.dragable=false;
    node.setLocation(x, y);
    node.setSize(w, h);
    node.shadow =true;
    node.setImage("../../Resources/images/top/server2.png",true);
    if(type=="circle"){
   	 node.layout = {type: 'circle',radius: 90};
    }else{
   	node.layout = {type: 'tree', direction: 'bottom', width: 50, height: 90};
   }
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
