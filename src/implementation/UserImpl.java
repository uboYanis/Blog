package implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import beans.User;
import connection.connexion;
import specification.IUser;

/* 
 * Implémentation des méthodes définie dans l'interface IUser 
 *
 */
public class UserImpl implements IUser {

	// ****** Ajouter un user *********
	@Override
	public void addUser(User user) {
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO `user`(`username`, `password`, `role`) VALUES (?,?,?) ");
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * set.....() mises à disposition par l'objet PreparedStatement.
			 */
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getRole());
			/* Exécution de la requête */
			ps.executeUpdate();

			/* fermer le PreparedStatement */
			ps.close();
		} catch (Exception e) {
			System.out.println("GestionUser / addUser() " + e.getMessage());
		}
	}

	// ****** Supprimer un user *********
	@Override
	public void deleteUser(int id) {
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("DELETE FROM `user` WHERE `idUser`=? ");
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

	// ****** Modifier un User *********
	@Override
	public void updateStatut(int id) {
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("UPDATE `user` SET `role`='admin' WHERE `idUser`=?");
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
			System.out.println("Erreur gestionArticle / updateStatut() " + e.getMessage());
		}

	}

	// ******Liste de tout les Users *********
	@Override
	public List<User> listeUser() {
		// instanciation d'un objet ArrayList <User>()
		List<User> users = new ArrayList<User>();
		// connexion à la base de données
		Connection con = connexion.getConnection();

		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user");
			/* Exécution d'une requête de lecture */
			ResultSet rs = ps.executeQuery();
			/* Récupération des données du résultat de la requête de lecture */
			while (rs.next()) {
				/*
				 * Création du bean User et initialisation avec les données
				 * récupérées
				 */
				User user = new User();
				user.setIdUser(rs.getInt("idUser"));
				user.setUsername(rs.getString("username"));
				user.setRole(rs.getString("role"));
				// ajouter le User a la liste de User (ArrayList<User>())
				users.add(user);
			}
			/* fermer le ResultSet */
			rs.close();
			/* fermer le PreparedStatement */
			ps.close();
		} catch (Exception e) {
			System.out.println("Erreur gestionUser / listeUser() " + e.getMessage());
		}
		// return la liste des Users
		return users;
	}

	// ******authentification d'un User
	@Override
	public User login(String username, String password) {
		User user = null;
		// connexion à la base de données
		Connection con = connexion.getConnection();
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username= ? and password= ?");
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * setString() mises à disposition par l'objet PreparedStatement.
			 */
			ps.setString(1, username);
			ps.setString(2, password);
			/* Exécution d'une requête de lecture */
			ResultSet rs = ps.executeQuery();
			/* Récupération des données du résultat de la requête de lecture */
			if (rs.next()) {
				/*
				 * Création du bean User et initialisation avec les données
				 * récupérées
				 */
				user = new User();
				user.setIdUser(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setRole(rs.getString(4));

			}
			/* fermer le ResultSet */
			rs.close();
			/* fermer le PreparedStatement */
			ps.close();
		} catch (SQLException e) {
			System.out.println("GestionUser / login() " + e.getMessage());
		}
		// return le User
		return user;
	}

	// vérifier si le User éxiste deja
	@Override
	public boolean existe(String username) {
		// connexion à la base de données
		Connection con = connexion.getConnection();
		// si le User n'éxiste pas deja en return false
		boolean ex = false;
		try {
			/* Création de l'objet gérant la requête préparée définie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username= ?");
			/*
			 * Remplissage des paramètres de la requête grâce aux méthodes
			 * setString() mises à disposition par l'objet PreparedStatement.
			 */
			ps.setString(1, username);
			/* Exécution d'une requête de lecture */
			ResultSet rs = ps.executeQuery();
			/* Récupération des données du résultatcture */
			if (rs.next()) {
				/*
				 * s'il y a une ligne de retournée alors le username est deja
				 * pris
				 */
				ex = true;
			}
			/* fermer le ResultSet */
			rs.close();
			/* fermer le PreparedStatement */
			ps.close();
		} catch (SQLException e) {
			System.out.println("GestionUser / existe() " + e.getMessage());
		}
		// return true  or  false
		return ex;
	}

}
