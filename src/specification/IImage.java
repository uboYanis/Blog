package specification;

import java.util.List;

import beans.Article;
import beans.Image;

public interface IImage {

	public void enregistrerImage(Image image);
	public void deleteImage(int id);
	public Article show(int id);
	public List<Image> listeImage(int id);	
	public Image getImage(int id);
	
}
