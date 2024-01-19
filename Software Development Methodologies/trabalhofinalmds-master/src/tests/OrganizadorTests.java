package tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import article.Article;
import article.Review;
import org.hamcrest.Matcher;
import org.junit.Test;
import user.*;
import repository.*;
import presentation.*;



public class OrganizadorTests {

  Repository repositorio = new Repository();
  Autor a1 = new Autor("a", "a@.com", 9123,"pass",123);
  Revisor a2 = new Revisor("a2", "ola@gmai.com", 9211, "pea1", 1123);
  Autor a3 = new Autor("b", "a@a.com", 91523,"pa2ss",1234);
  Article art1 = new Article("Titulo","conteudo",a1);
  Review r1 = new Review(a2,4,"muito boa");
  Review r2 = new Review(a2,3,"muito boa");
  Review r3 = new Review(a2,5,"boa");
  Organizador o1 = new Organizador("miguel","oa@.c",23423,"weq",2342);


  @Test
  public void testAddRevisorToArticle() throws Exception {

    assertFalse(art1.getRevisors().contains(a2));
    o1.addRevisorToArticle(a2,art1);
    assertTrue(art1.getRevisors().contains(a2));



  }

  @Test
  public void testSetMinRating() throws Exception{

    o1.setMinRating(3,repositorio);
    assertEquals(3,art1.getMinRating());

  }

  @Test
  public void testCloseArticle() throws Exception{

    o1.closeArticle(art1);
    assertTrue(art1.isClosed());

  }

  @Test
  public void testOpenArticle() throws Exception{

    o1.openArticle(art1);
    assertFalse(art1.isClosed());

  }

  @Test
  public void testApprovedArticle() throws Exception{
    assertFalse(art1.isApproved());
    o1.approveArticle(art1);
    assertFalse(art1.isApproved());
    art1.addReview(r1);
    art1.addReview(r2);
    o1.approveArticle(art1);
    assertFalse(art1.isApproved());
    art1.addReview(r3);
    o1.approveArticle(art1);
    assertTrue(art1.isApproved());

  }



  @Test
  public void testCreatePresentation() throws Exception{

    art1.addReview(r1);
    art1.addReview(r2);
    art1.addReview(r3);
    o1.approveArticle(art1);
    o1.endPresentation(o1.createPresentation(art1));
  }




}
