import article.Article;
import article.Review;
import presentation.Presentation;
import repository.Repository;
import user.*;

public class Application {

    public Repository repository = new Repository();

    public <T extends User> T signUp(User user){
       repository.addUser(user);
       return (T)user;
    }

    public void listUsers(){
        repository.listUsers();
    }

    public void listArticles(){
        repository.listArticles();
    }

    public <T extends User> T loadUser(String name){
        return (T)repository.getUserByName(name);
    }


    public void start(){
        //Registar utilizadores
        signUp(new Revisor("revisor1","@email",1234,"pass",123));
        signUp(new Revisor("revisor2","@email",1234,"pass",123));
        signUp(new Revisor("revisor3","@email",1234,"pass",123));
        signUp(new Revisor("revisor4","@email",1234,"pass",123));
        signUp(new User("user1","@email",1234,"pass",123));
        signUp(new User("user2","@email",1234,"pass",123));
        signUp(new User("user3","@email",1234,"pass",123));
        signUp(new User("user4","@email",1234,"pass",123));
        signUp(new Organizador("org1","@email",1234,"pass",123));
        signUp(new Organizador("org2","@email",1234,"pass",123));
        signUp(new Organizador("org3","@email",1234,"pass",123));
        signUp(new Organizador("org4","@email",1234,"pass",123));
        signUp(new Autor("autor1","@email",1234,"pass",123));
        signUp(new Autor("autor2","@email",1234,"pass",123));
        signUp(new Autor("autor3","@email",1234,"pass",123));
        signUp(new Autor("autor4","@email",1234,"pass",123));

        //listUsers();

        Autor a1 = loadUser("autor1");
        Autor a2 = loadUser("autor2");
        Autor a3 = loadUser("autor3");

        Article art1 = a1.writeArticle("Titulo1","Conteudo1");
        a1.submitArticle(repository,art1);

        Article art2 = a2.writeArticle("Tituto2","Conteudo2");
        art2.addAutor(a3);
        a2.submitArticle(repository,art2);

        /*Submeter 2 vezes o mesmo artigo
        a3.submitArticle(repository,art2);
         */


        listArticles();

        Organizador org1 = loadUser("org1");

        org1.setMinRating(5,repository);
        listArticles();

        Revisor rev1 = loadUser("revisor1");
        Revisor rev2 = loadUser("revisor2");
        Revisor rev3 = loadUser("revisor3");

        /*Cenário: Revisores reveem artigo COM permissão e artigo é APROVADO*/
        org1.addRevisorToArticle(rev1,art1);
        org1.addRevisorToArticle(rev2,art1);
        org1.addRevisorToArticle(rev3,art1);

        rev1.reviewArticle(art1,3,"Mediocre");
        rev2.reviewArticle(art1,5,"Excelente");
        rev3.reviewArticle(art1,1,"Fraco");

        org1.approveArticle(art1);

        /*
        Cenário: Revisores reveem artigo SEM permissão
        rev1.reviewArticle(art1,3,"Mediocre");
        rev2.reviewArticle(art1,5,"Excelente");

        org1.approveArticle(art1);
         */

        /*
        Cenário: Revisores reveem artigo COM permissão e artigo é REPROVADO
        org1.addRevisorToArticle(rev1,art1);
        org1.addRevisorToArticle(rev2,art1);

        rev1.reviewArticle(art1,3,"Mediocre");
        rev2.reviewArticle(art1,5,"Excelente");

        org1.approveArticle(art1);
         */


        Presentation presentation = org1.createPresentation(art1);
        System.out.println(presentation);

    }

    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }

}
