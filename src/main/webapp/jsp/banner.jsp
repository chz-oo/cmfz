<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }"></c:set>
<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url : "${path}/banner/fen",
                datatype : "json",
                colNames : [ 'id', '标题', '图片','超链接','时间','描述','状态'],
                colModel : [
                    //hidder:true 隐藏    align:"center" 局中   editrules:{required:true} 设置必填
                    //editable:true 添加修改功能有这个列
                    {name : 'id',hidder:true},
                    {name : 'title',align:"center",editable: true,editrules:{required:true}},
                    {name : 'url',align:"center",formatter:function (data) {
                            return "<img style='width: 180px;height: 80px' src='.."+data+"'>"
                        },editable: true,edittype:"file",editoptions: {enctype:"multipart/form-data"}},
                    {name : 'href',align:"center",editable:true},
                    {name : 'createDate',align:"center",editable:true,editrules:{required:true},edittype: "date"},
                    {name : 'desccc',align:"center",editrules:{required:true},editable:true},
                    {name : 'status',align:"center",formatter:function (data) {
                            if (data=="1"){
                                return "展示";
                            } else {
                                return "冻结";
                            }
                        },editable:true,editrules:{required:true},edittype:"select",editoptions: {value:"1:展示;2:冻结"}}
                ],
                rowNum:2,// 每页显示的行数
                rowList:[5, 10, 15], // 行数的数组
                pager:"#bannerpage",//分页
                sortname : 'id',
                mtype : "post",//限定请求方式
                viewrecords : true,//是否要显示总记录数
                sortorder : "desc",
                caption : "轮播图",
                autowidth:true,//自动调整表格宽度
                multiselect:true,//多选框
                styleUI:"Bootstrap",//样式
                height:200,//表格高度，可以是数字，像素值或者百分比
                editurl:"${path}/banner/function"
            });
        $("#bannerTable").jqGrid('navGrid', '#bannerpage', {edit : true,add : true,del : true,edittext:"修改",addtext:"添加",deltext:"删除"},
            //指定修改  添加   删除  前后事件
            {
                closeAfterEdit: true ,//事件完成后框关闭
            },{
                closeAfterAdd: true,
                //数据库添加轮播图后  进行上传  上传完成后需更改url路径
                //需要获取添加轮播图的id
                afterSubmit:function (response,postData) {
                    var bannerid = response.responseJSON.BannerId;
                    $.ajaxFileUpload({
                        //指定上传路径
                        url: "${path}/banner/uploadBanner",
                        type: "post",
                        datatype: "json",
                        //把要添加图片的id发送至controller
                        data: {bannerid:bannerid},
                        //指定上传的input框id
                        fileElementId:"url",
                        success: function (data) {
                            $("#bannerTable").trigger("reloadGrid");
                        }
                    });
                    //防止页面报错
                    return postData;
                }
            },{
                closeAfterDel: true,
            });
    });

    /*
    * 导出按钮的单击事件
    */
    function DaoChu() {
        $.ajax({
            url:"${path}/banner/chuEasyExcel",
            type:"post",
            datatype:"json"
        })

    }

    /*
    * 导入按钮的单击事件   弹出模态框
    */
    function DaoRu() {
        $("#myModal2").modal("show");
    }

    /*
    * 点击提交  发Ajax  把文件名字给功能
    */
    function bbb() {
        $.ajaxFileUpload({
            url:"${path}/banner/ruEasyExcel",
            type:"post",
            datatype:"json",
            fileElementId: "inputt",
            success: function (data) {

            }
        })
    }
</script>

<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>
    <li><a onclick="DaoChu()">导出轮播图信息</a></li>
    <li><a onclick="DaoRu()">导入轮播图信息</a></li>
    <li><a>Excel模板下载</a></li>
</ul>
<div class="panel">
    <table id="bannerTable"></table>
    <div id="bannerpage" style="height: 50px"></div>
</div>

