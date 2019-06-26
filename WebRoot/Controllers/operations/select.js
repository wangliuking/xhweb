if (!("xh" in window)) {
	window.xh = {};
};
var frist = 0;
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

	app.controller("xhcontroller", function($scope, $http) {

		$scope.select_workcontact=function(){
			$http.get("../../select/workcontact").success(function(response) {
				$scope.workcontact_data = response.items;
				$scope.workcontact_data_size = response.items.length;
			});
		}
		$scope.workcontact_del = function(index) {
			swal({
				title : "提示",
				text : "确定要删除吗？",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定",
				cancelButtonText : "取消"
			/*
			 * closeOnConfirm : false, closeOnCancel : false
			 */
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
						url : '../../select/workcontact_del',
						type : 'post',
						dataType : "json",
						data : {
							name : $scope.workcontact_data[index].name
						},
						async : false,
						success : function(data) {
							if (data.success) {
								toastr.success(data.message, '提示');
								$scope.refresh();

							} else {
								toastr.success(data.message, '提示');
							}
						},
						error : function() {
							$scope.refresh();
						}
					});
				}
			});
		};
		$scope.refresh = function() {
			$scope.select_workcontact();
		};
		$scope.select_workcontact();
		var ss="03,04,07,11,17,20,30,33,35,41,43,44,45,48,50,54,58,60,62,79"
		console.log("ddd->"+JSON.stringify(dyeResult(ss)));
	
	});
};
//刷新数据
xh.refresh = function() {
	var $scope = angular.element(appElement).scope();
	// 调用$scope中的方法
	$scope.refresh();
};
/* 添加 */
xh.workcontact_add = function(name) {
	var $scope = angular.element(appElement).scope();
	$.ajax({
		url : '../../select/workcontact_add',
		type : 'POST',
		dataType : "json",
		async : true,
		data:{
			name:name
		},
		success : function(data) {
			if (data.success) {
				$('#workcontact-add').modal('hide');
				xh.refresh();
				toastr.success(data.message, '提示');
			} else {
				swal({
					title : "提示",
					text : data.message,
					type : "error"
				});
			}
		},
		error : function() {
		}
	});
};
function dyeResult(result){
	var mapid="83"
	var GTYPE=28
    if (GTYPE != 3)
        result = result.split(',');

    switch(GTYPE){
        
        case 28:
        case 110:
            if(mapid == 5){
             }else{
                var returnData = {},
                    nos = [],
                    first = [],
                    second = [],
                    third =[],
                    first_sum = 0,
                    second_sum = 0,
                    third_sum = 0,
                    first_value,
                    second_value,
                    third_value;

                $.each(result, function(i,o){
                    var ii = i+1;
                    if(ii%3 == 2 && ii <= 19 && ii > 1){
                        nos[i] = '<i style="color:red">' + o + '</i>';
                        first.push(nos[i]);
                        first_sum += Number(o);
                    }else if(ii%3 == 0 && ii <= 19 && ii > 1){
                        nos[i] = '<i style="color:green">' + o + '</i>';
                        second.push(nos[i]);
                        second_sum += Number(o);
                    }else if(ii%3 == 1 && ii <= 19 && ii > 1){
                        nos[i] = '<i style="color:orange">' + o + '</i>';
                        third.push(nos[i]);
                        third_sum += Number(o);
                    }else{
                        nos[i] = '<i style="color:gray">' + o + '</i>';
                    }

                })

                /*first_value = getTail(first_sum);
                second_value = getTail(second_sum);
                third_value = getTail(third_sum);*/
                first_value = first_sum;
                second_value = second_sum;
                third_value = third_sum;
                returnData['first_value'] = first_value;
                returnData['second_value'] = second_value;
                returnData['third_value'] = third_value;
                returnData['win'] = first_value + second_value + third_value;
            }
            break;
       
    }
    return returnData;

}
function dyeResultss(result){
	var mapid="83"
	var GTYPE=28
    if (GTYPE != 3)
        result = result.split(',');

    switch(GTYPE){
        case 2:
            var returnData = {};
            if (Number(result[0]) > Number(result[9]))
                returnData['win'] = 1;
            else
                returnData['win'] = 2;
            break;
        case 3:
            var returnData = {},

                banker = 0,
                player = 0;
            $.each(result.banker, function(i,o){
                var tmp = Number(o.substr(0, o.length -1));
                banker = banker + (tmp >= 10 ? 0 : tmp);

            })
            $.each(result.player, function(i,o){
                var tmp = Number(o.substr(0, o.length -1));
                player = player + (tmp >= 10 ? 0 : tmp);
            })
            banker = 9 - (banker %10);
            player = 9 - (player % 10);
            if (banker == player)
                returnData['win'] = 3;
            if (banker > player)
                returnData['win'] = 2;
            if (banker  < player)
                returnData['win'] = 1;
            break;
        case 10:
            var returnData = {},
                nos = [],
                first = [],
                first_sum = 0,
                first_value;
            if(mapid == 1 || mapid == 19 || mapid==63)
            {
                returnData['first_value'] = result[0];
                returnData['win'] = result[0];
            }
            else if (mapid == 17 || mapid == 61)
            {
                var idx =  Number(result[10]) % 10 == 0 ? 10 : Number(result[10]) % 10;
                idx = idx -1;
                returnData['first_value'] = Number(result[idx]);
                returnData['win'] = result[idx];
            }
            else
            {
                $.each(result, function(i,o){
                    var ii = i+1;
                    if(ii >= 1 && ii <= 7){
                        nos[i] = '<i style="color:red">' + o + '</i>';
                        first.push(nos[i]);
                        first_sum += Number(o);
                    }else{
                        nos[i] = '<i style="color:gray">' + o + '</i>';
                    }
                })
                returnData['nos'] = nos.join(' ');
                returnData['first'] = first.join(',');
                returnData['first_sum'] = first_sum;
                first_value = getTail(first_sum) + 1;
                returnData['first_value'] = first_value;
                returnData['win'] = first_value;
            }
            break;
        case 11:
        case 17:
            if(mapid == 2 || mapid == 7){
                var returnData = {};
                returnData['first_value'] = result[0];
                returnData['second_value'] = result[1];
                returnData['win'] = Number(result[0]) + Number(result[1]);

            }else{
                var returnData = {},
                    nos = [],
                    first = [],
                    second = [],
                    first_sum = 0,
                    second_sum = 0,
                    first_value,
                    second_value;
                $.each(result, function(i,o){
                    var ii = i+1;
                    if(ii%3 == 1 && ii <= 18){
                        nos[i] = '<i style="color:red">' + o + '</i>';
                        first.push(nos[i]);
                        first_sum += Number(o);
                    }else if(ii%3 == 0 && ii <= 18){
                        nos[i] = '<i style="color:green">' + o + '</i>';
                        second.push(nos[i]);
                        second_sum += Number(o);
                    }else{
                        nos[i] = '<i style="color:gray">' + o + '</i>';
                    }
                })
                returnData['nos'] = nos.join(' ');
                returnData['first'] = first.join(',');
                returnData['second'] = second.join(',');
                returnData['first_sum'] = first_sum;
                returnData['second_sum'] = second_sum;
                first_value = first_sum % 6 + 1;
                second_value = second_sum % 6 + 1;
                returnData['first_value'] = first_value;
                returnData['second_value'] = second_value;
                returnData['win'] = first_value + second_value;
            }
            break;
        case 16:
            if(mapid == 3){
                var returnData = {};
                returnData['first_value'] = Number(result[0]);
                returnData['second_value'] = Number(result[1]);
                returnData['third_value'] = Number(result[2]);
                returnData['win'] = returnData.first_value + returnData.second_value + returnData.third_value;
            }else{
                var returnData = {},
                    nos = [],
                    first = [],
                    second = [],
                    third =[],
                    first_sum = 0,
                    second_sum = 0,
                    third_sum = 0,
                    first_value,
                    second_value,
                    third_value;
                $.each(result, function(i,o){
                    var ii = i+1;
                    if(ii%3 == 1 && ii <= 18){
                        nos[i] = '<i style="color:red">' + o + '</i>';
                        first.push(nos[i]);
                        first_sum += Number(o);
                    }else if(ii%3 == 2 && ii <= 18){
                        nos[i] = '<i style="color:green">' + o + '</i>';
                        second.push(nos[i]);
                        second_sum += Number(o);
                    }else if(ii%3 == 0 && ii <= 18){
                        nos[i] = '<i style="color:orange">' + o + '</i>';
                        third.push(nos[i]);
                        third_sum += Number(o);
                    }else{
                        nos[i] = '<i style="color:gray">' + o + '</i>';
                    }
                })
                returnData['nos'] = nos.join(' ');
                returnData['first'] = first.join(',');
                returnData['second'] = second.join(',');
                returnData['third'] = third.join(',');
                returnData['first_sum'] = first_sum;
                returnData['second_sum'] = second_sum;
                returnData['third_sum'] = third_sum;
                first_value = first_sum % 6 + 1;
                second_value = second_sum % 6 + 1;
                third_value = third_sum % 6 + 1;
                returnData['first_value'] = first_value;
                returnData['second_value'] = second_value;
                returnData['third_value'] = third_value;
                returnData['win'] = first_value + second_value + third_value;
            }
            break;
        case 28:
        case 110:
            if(mapid == 5){
                var returnData = {};
                returnData['first_value'] = Number(result[0])
                returnData['second_value'] = Number(result[1]);
                returnData['third_value'] = Number(result[2]);
                returnData['win'] = returnData.first_value + returnData.second_value + returnData.third_value;
            }else{
                var returnData = {},
                    nos = [],
                    first = [],
                    second = [],
                    third =[],
                    first_sum = 0,
                    second_sum = 0,
                    third_sum = 0,
                    first_value,
                    second_value,
                    third_value;

                $.each(result, function(i,o){
                    var ii = i+1;
                    if(mapid == 8 || mapid == 33 || mapid == 27){//铔嬭泲28
                        if(ii >= 1 && ii < 7){
                            nos[i] = '<i style="color:red">' + o + '</i>';
                            first.push(nos[i]);
                            first_sum += Number(o);
                        }else if(ii >= 7 && ii < 13){
                            nos[i] = '<i style="color:green">' + o + '</i>';
                            second.push(nos[i]);
                            second_sum += Number(o);
                        }else if(ii >= 13 && ii < 19){
                            nos[i] = '<i style="color:orange">' + o + '</i>';
                            third.push(nos[i]);
                            third_sum += Number(o);
                        }else{
                            nos[i] = '<i style="color:gray">' + o + '</i>';
                        }
                    }else{
                        if(ii%3 == 2 && ii <= 19 && ii > 1){
                            nos[i] = '<i style="color:red">' + o + '</i>';
                            first.push(nos[i]);
                            first_sum += Number(o);
                        }else if(ii%3 == 0 && ii <= 19 && ii > 1){
                            nos[i] = '<i style="color:green">' + o + '</i>';
                            second.push(nos[i]);
                            second_sum += Number(o);
                        }else if(ii%3 == 1 && ii <= 19 && ii > 1){
                            nos[i] = '<i style="color:orange">' + o + '</i>';
                            third.push(nos[i]);
                            third_sum += Number(o);
                        }else{
                            nos[i] = '<i style="color:gray">' + o + '</i>';
                        }
                    }

                })

                returnData['nos'] = nos.join(' ');
                returnData['first'] = first.join(',');
                returnData['second'] = second.join(',');
                returnData['third'] = third.join(',');
                returnData['first_sum'] = first_sum;
                returnData['second_sum'] = second_sum;
                returnData['third_sum'] = third_sum;
                first_value = getTail(first_sum);
                second_value = getTail(second_sum);
                third_value = getTail(third_sum);
                returnData['first_value'] = first_value;
                returnData['second_value'] = second_value;
                returnData['third_value'] = third_value;
                returnData['win'] = first_value + second_value + third_value;
            }
            break;
        case 111:
            {
                var returnData = {},
                    nos = [],
                    first = [],
                    second = [],
                    third =[],
                    first_sum = 0,
                    second_sum = 0,
                    third_sum = 0,
                    first_value,
                    second_value,
                    third_value;

                $.each(result, function(i,o){
                    var ii = i+1;
                    if(mapid == 84){//铔嬭泲28
                        if(ii >= 1 && ii < 7){
                            nos[i] = '<i style="color:red">' + o + '</i>';
                            first.push(nos[i]);
                            first_sum += Number(o);
                        }else if(ii >= 7 && ii < 13){
                            nos[i] = '<i style="color:green">' + o + '</i>';
                            second.push(nos[i]);
                            second_sum += Number(o);
                        }else if(ii >= 13 && ii < 19){
                            nos[i] = '<i style="color:orange">' + o + '</i>';
                            third.push(nos[i]);
                            third_sum += Number(o);
                        }else{
                            nos[i] = '<i style="color:gray">' + o + '</i>';
                        }
                    }else{
                        if(ii%3 == 2 && ii <= 19 && ii > 1){
                            nos[i] = '<i style="color:red">' + o + '</i>';
                            first.push(nos[i]);
                            first_sum += Number(o);
                        }else if(ii%3 == 0 && ii <= 19 && ii > 1){
                            nos[i] = '<i style="color:green">' + o + '</i>';
                            second.push(nos[i]);
                            second_sum += Number(o);
                        }else if(ii%3 == 1 && ii <= 19 && ii > 1){
                            nos[i] = '<i style="color:orange">' + o + '</i>';
                            third.push(nos[i]);
                            third_sum += Number(o);
                        }else{
                            nos[i] = '<i style="color:gray">' + o + '</i>';
                        }
                    }

                })

                returnData['nos'] = nos.join(' ');
                returnData['first'] = first.join(',');
                returnData['second'] = second.join(',');
                returnData['third'] = third.join(',');
                returnData['first_sum'] = first_sum;
                returnData['second_sum'] = second_sum;
                returnData['third_sum'] = third_sum;
                first_value = getTail(first_sum);
                second_value = getTail(second_sum);
                third_value = getTail(third_sum);
                returnData['first_value'] = first_value;
                returnData['second_value'] = second_value;
                returnData['third_value'] = third_value;
                returnData['win'] = first_value + second_value + third_value;
                var numberArray = [first_value, second_value, third_value];
                var win_2 = verdictBSDBZ(numberArray);
                returnData['win_2'] = win_2;
            }
            break;
        case 5:
            if(mapid == 6){
                var returnData = {};
                returnData['first_value'] = Number(result[0]);
                returnData['second_value'] = Number(result[1]);
                returnData['third_value'] = Number(result[2]);
                returnData['win'] = verdictBSDBZ(result);
            }else{
                var returnData = {},
                    nos = [],
                    first = [],
                    second = [],
                    third =[],
                    first_sum = 0,
                    second_sum = 0,
                    third_sum = 0,
                    first_value,
                    second_value,
                    third_value;

                $.each(result, function(i,o){
                    var ii = i+1;
                    if(mapid == 9){//铔嬭泲36
                        if(ii >= 1 && ii < 7){
                            nos[i] = '<i style="color:red">' + o + '</i>';
                            first.push(nos[i]);
                            first_sum += Number(o);
                        }else if(ii >= 7 && ii < 13){
                            nos[i] = '<i style="color:green">' + o + '</i>';
                            second.push(nos[i]);
                            second_sum += Number(o);
                        }else if(ii >= 13 && ii < 19){
                            nos[i] = '<i style="color:orange">' + o + '</i>';
                            third.push(nos[i]);
                            third_sum += Number(o);
                        }else{
                            nos[i] = '<i style="color:gray">' + o + '</i>';
                        }
                    }else{
                        if(ii%3 == 2 && ii <= 19 && ii > 1){
                            nos[i] = '<i style="color:red">' + o + '</i>';
                            first.push(nos[i]);
                            first_sum += Number(o);
                        }else if(ii%3 == 0 && ii <= 19 && ii > 1){
                            nos[i] = '<i style="color:green">' + o + '</i>';
                            second.push(nos[i]);
                            second_sum += Number(o);
                        }else if(ii%3 == 1 && ii <= 19 && ii > 1){
                            nos[i] = '<i style="color:orange">' + o + '</i>';
                            third.push(nos[i]);
                            third_sum += Number(o);
                        }else{
                            nos[i] = '<i style="color:gray">' + o + '</i>';
                        }
                    }

                })

                returnData['nos'] = nos.join(' ');
                returnData['first'] = first.join(',');
                returnData['second'] = second.join(',');
                returnData['third'] = third.join(',');
                returnData['first_sum'] = first_sum;
                returnData['second_sum'] = second_sum;
                returnData['third_sum'] = third_sum;
                first_value = getTail(first_sum);
                second_value = getTail(second_sum);
                third_value = getTail(third_sum);
                returnData['first_value'] = first_value;
                returnData['second_value'] = second_value;
                returnData['third_value'] = third_value;
                var numberArray = [first_value, second_value, third_value];
                var win = verdictBSDBZ(numberArray);
                returnData['win'] = win;
            }
            break;
    }
    return returnData;

}
function getTail(num){
    var num = num + '',
        len = num.length,
        tail = num[len-1];
    tail = Number(tail);

    return tail;

}

