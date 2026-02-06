package model;

public class Admin extends User{

    public Admin(){super();};
    
    public Admin( String username, String hashedPassword ){
        super( username, hashedPassword, Role.ADMIN);
    }
}
