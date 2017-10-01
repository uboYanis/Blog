<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<c:if test="${empty sessionScope.sessionUtilisateur}">
	<c:redirect url="/UserController?action=login"></c:redirect>
</c:if>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>ajouter un Article</title>
<link rel="stylesheet" type="text/css" href="./Style/css/bootstrap.css">

<link rel="stylesheet" type="text/css" href="./Style/css/style.css">

<link rel="stylesheet" type="text/css"
	href="./Style/font/css/font-awesome.min.css">
</head>

<c:import url="../Include/navbar.jsp" />
<c:import url="../Include/sidebar.jsp" />

<div class="col-md-9">
	<form action="<c:url value="/ArticleController?action=add"></c:url>"
		method="post" class="ajoutArticle">
		<div class="form-group">
			<label>titre</label> <input type="text" name="titre"
				class="form-control" placeholder="Le titre de l'article ..."
				value="${articleValide.titre}" />
			<c:if test="${ form.erreurs['titre']!=null}">
				<div class="erreur" style="color: #fff;">${form.erreurs['titre']}</div>
			</c:if>
		</div>
		<div class="form-group">
			<label>contenu</label>
			<textarea name="contenu" rows="10" class="form-control"
				placeholder="Le contenu de l'article ...">${articleValide.contenu}</textarea>
			<c:if test="${ form.erreurs['contenu']!=null}">
				<div class="erreur" style="color: #fff;">${form.erreurs['contenu']}</div>
			</c:if>
		</div>
		<input type="hidden" name="auteur"
			value="${ sessionScope.sessionUtilisateur.username }" /> <input
			type="submit" value="Ajouter" name="action" class="btn btn-default" />
	</form>
</div>
</body>
</html>