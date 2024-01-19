package pt.uevora;

import java.util.Scanner;
import repository.Repositorio;

public class Exemplo {

    public static void main(String[] args) {
        
        Repositorio r = new Repositorio();
         Admin ad = new Admin("Informatica", "Joao", "ll", "sad");
         Empresa e = new Empresa("Empresa", "Responsavel", "Evora", "Trabalha...");
         Estagio est = new Estagio(e, "estagiar", "java", "remunerado", 500, 3, "Paulo");
         
        Scanner scanner = new Scanner(System.in);
        System.out.println("\tAdicionar Aluno...");       
        
        System.out.println("Nome:");
        String nome = scanner.nextLine();
        System.out.println("Email:");
        String email = scanner.nextLine();
        System.out.println("Password:");
        String pass = scanner.nextLine();
        System.out.println("Média:");
        int media = scanner.nextInt();

        System.out.println("Aluno :  " + nome + ", " + email + ", " + pass + ", media: " + media);
        
        // Cria o aluno
        Aluno a = new Aluno(media, nome, email, pass);
        ad.registarAluno(a, r);                         // Regista o aluno
        ad.atribuirEstagio(a, est, r);                  // Atribui o estágio ao aluno
        
        System.out.println("\tOrientador/Estágio");
        System.out.println("Nota estágio: ");
        int n_orientador = scanner.nextInt();
        
        est.guardarNota(n_orientador, a, r);            // Guarda a nota do Aluno
        
        System.out.println("\tAdmin");
        System.out.println("Nota Tutor: ");
        int n_tutor = scanner.nextInt();
        System.out.println("Nota Oral: ");
        int n_oral = scanner.nextInt();
        
        ad.setNotaFinal(n_tutor, n_oral, a, r);         // Guarda as notas de oral e tutor do aluno calculando a nota final
        
        int nota_final = r.getAluno(a).getNotaFinal();      // Retiroa do repositorio a nota final
        
        if(nota_final >= 9.5){
            System.out.println("Passou: " + nota_final);
        }else{
            System.out.println("Chumbou: " + nota_final);
        }
        
        
    }

}
