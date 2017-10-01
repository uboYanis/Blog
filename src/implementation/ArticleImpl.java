package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Article;
import connection.connexion;
import specification.IArticle;
/* 
 * Impl�mentation des m�thodes d�finie dans l'interface IArticle 
 *
 */
public class ArticleImpl implements IArticle {

	// ****** Ajouter un article *********
	@Override
	public void addArticle(Article p) {
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO `article`(`titre`, `contenu`, `date`, `auteur`) VALUES (?,?,CURDATE(),?)");
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * set.....() mises � disposition par l'objet PreparedStatement.
			 */

			ps.setString(1, p.getTitre());
			ps.setString(2, p.getContenu());
			ps.setString(3, p.getAuteur());

			/* Ex�cution de la requ�te */
			ps.executeUpdate();

			/* fermer le PreparedStatement */
			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ajout article" + e.getMessage());
		}
	}

	// ****** Modifier un article *********
	@Override
	public void updateArticle(Article p) {
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement(
					"UPDATE `article` SET `titre`=?,`contenu`=?,`date`=CURDATE() WHERE `idArticle`=?");

			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * set.....() mises � disposition par l'objet PreparedStatement.
			 */

			ps.setString(1, p.getTitre());
			ps.setString(2, p.getContenu());
			ps.setInt(3, p.getIdArticle());

			/* Ex�cution de la requ�te */
			ps.executeUpdate();
			/* fermer le PreparedStatement */
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ****** Supprimer un article *********
	@Override
	public void deleteArticle(int id) {
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("DELETE FROM `article` WHERE `idArticle`=? ");
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
			System.out.println("Erreur gestionArticle / Delete() " + e.getMessage());
		}

	}

	// ****** Afficher un article *********
	@Override
	public Article afficher(int id) {

		Article article = null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			ps = con.prepareStatement("SELECT * FROM article WHERE idArticle = ?");
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * setInt() mises � disposition par l'objet PreparedStatement.
			 */
			ps.setInt(1, id);
			/* Ex�cution d'une requ�te de lecture */
			rs = ps.executeQuery();
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
		// retourner l'article a afficher
		return article;
	}

	// ******Liste de tout les articles *********
	@Override
	public List<Article> listeArticle() {
		// instanciation d'un objet ArrayList <Article>()
		List<Article> articles = new ArrayList<Article>();
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM article ORDER BY idArticle desc");
			/* Ex�cution d'une requ�te de lecture */
			ResultSet rs = ps.executeQuery();
			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while (rs.next()) {
				/*
				 * Cr�ation du bean Article et initialisation avec les donn�es
				 * r�cup�r�es
				 */
				Article art = new Article();

				art.setIdArticle(rs.getInt("idArticle"));
				art.setTitre(rs.getString("titre"));
				art.setContenu(rs.getString("contenu"));
				art.setDate(rs.getString("date"));
				art.setAuteur(rs.getString("auteur"));
				// ajouter l'article a la liste d'article (ArrayList<Article>())
				articles.add(art);
			}
			/* fermer le ResultSet */
			rs.close();
			/* fermer le PreparedStatement */
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// retourner le liste d'articles
		return articles;
	}

	// ******Liste des article publier par un auteur
	@Override
	public List<Article> mesArticle(String auteur) {
		// instanciation d'un objet ArrayList <Article>()
		List<Article> articles = new ArrayList<Article>();
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM `article` WHERE `auteur`=? ORDER BY idArticle desc ");
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * setString() mises � disposition par l'objet PreparedStatement.
			 */
			ps.setString(1, auteur);
			/* Ex�cution d'une requ�te de lecture */
			ResultSet rs = ps.executeQuery();
			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while (rs.next()) {
				/*
				 * Cr�ation du bean Article et initialisation avec les donn�es
				 * r�cup�r�es
				 */
				Article art = new Article();

				art.setIdArticle(rs.getInt("idArticle"));
				art.setTitre(rs.getString("titre"));
				art.setContenu(rs.getString("contenu"));
				art.setDate(rs.getString("date"));
				art.setAuteur(rs.getString("auteur"));
				// ajouter l'article a la liste d'article (ArrayList<Article>())
				articles.add(art);
			}
			/* fermer le ResultSet */
			rs.close();
			/* fermer le PreparedStatement */
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// retourner le liste d'articles
		return articles;
	}
	
	

}
