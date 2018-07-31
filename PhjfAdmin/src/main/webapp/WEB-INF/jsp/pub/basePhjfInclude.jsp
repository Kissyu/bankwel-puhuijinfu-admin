<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/compent/jstl/webTag-Bootstrap.tld" prefix="webTag"%>
<%@ page import="com.bankwel.phjf_admin.component.c11assistant.ServletHelper" %>
<html>
<head>
    <title>Title</title>
    <%
        String includeContextPath = request.getContextPath();
        String DukeToken = (String) request.getSession().getAttribute("DukeToken");
    %>
    <link rel="stylesheet" type="text/css" href="<%=includeContextPath%>/compent/css/bootstrap/bootstrap-cerulean-phjf.css?<%=ServletHelper.getPVersion()%>" >
    <link rel="stylesheet" type="text/css" href="<%=includeContextPath%>/compent/css/bootstrap/bootstrap-responsive.min.css?<%=ServletHelper.getPVersion()%>" >
    <link rel="stylesheet" type="text/css" href="<%=includeContextPath%>/compent/css/validation.css?<%=ServletHelper.getPVersion()%>" >
    <link rel="stylesheet" type="text/css" href="<%=includeContextPath%>/compent/ztree/css/zTreeStyle/zTreeStyle.css?<%=ServletHelper.getPVersion()%>" >


    <script>
        window.XMLHttpRequest.prototype._send = XMLHttpRequest.prototype.send;
        window.XMLHttpRequest.prototype.send = function (body) {
            if(typeof(token) != 'undefined' && token != null && token != ""){
                this.setRequestHeader("DukeToken", token);
            }
            //duke_send(body);
            window.XMLHttpRequest.prototype._send.apply(this, arguments);
            //duke_send.apply(this, arguments);
        }

        var includeContextPath = "<%=includeContextPath%>";
        var token = "<%=DukeToken%>";


        function appendToken(){
            updateForms();
            updateTags();
        }
        function updateForms() {
            // 得到页面中所有的 form 元素
            var forms = document.getElementsByTagName('form');
            for(i=0; i<forms.length; i++) {
                var url = forms[i].action;
                // 如果这个 form 的 action 值为空，则不附加 DukeToken
                if(url == null || url == "" ) continue;
                // 动态生成 input 元素，加入到 form 之后
                var e = document.createElement("input");
                e.name = "DukeToken";
                e.value = token;
                e.type="hidden";
                forms[i].appendChild(e);
            }
        }

        function updateTags() {
            var all = document.getElementsByTagName('a');
            var len = all.length;
            // 遍历所有 a 元素
            for(var i=0; i<len; i++) {
                var e = all[i];
                updateTag(e, 'href', token);
            }
        }
        function updateTag(element, attr, token) {
            var location = element.getAttribute(attr);
            if(location != null && location != '') {
                var fragmentIndex = location.indexOf('#');
                var fragment = null;
                if(fragmentIndex != -1){
                    //url 中含有只相当页的锚标记
                    fragment = location.substring(fragmentIndex);
                    location = location.substring(0,fragmentIndex);
                }
                var index = location.indexOf('?');
                if(index != -1) {
                    //url 中已含有其他参数
                    location = location + '&=DukeToken' + token;
                } else {
                    //url 中没有其他参数
                    location = location + '?DukeToken=' + token;
                }
                if(fragment != null){
                    location += fragment;
                }
                element.setAttribute(attr, location);
            }
        }
    </script>
    <script type="text/javascript" src="<%=includeContextPath%>/compent/jquery/jquery-1.9.1.min.js?<%=ServletHelper.getPVersion()%>"></script><!-- jQuery -->
    <script type="text/javascript" src="<%=includeContextPath%>/compent/jquery/haupt.js?<%=ServletHelper.getPVersion()%>"></script>
    <script type="text/javascript" src="<%=includeContextPath%>/compent/datepicker/WdatePicker.js?<%=ServletHelper.getPVersion()%>"></script>
    <script type="text/javascript" src="<%=includeContextPath%>/compent/ztree/js/jquery.ztree.core.js?<%=ServletHelper.getPVersion()%>"></script>
    <script type="text/javascript" src="<%=includeContextPath%>/compent/ztree/js/jquery.ztree.excheck.js?<%=ServletHelper.getPVersion()%>"></script>

    <script type="text/javascript" src="<%=includeContextPath%>/compent/jquery/jquery.validate.js?<%=ServletHelper.getPVersion()%>"></script><!-- jQuery -->
    <script type="text/javascript" src="<%=includeContextPath%>/compent/jquery/jquery.validate.messages_cn.js?<%=ServletHelper.getPVersion()%>"></script><!-- jQuery -->
    <script type="text/javascript" src="<%=includeContextPath%>/compent/jquery/validate.js?<%=ServletHelper.getPVersion()%>"></script><!-- jQuery -->
    <script type="text/javascript" src="<%=includeContextPath%>/compent/jquery/jquery.metadata.js?<%=ServletHelper.getPVersion()%>"></script><!-- jQuery -->
    <script type="text/javascript" src="<%=includeContextPath%>/js/common/compress.js?<%=ServletHelper.getPVersion()%>"></script><!-- jQuery -->
    <script type="text/javascript" src="<%=includeContextPath%>/ajax/commonAjax.js?<%=ServletHelper.getPVersion()%>"></script>
    <script type="text/javascript" src="<%=includeContextPath%>/js/common/provinceCityArea.js?<%=ServletHelper.getPVersion()%>"></script>


    <script>
        $(function(){
            appendToken();
        });
    </script>
</head>
<body>


</body>
</html>
