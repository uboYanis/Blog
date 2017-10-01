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
			/* R�cup�ration de la valeur "idImg" dans URL */
			int idImg = Integer.parseInt(request.getParameter("idImg"));
			/*cr�e un objet image*/
			Image image = new Image();
			/*recuperer l'image en question*/
			image = gestion.getImage(idImg);
			/*stock� l'image dans une var InputStream */ 
			InputStream is = image.getImage();
			/*d�finir le type de la r�ponse a renvoyer*/
			response.setContentType("image/jpg");
			/*une var OutputStream o� les donn�es peuvent �tre �crites*/
			OutputStream o = response.getOutputStream();
			int c;
			/*Lire l'octet suivant de donn�es � partir du flux d'entr�e*/
			while ((c = is.read()) != -1) {
				o.write(c);
			}
			/*forcer les octets de sortie tamponn�es � �tre �crits*/
			o.flush();
			/*Ferme ce flux de sortie et lib�re toutes les ressources syst�me associ�es � ce flux*/
			o.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	}
	
	
	}


