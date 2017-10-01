package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import hachee.Hachee;
import implementation.UserImpl;
import specification.IUser;
import validation.ValidationUser;

@WebServlet("/UserController")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IUser gestionUser;

	public void init() throws ServletException {
		super.init();
		/* instanciation de la class UserImpl */
		gestionUser = new UserImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* Récupération de la valeur du param "action" dans URL */
		String action = request.getParameter("action");

		String idUser = null;
		/* selon la val du param "action" faire */
		if (action != null) {
			switch (action) {
			/*
			 * authentification
			 */
			case "login":
				/* Affichage de la page d'authentification */
				this.getServletContext().getRequestDispatcher("/WEB-INF/User/Authentification.jsp").forward(request,
						response);
				break;
			/*
			 * déconnexion
			 */
			case "disconnect":
				/* Renvoyer la session en cours associé à cette demande */
				HttpSession session = request.getSession();
				/* fermer la session et detacher tout les objet liée a elle */
				session.invalidate();
				/* Affichage de la page "liste des articles" */
				response.sendRedirect(request.getContextPath() + "/ArticleController?action=list");
				break;
			/*
			 * Inscription
			 */
			case "add":
				/* Affichage de la page d'inscription */
				this.getServletContext().getRequestDispatcher("/WEB-INF/User/Inscription.jsp").forward(request,
						response);
				break;
			/*
			 * liste des Users
			 */
			case "liste":
				/* récupérer la liste des Users */
				List<User> users = gestionUser.listeUser();
				/* Ajout de la liste des Users à l'objet requête */
				request.setAttribute("afficher", users);
				/* Affichage de la page "liste des Users" */
				this.getServletContext().getRequestDispatcher("/WEB-INF/User/ListeUser.jsp").forward(request, response);
				break;
			/*
			 * Supprimer un User
			 */
			case "delete":
				/* Récupération de la valeur de " idUser " dans URL */
				idUser = request.getParameter("idUser");
				if (idUser != null) {
					int id = Integer.parseInt(idUser);
					/* Supprimer le user */
					gestionUser.deleteUser(id);
					/* Redirection vers la page "liste des Users" */
					response.sendRedirect(request.getContextPath() + "/UserController?action=liste");
				}

				break;
			/*
			 * modifier un User
			 */
			case "update":
				idUser = request.getParameter("idUser");
				if (idUser != null) {
					int id = Integer.parseInt(idUser);
					gestionUser.updateStatut(id);
					response.sendRedirect(request.getContextPath() + "/UserController?action=liste");
				}

				break;
			default:
				break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User use = null;
		String username = null;
		String password = null;
		String role = null;

		/* Utiliser pour le hache mot de passe */
		Hachee md5 = null;
		String passeHachee = null;

		/* Utiliser pour la validation de formulaire */
		ValidationUser validation = null;
		User valid = null;

		HttpSession session = null;

		/* Récupération de la valeur du param "action" dans URL */
		String action = request.getParameter("action");
		/* selon la val du param actoin faire */
		if (action != null) {
			switch (action) {
			/*
			 * authentification
			 */
			case "login":
				/* Récupération des valeurs des champs dans le formulaire */
				username = request.getParameter("username");
				password = request.getParameter("password");

				/* hachée le mot de passe (SHA-256) */
				md5 = new Hachee(password);
				/* récupérer le hache du mot de pass */
				passeHachee = md5.codeGet();

				/* vérifier l'existence du user */
				use = gestionUser.login(username, passeHachee);

				/* Préparation de l'objet ValidationUser */
				validation = new ValidationUser();
				/*
				 * Appel au traitement et à la validation de la requête, et
				 * récupération du bean en résultant
				 */
				valid = validation.singin(request);
				/*
				 * Stockage du ValidationUser et du bean User dans l'objet
				 * request
				 */
				request.setAttribute("user", valid);
				request.setAttribute("form", validation);

				/* crée une session */
				session = request.getSession();

				/* S'il y a aucune erreur lors de la saisie */
				if (validation.getErreurs().isEmpty()) {
					/* si le user existe */
					if (use != null) {
						/*
						 * stocké l'objet user dans une variable de session
						 * "sessionUtilisateur"
						 */
						session.setAttribute("sessionUtilisateur", use);
						/* Redirection vers la page "liste des Articles" */
						response.sendRedirect(request.getContextPath() + "/ArticleController?action=list");
					} else {
						/*
						 * sinon , si le user n'existe pas
						 */

						/*
						 * stocké un message d'erreur dans la variable "error"
						 */
						request.setAttribute("error",
								"ce compte n'éxiste pas veuillez vous vérifier le username et/ou password ");
						/* Affichage de la page d'Authentification */
						this.getServletContext().getRequestDispatcher("/WEB-INF/User/Authentification.jsp")
								.forward(request, response);
					}
				} else {
					/*
					 * sinon , Si y eu une erreur lors de la saisie
					 */
					/* Affichage de la page d'Authentification */
					this.getServletContext().getRequestDispatcher("/WEB-INF/User/Authentification.jsp").forward(request,
							response);
				}

				break;
			/*
			 * inscription
			 */
			case "add":
				if (request.getParameter("action") != null) {
					/* Récupération des valeurs des champs dans le formulaire */
					username = request.getParameter("username");
					password = request.getParameter("password");
					role = request.getParameter("role");

					/* hachée le mot de passe (SHA-256) */
					md5 = new Hachee(password);
					/* récupérer le hache du mot de pass */
					passeHachee = md5.codeGet();

					/* Préparation de l'objet ValidationUser */
					validation = new ValidationUser();
					/*
					 * Appel au traitement et à la validation de la requête, et
					 * récupération du bean en résultant
					 */
					valid = validation.singin(request);

					/*
					 * Stockage du ValidationUser et du bean User dans l'objet
					 * request
					 */
					request.setAttribute("user", valid);
					request.setAttribute("form", validation);

					/*vérifier le si le username est deja utiliser ou non */
					boolean ex = gestionUser.existe(username);

					/*si le username n'est pas deja utiliser */
					if (ex != true) {
						/* S'il y a aucune erreur lors de la saisie */
						if (validation.getErreurs().isEmpty()) {
							/*crée un objet user avec les par transmit */
							use = new User(username, passeHachee, role);
							/*ajouter le user*/
							gestionUser.addUser(use);
							
							/* Redirection vers la page d'authentification */
							response.sendRedirect(request.getContextPath() + "/UserController?action=login");
						} else {
							/* Affichage de la page d'Inscription */
							this.getServletContext().getRequestDispatcher("/WEB-INF/User/Inscription.jsp")
									.forward(request, response);
						}
					} else {
						/*
						 * sinon , si le username existe deja 
						 */
						/*
						 * stocké un message d'erreur dans la variable "error"
						 */
						request.setAttribute("error", "ce username existe déja veuillez changer svp");
						
						/* Affichage de la page d'Inscription */
						this.getServletContext().getRequestDispatcher("/WEB-INF/User/Inscription.jsp").forward(request,
								response);
					}

				}

				break;

			}
		}
	}

}
