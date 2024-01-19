package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import org.junit.Test;
import article.Article;
import article.Review;
import org.hamcrest.Matcher;
import org.junit.Test;
import user.*;
import repository.*;



public class RevidorTestes {

  Repository repositorio = new Repository();
  private LinkedList<Review> reviewList = repositorio.getReviewList();
  Revisor r1 = new Revisor("miguel","awds.co",23434,"dsdfs",2343);
  Article art1 = new Article("ola","conteudo",r1);
  Revisor r2 = new Revisor("Diogo","asd.com",234,"sad",3242);
  Review  rev = new Review(r1,5,"muito boa");

  @Test
  public void testSetMinRating() throws Exception{

    r1.submitArticle(repositorio,art1);
    assertEquals(art1,repositorio.getArticleByTitle("ola"));

  }

  @Test
  public void testSubmitReview() throws Exception{

    r1.submitArticle(repositorio,art1);
    r2.submitReview(repositorio,rev);
    assertEquals(rev,reviewList.get(0));
  }

  


}
