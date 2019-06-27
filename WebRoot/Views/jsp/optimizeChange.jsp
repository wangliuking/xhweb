<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>优化整改</title>
<script type="text/javascript" src="../jquery.min.js"></script>
<script type="text/javascript">
	//文档关闭前先提示用户是否保存
	
</script>

</head>
<body>

	<script type="text/javascript">
	var opt=0;
	var searName1="${sealName1}";
	var searName2="${sealName2}";
	var type="${type}";
		function Save() {
			document.getElementById("PageOfficeCtrl1").WebSave();
		
            /*if(type==1){
                if(!getSeal()){                 
                    alert("提示：你还没有签章，或者签字");
                    return;
                }else{
                    window.external.CallParentFunc("xh.check(1)");
                }
            }else{
                if(!getSeal()){
                    alert("提示：你还没有签章，或者签字");
                    return;
                }else{
                    window.external.CallParentFunc("xh.check2(2)");
                }
            }*/
		}
		function Seal1(tag) {  
			var searNameStr=searName1;
		
            try {            
            	var bRet=document.getElementById("PageOfficeCtrl1").ZoomSeal.AddSealByName(searNameStr,null,null);
                   if(bRet){
                         alert("成功！");
                       }else{
                         alert("失败！");
                       }
                   } catch(e) {}

       };
       function Seal2(tag) {  
           var searNameStr=searName2;
        
           try {            
               var bRet=document.getElementById("PageOfficeCtrl1").ZoomSeal.AddSealByName(searNameStr,null,null);
                  if(bRet){
                        alert("成功！");
                      }else{
                        alert("失败！");
                      }
                  } catch(e) {}

      };

		function PrintFile() {
			document.getElementById("PageOfficeCtrl1").ShowDialog(4);

		}
		function IsFullScreen() {
			document.getElementById("PageOfficeCtrl1").FullScreen = !document
					.getElementById("PageOfficeCtrl1").FullScreen;

		}
		function CloseFile() {
			window.external.close();

		}
		
		function DeleteSeal1(){
			var strSealName=searName1;
         
            var iCount = document.getElementById("PageOfficeCtrl1").ZoomSeal.Count;//获取加盖的印章数量
            var strTempSealName = "";
            if(iCount > 0){
                for(var i=0; i<iCount; i++){
                    strTempSealName = document.getElementById("PageOfficeCtrl1").ZoomSeal.Item(i).SealName;//获取加盖的印章名称
                    if(strTempSealName==strSealName){
                        document.getElementById("PageOfficeCtrl1").ZoomSeal.Item(i).DeleteSeal();//删除印章
                        alert("成功删除了“"+strSealName+"”");
                        break;
                    }
                }
            }else{
                alert("请先在文档中加盖印章后，再执行当前操作。");
            }
        }
		function DeleteSeal2(){
            var strSealName=searName2;
        
            var iCount = document.getElementById("PageOfficeCtrl1").ZoomSeal.Count;//获取加盖的印章数量
            var strTempSealName = "";
            if(iCount > 0){
                for(var i=0; i<iCount; i++){
                    strTempSealName = document.getElementById("PageOfficeCtrl1").ZoomSeal.Item(i).SealName;//获取加盖的印章名称
                    if(strTempSealName==strSealName){
                        document.getElementById("PageOfficeCtrl1").ZoomSeal.Item(i).DeleteSeal();//删除印章
                        alert("成功删除了“"+strSealName+"”");
                        break;
                    }
                }
            }else{
                alert("请先在文档中加盖印章后，再执行当前操作。");
            }
        }
		function check(){
			window.external.close();
			window.external.CallParentFunc("xh.checkModel()");
		}
		function BeforeBrowserClosed() {
			
			if (confirm("提示：文档如果被修改过，需要保存才能生效，确认已经保存过，并关闭文档？")) {
                window.external.close();
                return true;

            } else {

                return false;
            }
			
			
			
			
			
			
			/* if(iCount<2 && type==1){
				if (confirm("提示：你还没有签章，或者签字，确认要关闭吗")) {
	                return true;

	            } else {
	                return false;
	            }
			}else if(iCount<4 && type==2){
                if (confirm("提示：你还没有签章，或者签字，确认要关闭吗")) {
                    return true;

                } else {
                    return false;
                }
            }else{
				if(type==1){
	                window.external.CallParentFunc("xh.check(1)");
	            }else{
	                window.external.CallParentFunc("xh.check2(2)");
	            }
	            Save();
			}		 */
	    }
		function getSeal() {
            var iCount = document.getElementById("PageOfficeCtrl1").ZoomSeal.Count;//获取加盖的印章数量
            var strTempSealName = "";
            if (iCount > 0) {
                for (var i = 0; i < iCount; i++) {
                    strTempSealName = document
                            .getElementById("PageOfficeCtrl1").ZoomSeal.Item(i).SealName;//获取加盖的印章名称
                    if (strTempSealName == searName1) {
                        return true;
                        break;
                    }
                }
            } else {
                
            }
            return false;
        }
	</script>
	<div style="height:700px;width:1200px;">${pageoffice }</div>

</body>
</html>

