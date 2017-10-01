<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>liste des Articles</title>
<!-- bootstrap.css -->
<link rel="stylesheet" type="text/css" href="./Style/css/bootstrap.css">

<link rel="stylesheet" type="text/css" href="./Style/css/style.css">

<link rel="stylesheet" type="text/css"
	href="./Style/font/css/font-awesome.min.css">
</head>

<body class="article row">
	<!-- import la sidebar et la navbar -->
	<c:import url="../Include/navbar.jsp" />

	<c:import url="../Include/sidebar.jsp" />

	<c:if test="${!empty sessionScope.sessionUtilisateur}">
		<div class="col-md-9">
	</c:if>

	<c:if test="${empty sessionScope.sessionUtilisateur}">
		<div class="container">
	</c:if>

	<c:if test="${empty afficher}">
		<h1 style="text-align: center;">aucun article publier</h1>
	</c:if>
	<c:if test="${!empty afficher}">
		<c:forEach items="${afficher}" var="p">
			<div class="panel panel-default">
				<div class="panel-heading">

					<spen class="titre">${p.titre}</spen>
					<c:if test="${!empty sessionScope.sessionUtilisateur}">
						<p>
							<a
								href='<c:url value="ImageController?action=liste"><c:param name="idArticle" value="${p.idArticle}"/></c:url>'>
								<i class="fa fa-picture-o fa-2x" aria-hidden="true"
								title="Liste d'image"></i>
							</a>
						</p>
					</c:if>
					<c:if
						test="${p.auteur == sessionScope.sessionUtilisateur.username}">
						<p>
							<a
								href='<c:url value="/ArticleController?action=delete"><c:param name="idArticle" value="${p.idArticle}"/></c:url>'>
								<i class="fa fa-times-circle fa-2x" aria-hidden="true"
								title="Supprimer cet article"></i> 
							</a>
						</p>
					</c:if>
					<c:if
						test="${p.auteur == sessionScope.sessionUtilisateur.username}">
						<p>
							<a
								href='<c:url value="/ArticleController?action=edit"><c:param name="idArticle" value="${p.idArticle}"/></c:url>'>
								<i class="fa fa-pencil-square fa-2x" aria-hidden="true"
								title="Modifier cet article"></i>
							</a>
						</p>
					</c:if>
					<c:if test="${!empty sessionScope.sessionUtilisateur}">
						<p>
							<a
								href='<c:url value="/ImageController?action=add"><c:param name="idArticle" value="${p.idArticle}"/></c:url>'>
								<i class="fa fa-plus-square fa-2x" aria-hidden="true"
								title="Ajouter une Image"></i>
							</a>
						</p>
					</c:if>

				</div>
				<div class="panel-body">
					<div class="suite">${p.contenu}</div>
					<br>
					<p>
						<a class="btn btn-default"
							href='<c:url value="ImageController?action=afficher"><c:param name="idArticle" value="${p.idArticle}"/></c:url>'>
							lire la suite </a>
					</p>
					<br> <span style="font-weight: bold;">Publier par:
						${p.auteur} le : ${p.date}</span>
				</div>
			</div>

		</c:forEach>
	</c:if>
	</div>

	
	<!-- jquery -->
	<script type="text/javascript" src="./Style/js/jq.js"></script>
	<!-- bootstrap -->
	<script type="text/javascript" src="./Style/js/bootstrap.js"></script>
	<script type="text/javascript">
		$("div.suite").text(function(index, currentText) {
			return currentText.substr(0, 250) + ' ...';
		});
	</script>
</body>
</html>