
package pt.uevora;

import java.util.List;

public class Docente extends Utilizador{
    
    private String uc;
    private List<Aluno> alunosResponsavel;      // Não incluido nos teste, (atribuição de tutores)

    public Docente(String uc, String nome, String email, String password) {
        super(nome, email, password);           // Teste de login não implementado, é logo criado o aluno
        this.uc = uc;
    }

    public String getUc() {
        return uc;
    }

    public List<Aluno> getAlunosResponsavel() {
        return alunosResponsavel;
    }

    public void setUc(String uc) {
        this.uc = uc;
    }

    public void setAlunosResponsavel(List<Aluno> alunosResponsavel) {
        this.alunosResponsavel = alunosResponsavel;
    }
    
    
}
