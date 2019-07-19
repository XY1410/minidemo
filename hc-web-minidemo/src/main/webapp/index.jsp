<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.gzhc365.common.constans.CommonConstans"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
this is hc-web
  <%
    HttpSession s = request.getSession(); 
    %>
    你的微信用户openId是<%=JSONObject.toJSON(s.getAttribute(CommonConstans.USER_WECHAT_OPENID_KEY_IN_SESSION))%>
</body>
</html>