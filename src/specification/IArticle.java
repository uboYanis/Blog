package specification;

import java.util.List;

import beans.Article;

public interface IArticle {

	public void addArticle (Article p);
	public void updateArticle(Article p);
	public void deleteArticle(int id);
	
	public Article afficher(int id);
	public List<Article> listeArticle();
	public List<Article> mesArticle(String auteur);
	
	
}
