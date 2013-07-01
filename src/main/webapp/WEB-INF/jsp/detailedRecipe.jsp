<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/static/css/bootstrap.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recipe ${recipe.externalId}</title>
</head>
<body>
	<h1>${recipe.title}</h1>
	<b>Problema:</b>
	<p>${recipe.problem}</p>
	<b>Solução:</b>
	<p>${recipe.solution}</p>
	<b>Autor:</b>
	<p>${recipe.author}</p>
	<b>Data:</b>
	<p>${recipe.creationTimestamp}</p>


<input type="button" value="Voltar" class="btn btn-inverse" onclick="JavaScript:window.location='/recipes'"/>
<input type="button" value="Eliminar Receita" class="btn btn-danger" href="/lists/${recipe.externalId}/delete"/>


</body>
</html>