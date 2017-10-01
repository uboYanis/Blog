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
<title>Liste des Utilisateurs</title>
<link rel="stylesheet" type="text/css" href="./Style/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./Style/css/style.css">
<link rel="stylesheet" type="text/css"
	href="./Style/font/css/font-awesome.min.css">
</head>
<body>

	<c:import url="../Include/navbar.jsp" />
	<c:import url="../Include/sidebar.jsp" />

	<div class="col-md-9">
		<table class="table table-hover">

			<tr class="active">
				<td>id</td>
				<td>User Name</td>
				<td>role</td>
				<td>action</td>
			</tr>

			<c:forEach items="${afficher}" var="p">
				<tr>
					<td>${p.idUser}</td>
					<td>${p.username}</td>
					<td>${p.role}</td>
					<td><c:if test="${ p.role!='admin'}">
							<a class="btn btn-default"
								href='<c:url value="/UserController?action=delete"><c:param name="idUser" value="${p.idUser}"/></c:url>'>
								supprimer </a>
							<a class="btn btn-default"
								href='<c:url value="/UserController?action=update"><c:param name="idUser" value="${p.idUser}"/></c:url>'>
								changer son statut </a>
						</c:if></td>
				</tr>
			</c:forEach>
		</table>
	</div>


</body>
</html>