package service;

import commons.OperationResult;
import commons.util.PasswordHasherUtil;
import java.util.Optional;
import java.util.UUID;
import model.Admin;
import model.Customer;
import model.Role;
import model.User;
import repository.iUserRepository;

public class AuthService {
    
   private final iUserRepository userRepository;
   //private final PasswordHasherUtil hasher;
   private User currentUser;

   public AuthService(iUserRepository userRepository){
    this.userRepository = userRepository;
    //this.hasher = hasher;
    
   }

   public OperationResult<User> loginBase(String username, String password){
        try{
            if(username == null || username.trim().isEmpty()) return  OperationResult.fail("username cannot ba empty");
            if(password == null || password.trim().isEmpty()) return  OperationResult.fail("password cannot ba empty");
//????????
            Optional<User> opt = userRepository.findByUsername(username.trim());
            if(opt.isEmpty()) return OperationResult.fail("user not found");
//?????????????
            User user = opt.get();

            if(!PasswordHasherUtil.hasher(password).equals(user.getHashedPasswordForRepo())){
                return OperationResult.fail("wrong password");
            }
            currentUser = user;
            return  OperationResult.ok( "login successfully", user);
        }
        catch(RuntimeException e){
            return OperationResult.fail("loginn failed due to system bug");
        }
    }

public OperationResult<Customer> LoginCustomer(String usrname, String password){
    OperationResult<User> base = loginBase(usrname, password);
    if(!base.getSuccess())return  OperationResult.fail(base.getMessage());

    User u = base.getData();
    if(u.getRole() != Role.CUSTOMER){
        logOut();
        return OperationResult.fail("not a customer");
    }
    if(!(u instanceof Customer)){
        logOut();
        return OperationResult.fail("invalid cusomer data");
    }
    return OperationResult.ok("customer login successful", (Customer) u);
}

public OperationResult<Admin> LoginAdmin(String usrname, String password){
    OperationResult<User> base = loginBase(usrname, password);
    if(!base.getSuccess())return  OperationResult.fail(base.getMessage());

    User u = base.getData();
    if(u.getRole() != Role.ADMIN){
        logOut();
        return OperationResult.fail("not an admin");
    }
    if(!(u instanceof Admin)){
        logOut();
        return OperationResult.fail("invalid admin data");
    }
    return OperationResult.ok("customer login successful", (Admin) u);
}

    public OperationResult<Customer> register(String username, String password){
        try{
            if(username == null || username.trim().isEmpty()) return  OperationResult.fail("username cannot ba empty");
            if(password == null || password.trim().isEmpty()) return  OperationResult.fail("password cannot ba empty");

            username = username.trim();
            Optional<User> exist = userRepository.findByUsername(username);
// یادت باشه لوپ در سوئینگ بنویسی تا وقتی یوزنیم غیرتکراری نداده
            if(exist.isPresent()) return OperationResult.fail("username exists");

            Customer customer = new Customer(UUID.randomUUID().toString(),username,PasswordHasherUtil.hasher(password),1000000L);
      
            userRepository.save(customer);

            currentUser = customer;
            /////////////GPT////////////////////////
            System.out.println("REGISTER customer.balance = " + customer.getBalance());

            return OperationResult.ok( "registration succussfully", customer);

            }
        catch(RuntimeException e){
            e.printStackTrace();
            return OperationResult.fail("registration failed due to system bug");
    }

}
        public void logOut(){currentUser = null;}
        public User getCurrentUser(){
            return  currentUser;
        }
}