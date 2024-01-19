
package pt.uevora;

import java.util.List;
import repository.Repositorio;

public class Estagio {
    
    
    private Empresa e;
    private String objetivo;
    private String ferramentas;
    private String tipo;
    private int pagamento;
    private int vagas;
    private String orientador;

    public Estagio(Empresa e, String objetivo, String ferramentas, String tipo, int pagamento, int vagas, String orientador) {
        this.e = e;
        this.objetivo = objetivo;
        this.ferramentas = ferramentas;
        this.tipo = tipo;
        this.pagamento = pagamento;
        this.vagas = vagas;
        this.orientador = orientador;
    }
    
    // Obtem a lista de alunos
    public List<Aluno> getAlunos(Repositorio r){
        return r.listaAlunos();
    }
    
    // Guarda a nota do orientador para o aluno a
    public void guardarNota(int nota, Aluno a, Repositorio r){
        r.guardarNota(nota, a);
    }

    public Empresa getE() {
        return e;
    }

    public void setE(Empresa e) {
        this.e = e;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getFerramentas() {
        return ferramentas;
    }

    public void setFerramentas(String ferramentas) {
        this.ferramentas = ferramentas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPagamento() {
        return pagamento;
    }

    public void setPagamento(int pagamento) {
        this.pagamento = pagamento;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }

    public String getOrientador() {
        return orientador;
    }

    public void setOrientador(String orientador) {
        this.orientador = orientador;
    } 
    
}
