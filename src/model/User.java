package model;
import commons.util.PasswordHasherUtil;

public abstract class User {
    protected  String username;
    protected  String hashedPassword;
    protected  Role role;

    protected User(){}

    protected  User( String username, String hashedPassword,  Role role){
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    

    public String getUsername(){
        return username;
    }

    public Role getRole(){
        return role;
    }
    

    public void setUsername(String username){
        this.username = username;
    }

    public void setRole(Role role){
        this.role = role;
    }
    public void setPassword(String hashedPassword){
        this.hashedPassword = hashedPassword;

    }
    public boolean checkPassword(String inputPassword){
        return hashedPassword.equals(PasswordHasherUtil.hasher(inputPassword));
    }

    public void changePassword(String newPassword){
        this.hashedPassword = PasswordHasherUtil.hasher(newPassword);
    }
    public String getHashedPasswordForRepo(){
        return hashedPassword;
    }
}// 