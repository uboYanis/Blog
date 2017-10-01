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
 * Impl�mentation des m�thodes d�finie dans l'interface IUser 
 *
 */
public class UserImpl implements IUser {

	// ****** Ajouter un user *********
	@Override
	public void addUser(User user) {
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con
					.prepareStatement("INSERT INTO `user`(`username`, `password`, `role`) VALUES (?,?,?) ");
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * set.....() mises � disposition par l'objet PreparedStatement.
			 */
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getRole());
			/* Ex�cution de la requ�te */
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
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("DELETE FROM `user` WHERE `idUser`=? ");
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

	// ****** Modifier un User *********
	@Override
	public void updateStatut(int id) {
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("UPDATE `user` SET `role`='admin' WHERE `idUser`=?");
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
			System.out.println("Erreur gestionArticle / updateStatut() " + e.getMessage());
		}

	}

	// ******Liste de tout les Users *********
	@Override
	public List<User> listeUser() {
		// instanciation d'un objet ArrayList <User>()
		List<User> users = new ArrayList<User>();
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();

		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user");
			/* Ex�cution d'une requ�te de lecture */
			ResultSet rs = ps.executeQuery();
			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			while (rs.next()) {
				/*
				 * Cr�ation du bean User et initialisation avec les donn�es
				 * r�cup�r�es
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
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username= ? and password= ?");
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * setString() mises � disposition par l'objet PreparedStatement.
			 */
			ps.setString(1, username);
			ps.setString(2, password);
			/* Ex�cution d'une requ�te de lecture */
			ResultSet rs = ps.executeQuery();
			/* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
			if (rs.next()) {
				/*
				 * Cr�ation du bean User et initialisation avec les donn�es
				 * r�cup�r�es
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

	// v�rifier si le User �xiste deja
	@Override
	public boolean existe(String username) {
		// connexion � la base de donn�es
		Connection con = connexion.getConnection();
		// si le User n'�xiste pas deja en return false
		boolean ex = false;
		try {
			/* Cr�ation de l'objet g�rant la requ�te pr�par�e d�finie */
			PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE username= ?");
			/*
			 * Remplissage des param�tres de la requ�te gr�ce aux m�thodes
			 * setString() mises � disposition par l'objet PreparedStatement.
			 */
			ps.setString(1, username);
			/* Ex�cution d'une requ�te de lecture */
			ResultSet rs = ps.executeQuery();
			/* R�cup�ration des donn�es du r�sultatcture */
			if (rs.next()) {
				/*
				 * s'il y a une ligne de retourn�e alors le username est deja
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
