package tests;

import static org.junit.Assert.assertEquals;

import article.Article;
import org.junit.Test;
import user.*;
import repository.*;

public class AutorTest {


    Repository repositorio = new Repository();
    Autor a1 = new Autor("a", "a@.com", 9123,"pass",123);
    Autor a2 = new Autor("b", "a@.com", 91233,"passx",1123);
    Article art1 = new Article("Titulo","conteudo",a1);
    Article art2 = new Article("Titulo1","1conteudo",a2);
    @Test
    public void testSubmitArticle() throws Exception {

      a1.submitArticle(repositorio,art1);
      assertEquals(art1,repositorio.getArticleByTitle("Titulo"));

    }

    @Test
    public void testEditArticle() throws Exception {

      assertEquals("1conteudo",art2.getContent());
      a1.editArticle(art2,"ola");
      assertEquals("1conteudo",art2.getContent());
      assertEquals("conteudo",art1.getContent());
      a1.editArticle(art1,"adeus");
      assertEquals("adeus",art1.getContent());

    }

}
