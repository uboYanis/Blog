package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Article;
import beans.Image;
import connection.connexion;
import specification.IImage;
/* 
 * Implémentation des méthodes définie dans l'interface IImage 
 *
 */
public class ImageImpl implements IImage {

	// ****** Ajouter une Image *********
	@Override
	public void enregistrerImage(Image image) {
		// connexion à la base de données
		Connection con = connexion.getConnection();
		// requete Sql
		String sql = "INSERT INTO `image`(`image`, `titre`, `idArt`, `auteur`) VALUES (?,?,?,?)";
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement(sql);
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * set.....() mises à disposition par l'objet PreparedStatement.
			 */
			ps.setBinaryStream(1, image.getImage());
			ps.setString(2, image.getTitre());
			ps.setInt(3, image.getIdArt());
			ps.setString(4, image.getAuteur());
			/* Exécution de la requête */
			ps.executeUpdate();
			/* fermer le PreparedStatement */
			ps.close();
		} catch (Exception e) {
			System.out.println("Erreur gestionImage.enregistrerImage " + e.getMessage() + e.getStackTrace());
		}
	}
	
	// ****** Supprimer un Image *********
		@Override
		public void deleteImage(int id) {
			// connexion à la base de données
			Connection con = connexion.getConnection();
			try {
				/* Création de l'objet gérant la requête préparée définie */
				PreparedStatement ps = con.prepareStatement("DELETE FROM `image` WHERE `idImage`=? ");
				/*
				 * Remplissage des paramètres de la requête grâce aux méthodes
				 * setInt() mises à disposition par l'objet PreparedStatement.
				 */
				ps.setInt(1, id);

				/* Exécution de la requête */
				ps.executeUpdate();
				/* fermer le PreparedStatement */
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur gestionImage / Delete() " + e.getMessage());
			}

		}

	// ****** Afficher un article *********
	@Override
	public Article show(int id) {
		Article article = null;
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM article WHERE idArticle = ?");
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * setInt() mises à disposition par l'objet PreparedStatement.
			 */
			ps.setInt(1, id);
			/* Exécution d'une requête de lecture */
			ResultSet rs = ps.executeQuery();
			/* Récupération des données du résultat de la requête de lecture */
			if (rs.next()) {
				/*
				 * Création du bean Article et initialisation avec les données
				 * récupérées
				 */
				article = new Article();
				article.setIdArticle(rs.getInt("idArticle"));
				article.setTitre(rs.getString("titre"));
				article.setContenu(rs.getString("contenu"));
				article.setAuteur(rs.getString("auteur"));
				article.setDate(rs.getString("date"));
			}
			/* fermer le ResultSet */
			rs.close();
			/* fermer le PreparedStatement */
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (article == null)
			throw new RuntimeException("Article introuvable");
		//return l'article
		return article;
	}

	// ******Liste des Image associer a un Article
	@Override
	public List<Image> listeImage(int id) {
		// instanciation d'un objet ArrayList <Image>()
		List<Image> images = new ArrayList<Image>();
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM image WHERE idArt=? ORDER BY idImage DESC");
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * setString() mises à disposition par l'objet PreparedStatement.
			 */
			ps.setInt(1, id);
			/* Exécution d'une requête de lecture */
			ResultSet rs = ps.executeQuery();
			/* Récupération des données du résultat de la requête de lecture */
			while (rs.next()) {
				/*
				 * Création du bean Image et initialisation avec les données
				 * récupérées
				 */
				Image image = new Image();
				image.setId(rs.getInt(1));
				image.setImage(rs.getBinaryStream(2));
				image.setTitre(rs.getString(3));
				image.setIdArt(rs.getInt(4));
				image.setAuteur(rs.getString(5));
				// ajouter l'Image a la liste d'image (ArrayList<Image>())
				images.add(image);
			}
			/* fermer le ResultSet */
			rs.close();
			/* fermer le PreparedStatement */
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//return la Liste des Image associer a un Article
		return images;
	}

	// ****** Afficher un Image *********
	@Override
	public Image getImage(int id) {
		// instanciation d'un objet ArrayList <Article>()
		Image images = new Image();
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM image WHERE idImage=?");
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * setInt() mises à disposition par l'objet PreparedStatement.
			 */
			ps.setInt(1, id);
			/* Exécution d'une requête de lecture */
			ResultSet rs = ps.executeQuery();
			/* Récupération des données du résultat de la requête de lecture */
			if (rs.next()) {
				images.setImage(rs.getBinaryStream("image"));
			}
			/* fermer le PreparedStatement */
			ps.close();
		} catch (SQLException e) {
			System.out.println("GestionImage / ListeImage() / " + e.getMessage());
		}
		// return l'image a afficher
		return images;
	}
	
}
