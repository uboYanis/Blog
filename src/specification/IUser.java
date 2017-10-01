package specification;

import java.util.List;

import beans.User;

public interface IUser {
	
	public void addUser (User user);
	public void deleteUser(int id);
	public void updateStatut(int id);
	
	public List<User> listeUser();
	public User login(String username, String password);
	public boolean existe(String username);
}
