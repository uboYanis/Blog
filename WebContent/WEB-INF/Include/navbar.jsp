<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Petit journal</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav navbar-right">
				<c:if test="${!empty sessionScope.sessionUtilisateur}">
					<li><a href="#">Utilisateur :
							${sessionScope.sessionUtilisateur.username} </a></li>
				</c:if>
				<c:if test="${!empty sessionScope.sessionUtilisateur}">
					<li><a
						href='<c:url value="/UserController?action=disconnect"></c:url>'>Deconnexion</a></li>
				</c:if>
				<c:if test="${empty sessionScope.sessionUtilisateur}">
					<li><a
						href='<c:url value="/ArticleController?action=list"></c:url>'>Accueil</a></li>
				</c:if>
				<c:if test="${empty sessionScope.sessionUtilisateur}">
					<li><a
						href='<c:url value="/UserController?action=login"></c:url>'>se
							connecter</a></li>
				</c:if>
				<c:if test="${empty sessionScope.sessionUtilisateur}">
					<li><a
						href='<c:url value="/UserController?action=add"></c:url>'>s'inscrire</a></li>
				</c:if>



			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>