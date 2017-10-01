package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.Article;
import beans.Image;
import implementation.ImageImpl;
import specification.IImage;
import validation.ValidationImage;

/**
 * Servlet implementation class ImageController
 */
@WebServlet("/ImageController")
public class ImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	IImage gestion;

	@Override
	public void init() throws ServletException {
		super.init();
		gestion = new ImageImpl();
	}

	public ImageController() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idArt = request.getParameter("idArticle");
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			/*
			 * ajouter une image
			 */
			case "add":
				if (idArt != null) {
					int id = Integer.parseInt(idArt);
					/*
					 * Ajout d'idArticle � l'objet requ�te et la stock� dans une
					 * var "article"
					 */
					request.setAttribute("article", id);
					/* Affichage de la page "ajout d'une image" */
					this.getServletContext().getRequestDispatcher("/WEB-INF/Image/Image.jsp").forward(request,
							response);
				}
				break;
			/*
			 * afficher un article avec ses images
			 */
			case "afficher":
				if (request.getParameter("idArticle") != null) {
					/* r�cup�rer l'idArticle */
					int idArticle = Integer.parseInt(request.getParameter("idArticle"));
					/* r�cup�rer la liste d'image associe a un article donn� */
					List<Image> img = gestion.listeImage(idArticle);
					/* r�cup�rer un article donn� */
					Article article = gestion.show(idArticle);
					/*
					 * ajouter la liste des images et l'article a des objet
					 * requete
					 */
					request.setAttribute("listeImages", img);
					request.setAttribute("article", article);
					this.getServletContext().getRequestDispatcher("/WEB-INF/Image/Imageaffiche.jsp").forward(request,
							response);
				}

				break;
			/*
			 * afficher la liste d'image associer a un article
			 */
			case "liste":
				if (request.getParameter("idArticle") != null) {
					int idArticle = Integer.parseInt(request.getParameter("idArticle"));
					/* r�cup�rer la liste d'image d'un article donn� */
					List<Image> img = gestion.listeImage(idArticle);
					request.setAttribute("monArticle", idArticle);
					request.setAttribute("listeImages", img);
					this.getServletContext().getRequestDispatcher("/WEB-INF/Image/ListeImage.jsp").forward(request,
							response);
				}

				/*
				 * Supprimer une image
				 */
			case "delete":
				/* R�cup�ration de la valeur de " idImage " dans URL */
				if (request.getParameter("idImage") != null) {
					int idImg = Integer.parseInt(request.getParameter("idImage"));
					int idArticle = Integer.parseInt(request.getParameter("idArticle"));
					/* Supprimer l'image */
					gestion.deleteImage(idImg);
					/* Redirection vers la page "liste des Image" */
					response.sendRedirect(
							request.getContextPath() + "/ImageController?idArticle=" + idArticle + "&action=liste");
				}

				break;

			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		ValidationImage validation;
		Image valid = null;
		if (action != null) {
			switch (action) {
			/*
			 * ajouter une image
			 */
			case "add":
				/* Pr�paration de l'objet ValidationImage */
				validation = new ValidationImage();
				/*
				 * Appel au traitement et � la validation de la requ�te, et
				 * r�cup�ration du bean en r�sultant
				 */
				valid = validation.ImageForm(request);
				/*
				 * Stockage du ValidationImage et du bean image dans l'objet
				 * request
				 */
				request.setAttribute("ImageValide", valid);
				request.setAttribute("form", validation);

				if (ServletFileUpload.isMultipartContent(request)) {

					FileItemFactory factory = new DiskFileItemFactory();
					ServletFileUpload upload = new ServletFileUpload(factory);

					List<FileItem> items = null;
					try {
						items = upload.parseRequest(request);
					} catch (FileUploadException e) {
						e.printStackTrace();
					}
					Iterator<FileItem> iter = items.iterator();

					// traitement des param 1 par 1 dans le while
					Image image = new Image();
					InputStream is = null;
					String titre = null;
					String auteur = null;
					int id = 0;

					while (iter.hasNext()) {
						FileItem item = iter.next();
						if (item.isFormField()) {
							if (item.getFieldName().equals("id")) {
								id = Integer.parseInt(item.getString());
							} else if (item.getFieldName().equals("titre")) {
								titre = item.getString();
							} else if (item.getFieldName().equals("auteur")) {
								auteur = item.getString();

							}
						} else {
							// pi�ce jointe
							is = item.getInputStream();
						}
					}

					image.setImage(is);
					image.setTitre(titre);
					image.setIdArt(id);
					image.setAuteur(auteur);
					gestion.enregistrerImage(image);

					response.sendRedirect(
							request.getContextPath() + "/ImageController?idArticle=" + id + "&action=liste");

				}
				break;

			}
		}

	}
}
