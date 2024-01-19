package article;


import user.Revisor;

public class Review {
    public boolean reviewd = false;
    private  int rating;
    private String critic;
    private Revisor revisor;
    private Article article;
    private int num;

    public Review(Revisor revisor, int rating, String critic){
        this.rating = rating;
        this.critic = critic;
        this.revisor = revisor;
    }

    public void rate(int rating) throws Exception {
        if(rating < 1 || rating > 5)
            throw new Exception();
        else
            this.rating = rating;

    }

    public void addCritic(String critic){
        this.critic += critic;
    }

    public Revisor getRevisor(){
        return revisor;
    }

    public void setRevisor(Revisor revisor){
        this.revisor = revisor;
    }

    public int getRating(){
        return rating;
    }

    public String getCritic() {
        return critic;
    }

    public boolean isReviewd(){
        return reviewd;
    }

    public void close(){
        if(!isReviewd())
            reviewd = true;
        else{
            System.out.println("already reviewd");
        }
    }

    public void setArticle(Article article){
        this.article = article;
    }

    public void setNum(int num){
        this.num = num;
    }

    public String toString(){
        return "Review do artigo " +article.getTitle() +" por " +revisor.getName() +"RATING: " +rating +" CLOSED: " +reviewd;
    }
}
