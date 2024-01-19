package repository;

import java.util.LinkedList;
import java.util.List;
import pt.uevora.Aluno;
import pt.uevora.Docente;
import pt.uevora.Estagio;
import pt.uevora.Empresa;

public class Repositorio {
    
    // São utilizadas apenas LinkedList para BD do sistema
    private LinkedList<Estagio> listaEstagios = new LinkedList<>();
    private LinkedList<Aluno> listaAlunos = new LinkedList<>();
    private LinkedList<Docente> listaDocentes = new LinkedList<>();
    private LinkedList<Empresa> listaEmpresas = new LinkedList<>();
    
    
    public LinkedList<Estagio> getListaEstagios() {
        return listaEstagios;
    }

    public void setListaEstagios(LinkedList<Estagio> listaEstagios) {
        this.listaEstagios = listaEstagios;
    }

    public LinkedList<Aluno> getListaAlunos() {
        return listaAlunos;
    }

    public void setListaAlunos(LinkedList<Aluno> listaAlunos) {
        this.listaAlunos = listaAlunos;
    }

    public LinkedList<Docente> getListaDocentes() {
        return listaDocentes;
    }

    public void setListaDocentes(LinkedList<Docente> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }
    
    public LinkedList<Empresa> getListaEmpresas() {
        return listaEmpresas;
    }

    public void setListaEmpresas(LinkedList<Empresa> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    // procura na lista de utilizadores os que são alunos
    public LinkedList<Aluno> listaAlunos() {
        return listaAlunos;
    }  

    public void guardarNota(int nota, Aluno a) {
        // Procura o aluno na lista de alunos
        int i = listaAlunos.indexOf(a);
        this.listaAlunos.get(i).setNotaOrientador(nota);
        
    }
    
    public void addAluno(Aluno a){
        a.setUsern(listaAlunos.size() + listaDocentes.size() + 1);      //Atualiza o numero do user
        this.listaAlunos.add(a);
    }

    public void addEstagio(Estagio e){
        this.listaEstagios.add(e);
    }

    public void addEmpresa(Empresa empresa) {
        this.listaEmpresas.add(empresa);
    }

    public Aluno getAluno(Aluno a) {
        int i = this.listaAlunos.indexOf(a);
        return this.listaAlunos.get(i);
    }

    public Object getEmpresa(Empresa e) {
        int i = this.listaEmpresas.indexOf(e);
        return this.listaEmpresas.get(i);
    }

    public Estagio getEstagio(Estagio est) {
        int i = this.listaEstagios.indexOf(est);
        return this.listaEstagios.get(i);
    }

    public void adicionarEstagio(Aluno a, Estagio e) {
        int i = listaAlunos.indexOf(a);
        this.listaAlunos.get(i).setEstagio(e);  
    }
}

