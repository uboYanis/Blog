package validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import beans.User;

public final class ValidationUser {
	private static final String CHAMP_Username = "username";
	private static final String CHAMP_Password = "password";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public User singin(HttpServletRequest request) {
		String username = getValeurChamp(request, CHAMP_Username);
		String password = getValeurChamp(request, CHAMP_Password);

		User user = new User();

		try {
			validationUsername(username);
		} catch (Exception e) {
			setErreur(CHAMP_Username, e.getMessage());
		}
		user.setUsername(username);

		try {
			validationPassword(password);
		} catch (Exception e) {
			setErreur(CHAMP_Password, e.getMessage());
		}
		user.setPassword(password);

		if (erreurs.isEmpty()) {
			resultat = "Succès de la création du user.";
		} else {
			resultat = "Échec de la création du user.";
		}

		return user;
	}

	private void validationUsername(String username) throws Exception {
		if (username != null) {
			if (username.length() < 5) {
				throw new Exception("Le username doit contenir au moins 5 caractères.");
			}
		} else {
			throw new Exception("veuillez saisire un UserName svp");
		}
	}

	private void validationPassword(String password) throws Exception {
		if (password != null) {
			if (!password.matches("^(?=.*[a-z])(?=.*[A-Z]).{5,10}$") ) {
				throw new Exception("le password doit contenir au moins une Min,Maj,Nbr,5 char");
			}
		} else {
			throw new Exception("veuillez saisire un password svp");
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
