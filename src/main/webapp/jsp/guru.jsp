<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function(){
        $("#guruTable").jqGrid()({
            url: '${pageContext.request.contextPath}/guru/showAllGuru',
            datatype: "json",
            // 时间格式的处理在后台进行
            colNames: ['ID', '名字', '图片', '状态','昵称','操作'],
            colModel: [
                {name: 'id', align: "center", hidden: true},
                {name: 'name', align: "center", editable: true, editrules: {required: true}},
                {name: 'photo', align: "center", formatter: function (data) {
                        return "<img style='width: 180px;height: 80px' src='.." + data + "'>"
                    }, editable: true, edittype: "file", editoptions: {enctype: "multipart/form-data"}
                },
                {
                    name: 'status',
                    align: "center",
                    formatter: function (data) {
                        if (data == 1) {
                            return "展示";
                        } else return "冻结";
                    },
                    editable: true,
                    editrules: {required: true},
                    edittype: "select",
                    editoptions: {value: "1:展示;2:冻结"}
                },
                {name: 'nickName', align: "center",editable:true ,editrules:{required:true}},
                {
                    name:'option',
                    formatter:function (cellvalue, options, rowObject) {
                        var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('"+rowObject.id+"')\">修改</button>&nbsp;&nbsp;";
                        button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"del('"+rowObject.id+"')\">删除</button>";
                        return button;
                    }
                }
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#guruPage',
            sortname: 'id',
            mtype: "post",
            viewrecords: true,
            sortorder: "desc",
            autowidth: true,
            multiselect: true,
            styleUI: "Bootstrap",
            height: "500px",
            editurl: "${pageContext.request.contextPath}/article/insertGuru"
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
    })
</script>
<div class="page-header">
    <h4>用户管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>
</ul>
<div class="panel">
    <table id="guruTable"></table>
    <div id="guruPage" style="height: 50px"></div>
</div>