<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>inscription</title>
<link rel="stylesheet" type="text/css" href="./Style/css/bootstrap.css">

<link rel="stylesheet" type="text/css" href="./Style/css/style.css">

<link rel="stylesheet" type="text/css"
	href="./Style/font/css/font-awesome.min.css">
</head>
<body class="Authentification container">
	<a href='<c:url value="/ArticleController?action=list"></c:url>'> <i
		class="fa fa-reply fa-2x" aria-hidden="true"
		style="color: #fff; margin-top: 20px;"> Accueil </i>
	</a>
	<c:if test="${error!=null}">
		<div class="alert alert-danger">
			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			<strong>erreur!</strong> ${error}.
		</div>
	</c:if>

	<form action="<c:url value="/UserController?action=add"></c:url>"
		method="post" class="login">
		<h2>Inscription</h2>
		<div class="form-group row">
			<input type="text" name="username" class="form-control"
				placeholder="UserName" value="${user.username}" />
			<c:if test="${ form.erreurs['username']!=null}">
				<div class="erreur" style="color: #fff;">${form.erreurs['username']}</div>
			</c:if>

		</div>
		<div class="form-group row">
			<input type="password" name="password" class="form-control"
				placeholder="Password" value="${user.password}" />
			<c:if test="${ form.erreurs['password']!=null}">
				<div class="erreur" style="color: #fff;">${form.erreurs['password']}</div>
			</c:if>

		</div>
		<input type="hidden" name="role" value="membre" /> <input
			type="submit" name="action" value="s'inscrire"
			class="btn btn-primary" />
	</form>
	<!-- jquery -->
	<script type="text/javascript" src="./Style/js/jq.js"></script>
	<!-- bootstrap -->
	<script type="text/javascript" src="./Style/js/bootstrap.js"></script>

</body>
</html>