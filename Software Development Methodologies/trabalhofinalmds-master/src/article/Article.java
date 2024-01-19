package article;

import user.Autor;
import user.Revisor;
import user.User;

import java.util.LinkedList;

public class Article {
    private String title;
    private String content;
    private boolean closed = false;
    private int num;
    private int minRating = 3;
    private boolean approved;
    private LinkedList<User> autors = new LinkedList<>();
    private LinkedList<Review> reviews = new LinkedList<>();
    private LinkedList<Revisor> revisors = new LinkedList<>();


    public Article(String title, String content, LinkedList<User> autors) {
        this.title = title;
        this.content = content;
        this.autors = autors;
    }

    public Article(String title, String content, User autor) {
        this.title = title;
        this.content = content;
        autors.add(autor);
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void addAutor(Autor autor) {
        if (!autors.contains(autor))
            autors.add(autor);
    }

    public void setContent(String content){
        this.content = content;
    }

    public boolean hasAutor(Autor autor){
        return autors.contains(autor);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public String getTitle() {
        return title;
    }

    public void addRevisor(Revisor revisor){
        if(!revisors.contains(revisor))
            revisors.add(revisor);
        else
            System.out.println("Este revisor já está atribuido a este artigo");
    }

    public int reviewCounter(){
        return reviews.size();
    }

    public void close(){
        closed = true;
    }

    public void open(){
        closed = false;
    }

    public boolean isClosed(){
        return closed;
    }

    public double getAverage(){
        double average = 0;
        for(Review review : reviews)
            average += review.getRating();
        return average / reviewCounter();
    }

    public boolean hasRevisor(Revisor revisor){
        return revisors.contains(revisor);
    }

    public String getContent() {
        return content;
    }

    public LinkedList<Review> getReviews() {
        return reviews;
    }

    public LinkedList<Revisor> getRevisors() {
        return revisors;
    }

    public int getMinRating(){return minRating;}

    public void setMinRating(int minRating){
        this.minRating = minRating;
    }

    public boolean canBeApproved(){
        if(reviewCounter() < 3)
            System.out.println("Não foi revisto o suficiente");

        if(getAverage() < minRating)
            System.out.println("Não obteve nota minima :(");



        return reviewCounter() >=3 && getAverage() >= minRating;
    }

    public boolean isApproved(){return approved;}

    public void setApproved(boolean approved){
        if(approved){
            System.out.println("Artigo APROVADO com NOTA: " +getAverage());
            close();
        }
        this.approved = approved;
    }

    public LinkedList<User> getAutors(){
        return autors;
    }

    public String toString() {
        String status;
        if(approved)
            status = "APROVADO";
        else
            if(isClosed())
                status = "REPROVADO";
            else
                status = "EM ESPERA";
        return "Titulo: " + title + " Autores: " + autors.toString() +" STATUS: " +status +minRating;
    }
}
