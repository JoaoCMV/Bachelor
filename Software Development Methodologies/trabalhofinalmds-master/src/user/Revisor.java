package user;

import article.Article;
import article.Review;
import repository.Repository;

import java.util.LinkedList;

public class Revisor extends User{

    private LinkedList<Review> reviewsList = new LinkedList<>();

    public Revisor(String name, String email, int nif,String password, int nib) {
        super(name,email,nif,UserRole.REVISOR,password,nib);
    }

    public void submitArticle(Repository repository, Article article){
        repository.addArticle(article);
    }


    public void submitReview(Repository repository, Review review){
        review.close();
        repository.addReview(review);
    }

    public Review writeReview(String critic, int rating){
        return new Review(this,rating,critic);
    }

    private Review reviewArticle(Article article,Review review){
        if(article.hasRevisor(this)) {
            article.addReview(review);
            review.setArticle(article);
            addReview(review);
        }else{
            System.out.println("Não tem permissão para rever este artigo");
        }
        return review;
    }

    public void addReview(Review review){
        if(!reviewsList.contains(review)){
            reviewsList.add(review);
        }
    }

    public void closeReview(Review review){
        review.close();
    }

    public void reviewArticle(Article article,int rating,String critic){
        closeReview(reviewArticle(article,new Review(this,rating,critic)));
    }
}