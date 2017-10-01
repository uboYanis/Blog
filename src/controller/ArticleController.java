package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Article;
import implementation.ArticleImpl;
import specification.IArticle;
import validation.ValidationArticle;

@WebServlet("/ArticleController")
public class ArticleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IArticle gestion;

	@Override
	public void init() throws ServletException {
		super.init();
		/* instanciation de la class ArticleImpl */
		gestion = new ArticleImpl();
	}

	public ArticleController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idArt = null;

		/* Récupération de la valeur du param "action" dans URL */
		String action = request.getParameter("action");

		if (action != null) {
			/* selon la val du param "action" faire */
			switch (action) {
			/*
			 * liste des Articles
			 */
			case "list":
				/* récupérer la liste des Articles */
				List<Article> articles = gestion.listeArticle();
				/*
				 * Ajout de la liste d'Articles à l'objet requête et la stocké
				 * dans une var "afficher"
				 */
				request.setAttribute("afficher", articles);
				/* Affichage de la page "liste des Articles" */
				this.getServletContext().getRequestDispatcher("/WEB-INF/Article/Article.jsp").forward(request,
						response);
				break;
			/*
			 * liste d'articles d'un User
			 */
			case "mesArticle":
				/* Récupération de la valeur "auteur" dans URL */
				String auteur = request.getParameter("idAuteur");
				/* récupérer la liste des Articles du user passé en param */
				List<Article> mesArticles = gestion.mesArticle(auteur);
				/*
				 * Ajout de la liste d'Articles à l'objet requête et la stocké
				 * dans une var "show"
				 */
				request.setAttribute("show", mesArticles);
				/* Affichage de la page "liste des MesArticle" */
				this.getServletContext().getRequestDispatcher("/WEB-INF/Article/MesArticle.jsp").forward(request,
						response);
				break;
			/*
			 * ajouter un Article
			 */
			case "add":
				/* Affichage de la page "AjouterArticle" */
				this.getServletContext().getRequestDispatcher("/WEB-INF/Article/AjouterArticle.jsp").forward(request,
						response);
				break;

			/*
			 * Modifier un Article
			 */
			case "edit":
				/* Récupération de la valeur de " idArticle " dans URL */
				idArt = request.getParameter("idArticle");
				if (idArt != null) {
					int id = Integer.parseInt(idArt);
					/* Modifier cette article */
					Article art = gestion.afficher(id);

					/* Ajout de la article modifier à l'objet requête */
					request.setAttribute("article", art);
					/* Affichage de la page "ModifierArticle" */
					this.getServletContext().getRequestDispatcher("/WEB-INF/Article/ModifierArticle.jsp")
							.forward(request, response);
				}

				break;

			/*
			 * Supprimer un Article
			 */
			case "delete":
				/* Récupération de la valeur de " idArticle " dans URL */
				idArt = request.getParameter("idArticle");
				if (idArt != null) {
					int id = Integer.parseInt(idArt);
					/* Supprimer le user */
					gestion.deleteArticle(id);
					/* Redirection vers la page "liste des Article" */
					response.sendRedirect(request.getContextPath() + "/ArticleController?action=list");
				}

				break;
			default:
				break;
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Article article = null;
		String titre = null;
		String contenu = null;
		String auteur = null;

		/* Utiliser pour la validation de formulaire */
		ValidationArticle validation;
		Article valid = null;

		/* Récupération de la valeur du param "action" dans URL */
		String action = request.getParameter("action");

		if (action != null) {
			/* selon la val du param actoin faire */
			switch (action) {
			case "add":
				/* Récupération des valeurs des champs dans le formulaire */
				titre = request.getParameter("titre");
				contenu = request.getParameter("contenu");
				auteur = request.getParameter("auteur");

				/* Préparation de l'objet ValidationArticle */
				validation = new ValidationArticle();
				/*
				 * Appel au traitement et à la validation de la requête, et
				 * récupération du bean en résultant
				 */
				valid = validation.articleForm(request);
				/*
				 * Stockage du ValidationArticle et du bean User dans l'objet
				 * request
				 */
				request.setAttribute("articleValide", valid);
				request.setAttribute("form", validation);

				/* S'il y a aucune erreur lors de la saisie */
				if (validation.getErreurs().isEmpty()) {
					/* crée un objet Article avec les param transmit */
					article = new Article(titre, contenu, auteur);
					/* ajouter le user */
					gestion.addArticle(article);
					/* Redirection vers la page liste d'articles */
					response.sendRedirect(request.getContextPath() + "/ArticleController?action=list");
				} else {
					/*
					 * sinon , si le formulaire n'est pas valide
					 */
					/* Affichage de la page AjouterArticle */
					this.getServletContext().getRequestDispatcher("/WEB-INF/Article/AjouterArticle.jsp")
							.forward(request, response);
				}

				break;
			case "edit":
				/* Récupération des valeurs des champs dans le formulaire */
				titre = request.getParameter("titre");
				contenu = request.getParameter("contenu");

				/* Préparation de l'objet ValidationArticle */
				validation = new ValidationArticle();
				/*
				 * Appel au traitement et à la validation de la requête, et
				 * récupération du bean en résultant
				 */
				valid = validation.articleForm(request);

				/*
				 * Stockage du ValidationArticle et du bean User dans l'objet
				 * request
				 */
				request.setAttribute("articleValide", valid);
				request.setAttribute("form", validation);
				/* S'il y a aucune erreur lors de la saisie */
				if (validation.getErreurs().isEmpty()) {
					if (request.getParameter("idArt") != null) {
						/* crée un objet Article avec les param transmit */
						article = new Article();
						int idArt = Integer.parseInt(request.getParameter("idArt"));
						article.setIdArticle(idArt);
						article.setTitre(titre);
						article.setContenu(contenu);
						// modifier l'article en question
						gestion.updateArticle(article);
						/* Redirection vers la page de la liste des articles */
						response.sendRedirect(request.getContextPath() + "/ImageController?idArticle="+idArt+"&action=afficher");
					}
				} else {
					/*
					 * sinon , si il y eu une erreur lors de la sasie du
					 * formulaire
					 */
					this.getServletContext().getRequestDispatcher("/WEB-INF/Article/ModifierArticle.jsp")
							.forward(request, response);
				}
				break;
			default:
				break;
			}
		}
	}

}
