
package pt.uevora;

import java.util.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import repository.Repositorio;

public class AdminTest {
    
    @Test
    public void RegistarAluno(){
        
        Repositorio r = new Repositorio();
        Aluno a = new Aluno(12,"Helder Pustiga", "lls", "olaola");
        Admin ad = new Admin("Informatica", "Joao", "ll", "sad");
        ad.registarAluno(a, r);
        assertEquals(a, r.getAluno(a));     
    }
    
     @Test
    public void testNotaFinal() throws Exception {
        
      Repositorio r = new Repositorio();
      Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
      Estagio est = new Estagio(e, "estagiar", "java", "remunerado", 500, 3, "orient");
      Aluno a = new Aluno(12,"Helder Pustiga", "lls", "olaola");
      Admin ad = new Admin("Informatica", "Joao", "ll", "sad");
         
      ad.registarAluno(a, r);
      est.guardarNota(10, a, r);
      ad.setNotaFinal(10, 10, a, r);
      Aluno t = r.getAluno(a);     
      assertEquals(10, t.getNotaFinal());
        
    }
    
    
    @Test
    public void testAdicionarEstagio() throws Exception {
        
      Repositorio r = new Repositorio();
      Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
      Estagio est = new Estagio(e, "estagiar", "java", "remunerado", 500, 3, "orient");
      Aluno a = new Aluno(12,"Helder Pustiga", "lls", "olaola");
      Admin ad = new Admin("Informatica", "Joao", "ll", "sad");
         
      ad.registarAluno(a, r);
      ad.atribuirEstagio(a, est, r);
      Aluno t = r.getAluno(a);     
      assertEquals(est, t.getEstagio());
        
    }
}
