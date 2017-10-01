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
		<c:if test="${empty listeImages}">
			<h1 style="color: #7f8c8d; text-align: center;">
				<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> cet
				article n'a pas d'image associer
			</h1>
		</c:if>
		<p>
			<a class="btn btn-default"
				href='<c:url value="ImageController?action=afficher"><c:param name="idArticle" value="${monArticle}"/></c:url>'>
				Article associer </a>
		</p>
		<c:forEach items="${listeImages}" var="liste">
			<div>
				<c:if
					test="${liste.auteur == sessionScope.sessionUtilisateur.username}">
					<p>
						<a style="position: absolute;" class="btn btn-danger"
							href='<c:url value="/ImageController?action=delete"><c:param name="idImage" value="${liste.id}"/>
							<c:param name="idArticle" value="${liste.idArt}"/>
							</c:url>'>
							Supprimer </a>
					</p>
				</c:if>
				<img style="width: 1000px; height: 300px;"
					src="<c:url value="/afficheImage"><c:param name="idImg" value="${liste.id}"/></c:url>" />
				<div style="position: absolute; margin-top: -90px;margin-left: 400px;color: #fff;">
					<h3>${liste.titre}</h3>
					<p style="padding-left: 50px;">Ajouter par : ${liste.auteur}</p>
				</div>
			</div>

		</c:forEach>
	</div>
</body>
</html>