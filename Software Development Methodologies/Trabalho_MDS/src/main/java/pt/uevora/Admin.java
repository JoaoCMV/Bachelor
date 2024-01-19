package pt.uevora;

import java.util.Date;
import repository.Repositorio;


public class Admin extends Docente{

    public Admin(String uc, String nome, String email, String password) {
        super(uc, nome, email, password);
    }
    
    public void atribuirEstagio(Aluno a, Estagio e, Repositorio r){
        r.adicionarEstagio(a,e);
    }
    
    public void setTutor(Docente d){
        System.out.println("NÃO SUPORTADO");
    }
    
    // Adiciona um aluno á plataforma
    public void registarAluno(Aluno a,Repositorio r){
        r.addAluno(a);
    }
    
    // Calcula a nota final do aluno consoante a notaOral e notaTutor registadas pelo Admin
    public void setNotaFinal(int notaTutor, int notaOral, Aluno a,Repositorio r){
        Aluno aluno = r.getAluno(a);
        int avg = (a.getNotaOrientador() + notaTutor + notaOral) / 3;
        a.setNotaFinal(avg);   
    }
    
    // Adiciona um estágio á plataforma
    // visto não ser preciso confirmação esta alteração não é implementada no admin mas sim 
    // logo na empresa quando esta pede "inscrição"
    public void setEstagio(Estagio e){       
    }
    // O mesmo se aplica, visto não haver necessidade de ser confirmado pelo departamento todas
    // as propostas são válidas e são aceites logo na "inscrição"
    public void registarEmpresa(Empresa e){
        
    }
    
    // Também não incluido o registo de reuniões
    public void marcarReuniao(Date d, Estagio e){
        
    }
       
    
}
