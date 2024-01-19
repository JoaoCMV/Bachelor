
package pt.uevora;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
import repository.Repositorio;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EmpresaTest {
    
    @Test
    public void inscrever(){
        
        Repositorio r = new Repositorio();
        
        Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
        e.inscreverEmpresa(r);
        assertEquals(e, r.getEmpresa(e));
        
    }
    
    @Test
    public void submeterEstagio(){
        
        Repositorio r = new Repositorio();
        
        Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
        Estagio est2 = new Estagio(e, "estagiar", "java", "remunerado", 500, 3, "orient");
        e.subEstagio(est2, r);
        assertEquals(e, r.getEstagio(est2).getE());
        
    }
    
}
