package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import article.Article;
import article.Review;
import org.junit.Test;
import repository.Repository;
import user.Autor;
import user.Revisor;

public class ArticleTest {

  Repository repositorio = new Repository();
  Autor a1 = new Autor("a", "a@.com", 9123,"pass",123);
  Revisor a2 = new Revisor("a2", "ola@gmai.com", 9211, "pea1", 1123);
  Autor a3 = new Autor("b", "a@a.com", 91523,"pa2ss",1234);
  Article art1 = new Article("Titulo","conteudo",a1);
  Review r1 = new Review(a2,4,"muito boa");
  Review r2 = new Review(a2,3,"muito boa");
  Review r3 = new Review(a2,5,"boa");

  @Test
  public void testApproved() throws Exception{

    assertFalse(art1.isApproved());
    art1.setApproved(true);
    assertTrue(art1.isApproved());
  }

  @Test
  public void testTitleAndContent() throws Exception{
    assertEquals("Titulo",art1.getTitle());
    art1.setContent("conteudo1");
    assertEquals("conteudo1",art1.getContent());
  }

  @Test
  public void testAddRevisor() throws Exception{
    art1.addRevisor(a2);
    assertTrue(art1.hasRevisor(a2));

  }

  @Test
  public void testAddAutor() throws Exception{

    assertTrue(art1.hasAutor(a1));
    assertFalse(art1.hasAutor(a3));
    art1.addAutor(a3);
    assertTrue(art1.hasAutor(a3));
  }

  @Test
  public void testReview() throws Exception{
    art1.addReview(r1);
    art1.addReview(r2);
    assertFalse(art1.canBeApproved());
    art1.addReview(r3);
    assertTrue(art1.canBeApproved());
    art1.setMinRating(5);
    assertFalse(art1.canBeApproved());
    assertEquals(3,art1.reviewCounter());
    assertEquals(4,art1.getAverage(),0.1);
  }



  }
