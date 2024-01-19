package tests;

import article.Article;
import article.Review;
import java.util.LinkedList;
import org.junit.Test;
import repository.Repository;
import user.Autor;
import user.Revisor;
import user.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RepositoryTest {

  Repository repositorio = new Repository();
  private LinkedList<User> userList = repositorio.getUserList();
  private LinkedList<Article> articleList = repositorio.getArticleList();
  private LinkedList<Review> reviewList = repositorio.getReviewList();
  Autor autor = new Autor("miguel","miguel@gma.com",1223,"olam",13134);
  Article article = new Article("Titulo","conteudo",autor);
  Revisor revisor = new Revisor("Diogo","gmail.com",4123,"boasd", 23412);
  Review review = new Review(revisor,4,"boa");

  @Test
  public void testAddUser() throws Exception{
    repositorio.addUser(autor);
    assertTrue(userList.contains(autor));

  }

  @Test
  public void testAddArticle() throws Exception{
    repositorio.addArticle(article);
    assertTrue(articleList.contains(article));

  }

  @Test
  public void testAddReview() throws Exception{
    repositorio.addReview(review);
    assertTrue(reviewList.contains(review));

  }

  @Test
  public void testGetUserByName() throws Exception{
    repositorio.addUser(revisor);
    assertEquals(revisor,repositorio.getUserByName("Diogo"));

  }

  @Test
  public void testGetArticleByTitle() throws Exception{
    repositorio.addArticle(article);
    assertEquals(article, repositorio.getArticleByTitle("Titulo"));
  }





}
