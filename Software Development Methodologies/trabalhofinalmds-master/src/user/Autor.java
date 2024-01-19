package user;


import article.Article;
import repository.Repository;

public class Autor extends User {
    public Autor(String name, String email, int nif, String password, int nib) {
        super(name, email, nif, UserRole.AUTOR, password, nib);
    }

    public void submitArticle(Repository repository, Article article) {
        if(repository.getArticle(article) == null)
            repository.addArticle(article);
        else
            System.out.println("O artigo já foi submetido");
    }

    public Article writeArticle(String title, String content){
        return new Article(title,content,this);
    }

    public void editArticle(Article article,String content){
        if(article.hasAutor(this) && !article.isClosed())
            article.setContent(content);
        else
            System.out.println("Não pode editar artigos de outros autores");
    }

    public void reeditArticle(Article article,String content){
        if(article.hasAutor(this) && article.isClosed())
            article.setContent(content);
        else
            System.out.println("Não pode editar artigos de outros autores");
    }


}
