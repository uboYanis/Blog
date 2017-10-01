<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<c:if test="${empty sessionScope.sessionUtilisateur}">
	<c:redirect url="/UserController?action=login"></c:redirect>
</c:if>
<html>
<head>

<title>Ajouter une Image</title>
<link rel="stylesheet" type="text/css" href="./Style/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./Style/css/style.css">
<link rel="stylesheet" type="text/css"
	href="./Style/font/css/font-awesome.min.css">
</head>
<body>
	<c:import url="../Include/navbar.jsp" />
	<c:import url="../Include/sidebar.jsp" />
	<div class="col-md-9">
		<form action="<c:url value="/ImageController?action=add"></c:url>"
			method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label>Titre </label> <input name="titre" type="text"
					class="form-control" placeholder="Titre ..."
					value="${ImageValide.titre}" />
				<c:if test="${ form.erreurs['titre']!=null}">
					<div class="erreur" style="color: #fff;">${form.erreurs['titre']}</div>
				</c:if>
			</div>
			<div class="form-group">
				<label class="btn btn-success btn-file">ajouter une photo <input
					name="fic" type="file" style="display: none;" />
				</label>
			</div>
			<input name="auteur" type="hidden"
				value="${sessionScope.sessionUtilisateur.username}" /> <input
				name="id" type="hidden" value="${article}" /> <input
				type="submit" value="Ajouter" class="btn btn-default" />
		</form>
	</div>

</body>
</html>