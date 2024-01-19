
package pt.uevora;

import java.util.LinkedList;
import java.util.List;


public class Aluno extends Utilizador{
    
    private int media;
    private String curso;
    private int notaFinal;
    private Estagio estagio;
    private Docente tutor;                  // Não incluido nos teste, (atribuição de tutores)
    private int notaOrientador;
    private LinkedList<Estagio> candidaturas = new LinkedList<>();

    public Aluno( int media, String nome, String email, String password) {
        super(nome, email, password);               // Teste de login não implementado, é logo criado o aluno
        this.media = media;
        
    }
    
    public void candidatar(Estagio e){
        candidaturas.add(e);
    }

    public LinkedList<Estagio> getCandidaturas() {
        return candidaturas;
    }

    public void setCandidaturas(LinkedList<Estagio> candidaturas) {
        this.candidaturas = candidaturas;
    }

    public int getNotaOrientador() {
        return notaOrientador;
    }

    public void setNotaOrientador(int notaOrientador) {
        this.notaOrientador = notaOrientador;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(int notaFinal) {
        this.notaFinal = notaFinal;
    }

    public Estagio getEstagio() {
        return estagio;
    }

    public void setEstagio(Estagio estagio) {
        this.estagio = estagio;
    }

    // Não implementado nos testes (ATRIBUIR TUTORES NÃO SUPORTADO)
    public Docente getTutor() {
        return tutor;
    }
    
    // Não implementado nos testes (ATRIBUIR TUTORES NÃO SUPORTADO)
    public void setTutor(Docente tutor) {
        this.tutor = tutor;
    }
    
    

    


}
