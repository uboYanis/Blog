package validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import beans.Image;
public class ValidationImage {

	private static final String CHAMP_Titre = "titre";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public Image ImageForm(HttpServletRequest request) {
		String titre = getValeurChamp(request, CHAMP_Titre);
		
		Image image = new Image();

		try {
			validationTitre(titre);
		} catch (Exception e) {
			setErreur(CHAMP_Titre, e.getMessage());
		}
		image.setTitre(titre);
		
		if (erreurs.isEmpty()) {
			resultat = "Succès de la création de l'image.";
		} else {
			resultat = "Échec de la création de l'image.";
		}

		return image;
	}

	private void validationTitre(String titre) throws Exception {
		if (titre != null) {
			if (titre.length() < 5) {
				throw new Exception("Le titre doit contenir au moins 5 caractères.");
			}
		} else {
			throw new Exception("veuillez saisire un titre svp");
		}
	}



	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

}
