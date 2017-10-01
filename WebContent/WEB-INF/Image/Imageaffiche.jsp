<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
<head>
<title>Article avec son illustration</title>
<!-- bootstrap.css -->
<link rel="stylesheet" type="text/css" href="./Style/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="./Style/css/style.css">
<link rel="stylesheet" type="text/css"
	href="./Style/font/css/font-awesome.min.css">
</head>
<body class="row">
	<c:import url="../Include/navbar.jsp" />
	<c:import url="../Include/sidebar.jsp" />


	<c:if test="${!empty sessionScope.sessionUtilisateur}">
		<div class="col-md-9">
	</c:if>
	<c:if test="${empty sessionScope.sessionUtilisateur}">
		<div class="container">
	</c:if>
	<div class="btn-group">
		<c:if
			test="${article.auteur == sessionScope.sessionUtilisateur.username}">

			<a class="btn btn-danger"
				href='<c:url value="/ArticleController?action=delete"><c:param name="idArticle" value="${article.idArticle}"/></c:url>'>
				Supprimer cet Article </a>

		</c:if>
		<c:if
			test="${article.auteur == sessionScope.sessionUtilisateur.username}">

			<a class="btn btn-success"
				href='<c:url value="/ArticleController?action=edit"><c:param name="idArticle" value="${article.idArticle}"/></c:url>'>
				Modifier cet Article </a>

		</c:if>
		<c:if test="${!empty sessionScope.sessionUtilisateur}">

			<a class="btn btn-info"
				href='<c:url value="/ImageController?action=add"><c:param name="idArticle" value="${article.idArticle}"/></c:url>'>
				Ajouter une Image </a>

		</c:if>
		<c:if test="${!empty sessionScope.sessionUtilisateur}">

			<a class="btn btn-default"
				href='<c:url value="/ImageController?action=liste"><c:param name="idArticle" value="${article.idArticle}"/></c:url>'>
				Liste d'Image </a>

		</c:if>
	</div>
	<c:if test="${empty listeImages}">
		<h1 style="color: #7f8c8d; text-align: center;">
			<i class="fa fa-exclamation-triangle" aria-hidden="true"></i> cet
			article n'a pas d'image associer
		</h1>
	</c:if>
	<c:if test="${!empty listeImages}">
		<div id="carousel-example-generic" class="carousel slide"
			data-ride="carousel">
			<div class="carousel-inner" role="listbox">
				<c:forEach items="${listeImages}" var="liste">
					<div class="item">

						<img
							src="<c:url value="/afficheImage"><c:param name="idImg" value="${liste.id}"/></c:url>" />
						<div class="carousel-caption">
							<h3>${liste.titre}</h3>
							<p>Article illustré par : ${liste.auteur}</p>
						</div>
					</div>
				</c:forEach>
			</div>
			<a class="left carousel-control" href="#carousel-example-generic"
				role="button" data-slide="prev"> <i
				class="fa fa-caret-left fa-4x" aria-hidden="true"></i> <span
				class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#carousel-example-generic"
				role="button" data-slide="next"> <i
				class="fa fa-caret-right fa-4x" aria-hidden="true"></i> <span
				class="sr-only">Next</span>
			</a>
		</div>
	</c:if>

	<div class="ArticleImage">
		<h1 style="text-align: center;">${article.titre}</h1>
		<p>${article.contenu}</p>
		<br> <span style="font-weight: bold;">Publier par:
			${article.auteur} le : ${article.date}</span>
	</div>

	</div>


	<!-- jquery -->
	<script type="text/javascript" src="./Style/js/jq.js"></script>
	<!-- bootstrap -->
	<script type="text/javascript" src="./Style/js/bootstrap.js"></script>
	<script>
		jQuery(document).ready(function() {
			jQuery('.carousel-inner').find('.item:first').addClass('active');
		});
	</script>




</body>
</html>