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
 * Impl�mentation des m�thodes d�finie dans l'interface IImage 
 *
 */
public class ImageImpl implements IImage {

	// ****** Ajouter une Image *********
	@Override
	public void enregistrerImage(Image image) {
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		// requete Sql
		String sql = "INSERT INTO `image`(`image`, `titre`, `idArt`, `auteur`) VALUES (?,?,?,?)";
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement(sql);
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * set.....() mises � disposition par l'objet PreparedStatement.
			 */
			ps.setBinaryStream(1, image.getImage());
			ps.setString(2, image.getTitre());
			ps.setInt(3, image.getIdArt());
			ps.setString(4, image.getAuteur());
			/* Ex�cution de la requ�te */
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
			// connexion � la base de donn�es
			Connection con = connexion.getConnection();
			try {
				/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
				PreparedStatement ps = con.prepareStatement("DELETE FROM `image` WHERE `idImage`=? ");
				/*
				 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
				 * setInt() mises � disposition par l'objet PreparedStatement.
				 */
				ps.setInt(1, id);

				/* Ex�cution de la requ�te */
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
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM article WHERE idArticle = ?");
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * setInt() mises � disposition par l'objet PreparedStatement.
			 */
			ps.setInt(1, id);
			/* Ex�cution d'une requ�te de lecture */
			ResultSet rs = ps.executeQuery();
			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			if (rs.next()) {
				/*
				 * Cr�ation du bean Article et initialisation avec les donn�es
				 * r�cup�r�es
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
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM image WHERE idArt=? ORDER BY idImage DESC");
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * setString() mises � disposition par l'objet PreparedStatement.
			 */
			ps.setInt(1, id);
			/* Ex�cution d'une requ�te de lecture */
			ResultSet rs = ps.executeQuery();
			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while (rs.next()) {
				/*
				 * Cr�ation du bean Image et initialisation avec les donn�es
				 * r�cup�r�es
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
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM image WHERE idImage=?");
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * setInt() mises � disposition par l'objet PreparedStatement.
			 */
			ps.setInt(1, id);
			/* Ex�cution d'une requ�te de lecture */
			ResultSet rs = ps.executeQuery();
			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
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
