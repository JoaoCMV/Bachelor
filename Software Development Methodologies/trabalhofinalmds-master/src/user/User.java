package user;

import presentation.Presentation;

public class User {
    private String name;
    private String email;
    private int nif;
    private UserRole userRole;
    private String password;
    private int nib;
    private int userNum;

    public User(String name, String email, int nif,String password, int nib, int userNum) {
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.userRole = UserRole.USER;
        this.password = password;
        this.nib = nib;
        this.userNum = userNum;
    }

    public User(String name, String email, int nif, String password, int nib) {
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.userRole = UserRole.USER;
        this.password = password;
        this.nib = nib;
    }

    public User(String name, String email, int nif, UserRole userRole,String password, int nib){
        this.name = name;
        this.email = email;
        this.nif = nif;
        this.userRole = userRole;
        this.password = password;
        this.nib = nib;
    }

    public void registerPresentation(Presentation presentation){
        presentation.register(this);
    }

    public void setUserNum(int num){
        this.userNum = num;
    }

    public String getEmail() {
        return email;
    }

    public int getNif() {
        return nif;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public String getPassword() {
        return password;
    }

    public int getNib() {
        return nib;
    }

    public int getUserNum() {
        return userNum;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return "Nome: " +name +" Num: " +userNum +" Role: " +userRole;
    }

    //Todos os utilizadores se podem inscrever para ver uma apresentação
}
