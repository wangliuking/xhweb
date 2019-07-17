/**
 * 资产管理
 */
if (!("xh" in window)) {
	window.xh = {};
};

var frist = 0;
var appElement = document.querySelector('[ng-controller=xhcontroller]');
xh.load = function() {
	var app = angular.module("app", []);
    app.filter('upp', function() { //可以注入依赖
        return function(text) {
            if(text=="" || text==null)
                return "";
            else
                return parseFloat(text);
        };
    });

	var pageSize = $("#page-limit").val();
    app.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.html5Mode({
            enabled: true,
            requireBase: false
        });
    }]);
	app.controller("xhcontroller", function($scope,$http,$location) {
	    var fileName = $location.search().fileName;
	    $scope.fileName = fileName;
	    var clientWidth = $(window).width();
        var clientHeight = $(window).height();
	    console.log(clientWidth+"==="+clientHeight)
	    $("#rec").attr({'width':clientWidth,'height':clientHeight*0.9});
	    var imgWidth = $("img").width();
        var imgHeight = $("img").height();
        console.log(imgWidth+"==="+imgHeight)
		/*获取申请记录表*/
		/*$http.get("../../checkCut/selectAll?start=0&limit=" + pageSize).
		success(function(response){

		});*/

        /**
		 * 发起签名
         */
        $scope.signNow = function () {
            $.ajax({
                url : '../../checkCut/signNow',
                type : 'get',
                dataType : "json",
                async : false,
                success : function(data) {
                    if (data.success) {
                        //修改person的值
						if($scope.roleId == "301"){
                            $scope.sheetData.persion1 = "sign/"+$scope.loginUser+".png";
                            console.log($scope.sheetData);
						}else if($scope.roleId == "10003"){
                            $scope.sheetData.persion2 = "sign/"+$scope.loginUser+".png";
                            console.log($scope.sheetData);
                        }else if($scope.roleId == "10002"){
                            $scope.sheetData.persion3 = "sign/"+$scope.loginUser+".png";
                            console.log($scope.sheetData);
                        }
                    } else {
                        alert(data.message);
                    }
                }
            });
        }
	});
};

/*修改核减申请表*/
xh.sealImg = function(x,y) {
    var $scope = angular.element(appElement).scope();
    $.ajax({
        url : '../../checkCut/sealImg?x='+x+"&y="+y+"&fileName="+$scope.fileName,
        type : 'GET',
        dataType : "json",
        async : false,
        cache  : false,
        success : function(data) {
            //toastr.success(data.message, '提示');
            alert("签章成功！");
            location.reload();
        },
        error : function() {
        }
    });
};