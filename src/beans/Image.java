package beans;

import java.io.InputStream;

public class Image {
	/* Propriétés du bean */
	
	private int id;
	private InputStream image;
	private String titre;
	private String auteur;
	private int idArt;
	
	/* les constructeurs */
	
	public Image(){
		super();
	}

	public Image(InputStream image, String titre,int idArt,String auteur) {
		super();
		this.image = image;
		this.titre = titre;
		this.idArt = idArt;
		this.auteur = auteur;
	}
	
	// ************* ACCESSEURS *************
	
	public int getId() {
		return id;
	}

	public InputStream getImage() {
		return image;
	}

	public String getTitre() {
		return titre;
	}

	public String getAuteur() {
		return auteur;
	}

	public int getIdArt() {
		return idArt;
	}
	
	// ************* MUTATEURS *************

	public void setId(int id) {
		this.id = id;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public void setIdArt(int idArt) {
		this.idArt = idArt;
	}



}
