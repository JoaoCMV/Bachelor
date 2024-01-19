package tests;

import static org.junit.Assert.assertEquals;
import user.*;
import org.junit.Test;

public class userTests {

  @Test
  public void test() throws Exception {
    user.User u1 = new Revisor("Diogo", "@email", 1234, "pass", 123);
    user.User u2 = new User("Miguel", "@email", 1234, "pass", 123);
    user.User u3 = new Organizador("Joao", "@email", 1234, "pass", 123);
    user.User u4 = new Autor("Ze", "@email", 1234, "pass", 123);
    assertEquals(UserRole.REVISOR, u1.getUserRole());
    assertEquals("Diogo", u1.getName());
    assertEquals(UserRole.USER, u2.getUserRole());
    assertEquals("Miguel", u2.getName());
    assertEquals(UserRole.ORGNIZADOR, u3.getUserRole());
    assertEquals("Joao", u3.getName());
    assertEquals(UserRole.AUTOR, u4.getUserRole());
    assertEquals("Ze", u4.getName());

  }
}
