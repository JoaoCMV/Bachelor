package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import article.Article;
import article.Review;
import org.junit.Test;
import user.*;
import repository.*;




public class ReviewTests {

  Repository repositorio = new Repository();
  Autor a1 = new Autor("a", "a@.com", 9123,"pass",123);
  Revisor a2 = new Revisor("a2", "ola@gmai.com", 9211, "pea1", 1123);
  Article art1 = new Article("Titulo","conteudo",a1);
  Review r1 = new Review(a2,4,"muito boa");

  @Test
  public void testReview() throws Exception {
    assertEquals(a2, r1.getRevisor());
    assertEquals(4,r1.getRating());
    assertEquals("muito boa",r1.getCritic());



  }

  @Test(expected = Exception.class)
  public void testRate6() throws Exception{
    r1.rate(6);
  }

  @Test(expected = Exception.class)
  public void testRate1() throws Exception{
    r1.rate(-1);
  }
  @Test
  public void testRate3() throws Exception{
    r1.rate(3);
    assertEquals(3,r1.getRating());
  }

  @Test
  public void TestAddCritic() throws Exception{
    r1.addCritic(" muito mau");
    assertEquals("muito boa muito mau", r1.getCritic());

  }

  @Test
  public void TestClose() throws Exception{
    assertFalse(r1.isReviewd());
    r1.close();
    assertTrue(r1.isReviewd());

  }


}
