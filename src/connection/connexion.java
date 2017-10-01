package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class connexion {
	/* Parametre de connexion � la base de donn�es */
	private static Connection connection;
	private static String url="jdbc:mysql://localhost/news";
	private static String username="root";
	private static String password="";
	static {
		
		try {
			/* Chargement du driver JDBC pour MySQL */
			Class.forName("com.mysql.jdbc.Driver");
			/* Connexion � la base de donn�es */
			connection=DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Retourne l'objet connection
	public static Connection getConnection() {
		return connection;
	}
	
	
}
