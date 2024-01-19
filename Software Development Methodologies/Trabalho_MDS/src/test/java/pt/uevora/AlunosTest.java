
package pt.uevora;

import java.util.LinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import repository.Repositorio;

public class AlunosTest {
    
    @Test
    public void Candidaturas(){
        
        Aluno a = new Aluno(12,"Helder Pustiga", "lls", "olaola");
        Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
        Estagio est1 = new Estagio(e, "estagiar", "java", "remunerado", 500, 3, "orient");
        Estagio est2 = new Estagio(e, "estagiar", "java", "remunerado", 500, 3, "orient");
        a.candidatar(est1);
        a.candidatar(est2);
        LinkedList<Estagio> le = new LinkedList();
        le.add(est1);
        le.add(est2);
        assertEquals(le, a.getCandidaturas());
        
    }
}
