<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>登录界面</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript">
        /*
            1. 在提交按钮上调用方法 如 login();
            2. 在script中声明login方法 function login(){}
            3. 使用ajax技术发送异步请求
            4. 处理返回结果
         */
       function login() {
           $.ajax({
               url:"${pageContext.request.contextPath}/admin/login",
               type:"post",
               datatype:"json",
               // 传递表单数据时,通常使用选择器选中该表单,使用serialize()序列化方法,将表单数据全部提交
               data:$("#loginForm").serialize(),
               success:function (data) {
                   if (data.status==200){
                       location.href="${pageContext.request.contextPath}/jsp/main.jsp";
                   }else {
                       alert(data.msg);
                   }
               }
           })
       }
        function changeImage(){
            var imgobj=document.getElementById("imgg");
            imgobj.src="${pageContext.request.contextPath }/admin/yzm?zz="+Math.random();
        }
    </script>
</head>
<body style=" background: url(../img/05.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <%-- 验证码 --%>
            <div class="form-group">
                <input type="text" name="clientCode" />
                <a id="vcodeImgWrap" href="javascript:void(0);">
                    <img id="imgg" src="${pageContext.request.contextPath}/admin/yzm" onClick="changeImage()">
                    <a href="javascript:void(0)" onclick="changeImage()">看不清,换一张</a>
                </a>
                <span id="spn_vcode_ok" class="icon_yes pin_i" style="display: none;"></span>
                <span id="J_tipVcode" class="cue warn"></span>
            </div>

            <span id="msg"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
            </div>
        </div>
        </form>
    </div>
</div>
</body>
</html>
