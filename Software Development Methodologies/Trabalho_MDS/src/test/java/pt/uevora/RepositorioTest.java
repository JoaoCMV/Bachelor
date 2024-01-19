
package pt.uevora;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import repository.Repositorio;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RepositorioTest {
    
    @Test
    public void get_addAlunoTest(){
        
        Repositorio r = new Repositorio();
        Aluno a = new Aluno(12,"Helder Pustiga", "lls", "olaola");
        r.addAluno(a);
        assertEquals(a,r.getAluno(a));
        
    }
    
    @Test
    public void get_guardarnotaTest(){
        
        Repositorio r = new Repositorio();
        Aluno a = new Aluno(12,"Helder Pustiga", "lls", "olaola");
        r.addAluno(a);
        r.guardarNota(17, a);
        assertEquals(17, r.getAluno(a).getNotaOrientador());
        
    }
    
    @Test
    public void get_addEstagioTest(){
        
        Repositorio r = new Repositorio();
        Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
        Estagio est = new Estagio(e, "estagiar", "java", "remunerado", 500, 3, "orient");
        r.addEstagio(est);
        assertEquals(est,r.getEstagio(est));
        
    }
    
    @Test
    public void get_addEmpresaTest(){
        
        Repositorio r = new Repositorio();
        Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
        r.addEmpresa(e);
        assertEquals(e,r.getEmpresa(e));
        
    }
    
    @Test
    public void adicionarEstagioTest() {
        
        Repositorio r = new Repositorio();
        Aluno a = new Aluno(12,"Helder Pustiga", "lls", "olaola");
        Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
        Estagio est = new Estagio(e, "estagiar", "java", "remunerado", 500, 3, "orient");
        r.addAluno(a);
        r.adicionarEstagio(a, est);
        assertEquals(est,r.getAluno(a).getEstagio());
        
    }
}
