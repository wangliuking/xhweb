/**
 * 终端状态
 */
if (!("xh" in window)) {
	window.xh = {};
};
require.config({
	paths : {
		echarts : '../../lib/echarts'
	}
});
var background="#fff";
var frist = 0;
var appElement = document.querySelector('[ng-controller=xhcontroller]');
var data=null;
var totals=0;
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
		/*xh.loadUserStatusPie();*/
		/*获取日志信息*/
		$http.get("../../server/list").
		success(function(response){
			xh.maskHide();
			$scope.data = response.items;
			$scope.totals = response.totals;		
			
		});

		$scope.drawCanvas=function(){
			 var canvas = document.getElementById('canvas');
			 canvas.width = document.documentElement.clientWidth-22;
			 canvas.height = document.documentElement.clientHeight-38;
		        var stage = new JTopo.Stage(canvas);
		        //显示工具栏
		       /*  showJTopoToobar(stage); */
		        var scene = new JTopo.Scene();
		        stage.add(scene);
		        scene.background = '../../Resources/images/img/bg.jpg';
		        /*scene.backgroundColor='green';*/
		        

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
	                /*link.strokeColor('200,255,0')*/
	                scene.add(link);
	                
	                if(i==0){
	                	for(var j=0; j<6; j++){
		                    var vmNode = new JTopo.CircleNode('用户343435355-' + j);
		                    vmNode.radius = 5;
		                    vmNode.fillColor='220,20,60';
		                    /*vmNode.fillStyle = '255,255,0';*/
		                    /*vmNode.setLocation(scene.width * Math.random(), scene.height * Math.random());*/
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
	            
	           
	            
	            
	            
	            
	           /* for(var i=0; i<2; i++){
	               for(var j=0; j<6; j++){
	                    var vmNode = new JTopo.CircleNode('v-' + i + '-' + j);
	                    vmNode.radius = 10;
	                    vmNode.fillStyle = '255,255,0';
	                    vmNode.setLocation(scene.width * Math.random(), scene.height * Math.random());
	                    scene.add(vmNode);                                
	                    scene.add(new JTopo.Link(node, vmNode));                            
	                }
	            }*/
	           /* var currentNode = null;
	          
	            node.addEventListener('mouseup', function(event){
	                currentNode = this;
	                xh.handler(event);
	            });
	            node2.addEventListener('mouseup', function(event){
	                currentNode = this;
	                xh.handler(event);
	            });*/
	           /* stage.click(function(event){
	                if(event.button == 0){// 右键
	                    // 关闭弹出菜单（div）
	                    $("#contextmenu").hide();
	                }
	            });*/
	            JTopo.layout.layoutNode(scene, cloudNode, true);
	            
	            scene.addEventListener('mouseup', function(e){
	                if(e.target && e.target.layout){
	                    JTopo.layout.layoutNode(scene, e.target, true);    
	                }                
	            });
		   
		}
		$scope.drawCanvas();
		
	
		
		/*setInterval(function(){
			$scope.refresh();
			
			}, 10000);*/ //每隔 10 秒 
		
	});
};

xh.handler=function(event){
    if(event.button == 2){// 右键
        // 当前位置弹出菜单（div）
        $("#contextmenu").css({
            top: event.pageY,
            left: event.pageX
        }).show();    
    }
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
