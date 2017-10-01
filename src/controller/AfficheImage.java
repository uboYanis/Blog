package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Image;
import implementation.ImageImpl;


@WebServlet("/afficheImage")
public class AfficheImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ImageImpl gestion;
    
    public AfficheImage() {
        super();
        gestion = new ImageImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("idImg") != null) {
			/* Récupération de la valeur "idImg" dans URL */
			int idImg = Integer.parseInt(request.getParameter("idImg"));
			/*crée un objet image*/
			Image image = new Image();
			/*recuperer l'image en question*/
			image = gestion.getImage(idImg);
			/*stocké l'image dans une var InputStream */ 
			InputStream is = image.getImage();
			/*définir le type de la réponse a renvoyer*/
			response.setContentType("image/jpg");
			/*une var OutputStream où les données peuvent être écrites*/
			OutputStream o = response.getOutputStream();
			int c;
			/*Lire l'octet suivant de données à partir du flux d'entrée*/
			while ((c = is.read()) != -1) {
				o.write(c);
			}
			/*forcer les octets de sortie tamponnées à être écrits*/
			o.flush();
			/*Ferme ce flux de sortie et libère toutes les ressources système associées à ce flux*/
			o.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	}
	
	
	}


