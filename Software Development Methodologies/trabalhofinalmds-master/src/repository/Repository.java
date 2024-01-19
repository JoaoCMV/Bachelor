package repository;

import article.Article;
import article.Review;
import user.*;


import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Repository {

    private LinkedList<User> userList = new LinkedList<>();
    private LinkedList<Article> articleList = new LinkedList<>();
    private LinkedList<Review> reviewList = new LinkedList<>();

    //Recebe um utilizador sem um numero atribuido
    public void addUser(User user) {
        user.setUserNum(userList.size() + 1);
        userList.add(user);
    }

    //Recebe um artigo sem um numero atribuido
    public void addArticle(Article article) {
        article.setNum(articleList.size()+1);
        articleList.add(article);
    }

    public void addReview(Review review){
        review.setNum(reviewList.size()+1);
        reviewList.add(review);
    }


    public User getUserByName(String name) {
        for (User user : userList)
            if (user.getName().equals(name))
                return user;
        return null;
    }

    public Article getArticle(Article article) {
        for (Article aux : articleList)
            if (aux == article)
                return article;

       return null;
    }

    public void setMinRating(int minRating){
        for(Article article : articleList)
            article.setMinRating(minRating);
    }

    public void listArticles(){
        for (Article article : articleList)
            System.out.println(article);
    }

    public void listUsers(){
        for(User user : userList)
            System.out.println(user);
    }

    public Article getArticleByTitle(String title){
        for(Article article : articleList)
            if(article.getTitle().equals(title))
                return article;

        System.out.println("Não encontrou artigo");
        return null;
    }

    public void listUsersByRole(UserRole userRole){
        System.out.println("Lista de " +userRole);
        for(User user : userList)
            if(user.getUserRole() == userRole)
                System.out.println(user);
    }

    public LinkedList<User> getUserList() {
        return userList;
    }

    public LinkedList<Article> getArticleList() {
        return articleList;
    }

    public LinkedList<Review> getReviewList() {
        return reviewList;
    }
}
