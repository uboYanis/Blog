package beans;

public class User {
	/* Propriétés du bean */

	private int idUser;
	private String username;
	private String password;
	private String role;

	/* les constructeurs */

	public User() {
		super();
	}

	public User(int idUser, String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	// ************* ACCESSEURS *************

	public int getIdUser() {
		return idUser;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}
	
	// ************* MUTATEURS *************

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
