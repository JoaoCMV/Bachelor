package user;

import article.Article;
import presentation.Presentation;
import repository.Repository;

public class Organizador extends User{
    public Organizador(String name, String email, int nif,String password, int nib) {
        super(name,email,nif,UserRole.ORGNIZADOR,password,nib);
    }

    public void addRevisorToArticle(Revisor revisor, Article article){
        article.addRevisor(revisor);
    }

    public void setMinRating(int rating, Repository repository){
        repository.setMinRating(rating);
    }

    public void closeArticle(Article article){
        article.close();
    }

    public void openArticle(Article article){
        article.open();
    }


    public void approveArticle(Article article){
        if(article.canBeApproved())
            article.setApproved(true);
        else
            System.out.println("O ");
    }
    public Presentation createPresentation(Article article){
        if(article.isApproved())
            return new Presentation(article,this);
        else
            System.out.println("Não pode criar apresentações para artigos não aprovados");
        return null;
    }

    public void endPresentation(Presentation presentation){
        presentation.close();
    }



    public void listArticles(Repository repository){
        repository.listArticles();
    }

}
