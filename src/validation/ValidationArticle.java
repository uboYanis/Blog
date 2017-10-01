package validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import beans.Article;


public final class ValidationArticle {
	private static final String CHAMP_titre = "titre";
	private static final String CHAMP_contenu = "contenu";
	

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public Article articleForm(HttpServletRequest request) {
		String titre = getValeurChamp(request, CHAMP_titre);
		String contenu = getValeurChamp(request, CHAMP_contenu);
		

		Article article =new Article();

		try {
			validationTitre(titre);
		} catch (Exception e) {
			setErreur(CHAMP_titre, e.getMessage());
		}
		article.setTitre(titre);
		try {
			validationContenu(contenu);
		} catch (Exception e) {
			setErreur(CHAMP_contenu, e.getMessage());
		}
		article.setContenu(contenu);
		/*
		 * avant j ai oublier le setContenu(contenu) :p
		 */
		
		if (erreurs.isEmpty()) {
			resultat = "Succ�s de la cr�ation de l'article.";
		} else {
			resultat = "�chec de la cr�ation de l'article.";
		}

		return article;
	}

	private void validationTitre(String titre) throws Exception {
		if (titre != null) {
			if (titre.length() < 7) {
				throw new Exception("Le titre doit contenir au moins 7 caract�res.");
			}
		} else {
			throw new Exception("veuillez saisire un titre svp");
		}
	}

	private void validationContenu(String contenu) throws Exception {
		if (contenu != null) {
			if (contenu.length() < 10) {
				throw new Exception("Le contenu de l'article doit contenir au moins 10 caract�res.");
			}
		} else {
			throw new Exception("veuillez saisire un text svp");
		}
	}
	
	

	/*
	 * Ajoute un message correspondant au champ sp�cifi� � la map des erreurs.
	 */
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	/*
	 * R�cup�ration des champs et retourne null si un champ est vide, et son contenu
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
