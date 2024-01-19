package presentation;

import article.Article;
import user.Autor;
import user.Organizador;
import user.User;

import java.util.LinkedList;

public class Presentation {
    private int num;
    private boolean done = false;
    private Article article;
    private Organizador organizador;
    private LinkedList<User> presenters;
    private LinkedList<User> participants = new LinkedList<>();


    public Presentation(Article article,Organizador organizador){
        this.article = article;
        this.organizador = organizador;
        presenters = article.getAutors();
    }

    public void register(User user){
        if(!participants.contains(user))
            participants.add(user);
        else
            System.out.println("Já se encontra registado na conferencia");
    }

    public void listPresenters(){
        System.out.println("Lista de Apresentadores: ");
        for(User user : presenters)
            System.out.println(user);
    }

    public void listParticipants(){
        System.out.println("Lista de Participantes");
        for(User user : participants)
            System.out.println(user);
    }

    public Article getArticle() {
        return article;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public LinkedList<User> getPresenters() {
        return presenters;
    }

    public LinkedList<User> getParticipants() {
        return participants;
    }

    public void close(){
        done = true;
    }

    public boolean isDone(){
        return done;
    }

    public String toString(){
        return "APRESENTAÇÃO: \nArtigo: " +article.getTitle() +"\nApresentadores: " +presenters;
    }

}
