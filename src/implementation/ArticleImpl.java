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
 * Implémentation des méthodes définie dans l'interface IArticle 
 *
 */
public class ArticleImpl implements IArticle {

	// ****** Ajouter un article *********
	@Override
	public void addArticle(Article p) {
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO `article`(`titre`, `contenu`, `date`, `auteur`) VALUES (?,?,CURDATE(),?)");
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * set.....() mises à disposition par l'objet PreparedStatement.
			 */

			ps.setString(1, p.getTitre());
			ps.setString(2, p.getContenu());
			ps.setString(3, p.getAuteur());

			/* Exécution de la requête */
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
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement(
					"UPDATE `article` SET `titre`=?,`contenu`=?,`date`=CURDATE() WHERE `idArticle`=?");

			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * set.....() mises à disposition par l'objet PreparedStatement.
			 */

			ps.setString(1, p.getTitre());
			ps.setString(2, p.getContenu());
			ps.setInt(3, p.getIdArticle());

			/* Exécution de la requête */
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
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("DELETE FROM `article` WHERE `idArticle`=? ");
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
			System.out.println("Erreur gestionArticle / Delete() " + e.getMessage());
		}

	}

	// ****** Afficher un article *********
	@Override
	public Article afficher(int id) {

		Article article = null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			ps = con.prepareStatement("SELECT * FROM article WHERE idArticle = ?");
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * setInt() mises à disposition par l'objet PreparedStatement.
			 */
			ps.setInt(1, id);
			/* Exécution d'une requête de lecture */
			rs = ps.executeQuery();
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
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM article ORDER BY idArticle desc");
			/* Exécution d'une requête de lecture */
			ResultSet rs = ps.executeQuery();
			/* Récupération des données du résultat de la requête de lecture */
			while (rs.next()) {
				/*
				 * Création du bean Article et initialisation avec les données
				 * récupérées
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
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM `article` WHERE `auteur`=? ORDER BY idArticle desc ");
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * setString() mises à disposition par l'objet PreparedStatement.
			 */
			ps.setString(1, auteur);
			/* Exécution d'une requête de lecture */
			ResultSet rs = ps.executeQuery();
			/* Récupération des données du résultat de la requête de lecture */
			while (rs.next()) {
				/*
				 * Création du bean Article et initialisation avec les données
				 * récupérées
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
