package beans;

public class Article {
	/* Propriétés du bean */
	private int idArticle;
	private String titre;
	private String contenu;
	private String date;
	private String auteur;

	/* les constructeurs */

	public Article() {
		super();

	}

	public Article(int idArticle, String titre, String contenu, String date, String auteur) {
		super();
		this.idArticle = idArticle;
		this.titre = titre;
		this.contenu = contenu;
		this.date = date;
		this.auteur = auteur;
	}

	public Article(String titre, String contenu, String auteur) {
		super();

		this.titre = titre;
		this.contenu = contenu;
		this.auteur = auteur;

	}

	// ************* ACCESSEURS *************

	public int getIdArticle() {
		return idArticle;
	}

	public String getTitre() {
		return titre;
	}

	public String getContenu() {
		return contenu;
	}

	public String getDate() {
		return date;
	}

	public String getAuteur() {
		return auteur;
	}

	// ************* MUTATEURS *************

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

}
