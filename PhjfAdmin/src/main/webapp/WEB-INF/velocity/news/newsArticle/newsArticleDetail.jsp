<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/compent/jstl/webTag-Bootstrap.tld" prefix="webTag"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>系统</title>
    <jsp:include page="/WEB-INF/jsp/pub/basePhjfInclude.jsp" flush="true"/>
    <style>
        .mainContent{
            text-shadow: none;
            padding:0 3%;
        }
        .newsTitle{
            padding:0.8rem 0 1.2rem 0;
        }
        .newsTitle h5{
            color: #2a2929;
            font-size:1.2rem;
        }
        .sourceInfo{
            color: #999;
            font-size:0.7rem;
            padding-top:0.5rem;
        }
        .sourceInfo span{
            margin-right:0.5rem;
        }
        .contentBox{
            color: #646464;
            font-size:0.8rem;
            line-height:1.5rem;
        }
    </style>
    <script>
        $(function(){
            $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            window.onresize = function(){
                $(".container-fluid").animate({height:(document.documentElement.clientHeight - 10)})
            };
        });
    </script>

</head>
<body>
<div class="container-fluid" style="height: 650px; overflow-y: auto;">
    <!-- 面包屑导航  start -->
    <div class="dreadcount">
        <span class=mrl14><i class="icon-list icon-red"></i>新闻管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>新闻文章管理 </span><span class="divider">/</span>
        <span><i class="icon-list icon-red"></i>预览</span>
    </div>
    <!-- 面包屑导航  end -->
    <!-- 添加信息数据区 start -->
    <div class="row-fluid">
        <form id="mainForm" class="form-horizontal alert alert-info fade in span12">
            <div class="mainContent">
                <div class="newsTitle">
                    <h5>${data.title}</h5>
                    <div class="sourceInfo">
                        <span>来自于：${data.source}</span>
                        <span>${data.publish_date}</span>
                    </div>
                </div>
                <div class="contentBox">
                    ${data.content}
                </div>
            </div>
            <div class="row" align="right">
                <a class="btn btn-danger" href="/phjfht/api/v1/news/goNewsArticleQueryPage"><i class="icon-share-alt icon-white"></i>返回列表</a>
            </div>
        </form>
    </div>
    <!-- 数据区 end -->
</div>
<!-- 		底部高度填充块 -->
<div class="zeoBottom"></div>
<!-- 		底部高度填充块 结束-->
</body>

</html>
