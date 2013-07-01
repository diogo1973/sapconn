<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/static/css/bootstrap.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recipe List</title>
</head>
<body>
<ul>
<c:forEach var="recipe" items='${recipes}'>
	<a href="/recipes/${recipe.id}">abc${recipe.titulo}</a><br/>
</c:forEach>
</ul>
<input type="button" value="Voltar" class="btn btn-inverse" onclick="JavaScript:window.location='/'"/>

</body>
</html>