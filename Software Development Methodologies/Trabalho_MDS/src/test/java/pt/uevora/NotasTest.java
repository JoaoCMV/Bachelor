package pt.uevora;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import repository.Repositorio;

public class NotasTest {
    
    @Test
    public void testNotaOrientador() throws Exception {
        
      Repositorio r = new Repositorio();
      Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
      Estagio est = new Estagio(e, "estagiar", "java", "remunerado", 500, 3, "orient");
      Aluno a = new Aluno(12,"Helder Pustiga", "lls", "olaola");
      Admin ad = new Admin("Informatica", "Joao", "ll", "sad");
         
      ad.registarAluno(a, r);
      est.guardarNota(15, a, r);
      Aluno t = r.getAluno(a);     
      assertEquals(15, t.getNotaOrientador());
        
    }

}