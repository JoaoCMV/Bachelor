package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import article.Article;
import article.Review;
import java.util.LinkedList;
import org.junit.Test;
import presentation.Presentation;
import repository.Repository;
import user.*;

public class PresentationTests {

  Autor a1 = new Autor("a", "a@.com", 9123,"pass",123);
  Revisor a2 = new Revisor("a2", "ola@gmai.com", 9211, "pea1", 1123);
  Article art1 = new Article("Titulo","conteudo",a1);
  Article art2 = new Article("Titulo2","conteudo2",a2);
  Organizador o1 = new Organizador("miguel","miguel@gmail.com",3123,"hedl",352);
  Presentation presentation = new Presentation(art1,o1);
  private LinkedList<User> presenters;
  private LinkedList<User> participants = presentation.getParticipants();


  @Test
  public void testRegister() throws Exception {

    presentation.register(a1);
    assertTrue(participants.contains(a1));
    assertFalse(participants.contains(a2));


  }

  @Test
  public void testGet() throws Exception {

    assertEquals(art1,presentation.getArticle());
    assertEquals(o1,presentation.getOrganizador());
    assertEquals(a1,presentation.getPresenters().get(0));
    presentation.register(a2);
    assertEquals(a2,presentation.getParticipants().get(0));
    presentation.register(a1);
    assertEquals(a1,presentation.getParticipants().get(1));



  }

}
