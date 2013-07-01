<head>
<link rel="stylesheet" type="text/css" href="/static/css/bootstrap.css" />
</head>
<form method="POST" action="/recipes">
Titulo: <input type="text" name="titulo" /></br>
Problema: <input type="text" name="problema" /></br>
Solução: <input type="text" name="solucao"/></br>
Autor: <input type="text" name="autor"/></br>
Dificuldade: <input type="text" name="dificuldade"/></br>
<input type="submit" class="btn btn-success" value="Criar Nova Receita"/>
<input type="button" value="Voltar" class="btn btn-inverse" onclick="JavaScript:window.location='/recipes'"/>
</form>