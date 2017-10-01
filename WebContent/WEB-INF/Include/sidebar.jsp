
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${!empty sessionScope.sessionUtilisateur}">
	<div class="side col-md-3">
		<div class="list-group">
			<a class="list-group-item active" href='#'>Menu</a> <a
				class="list-group-item"
				href='<c:url value="/ArticleController?action=list"></c:url>'>Liste
				des Articles</a>
			<c:if test="${sessionScope.sessionUtilisateur.role.equals('admin')}">
				<a class="list-group-item"
					href='<c:url value="/ArticleController?action=add"></c:url>'>Ajouter
					un Article</a>
			</c:if>
			<c:if test="${sessionScope.sessionUtilisateur.role.equals('admin')}">
				<a class="list-group-item"
					href='<c:url value="/ArticleController?action=mesArticle"><c:param name="idAuteur" value="${sessionScope.sessionUtilisateur.username}"/></c:url>'>
					Mes articles </a>
			</c:if>
			<c:if test="${sessionScope.sessionUtilisateur.role.equals('admin')}">
				<a class="list-group-item"
					href='<c:url value="/UserController?action=liste"></c:url>'>
					Liste des membres </a>
			</c:if>
		</div>
	</div>
</c:if>