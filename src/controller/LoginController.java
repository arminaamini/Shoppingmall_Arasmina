package controller;
import commons.OperationResult;
import model.Admin;
import model.Customer;
import service.AuthService;
import service.CartService;
import service.CheckOutService;
import view.CustomerMainFrame;
import view.LoginFrame;

public class LoginController{

    private final LoginFrame view;
    private final AuthService authService;
    private final CartService cartService;//USAGEEE
    private final CheckOutService checkOutService;
    

    public LoginController(LoginFrame view, AuthService authService, CartService cartService, CheckOutService checkOutService){
        this.view = view;
        this.authService = authService;
        this.cartService = cartService;
        this.checkOutService = checkOutService;
        wireEvents();
    }
    private void wireEvents(){
        view.onCustomerLogin(this :: handleCustomerLogin);
        view.onAdminLogin(this :: handleAdminLogin);
        view.onRegister(this :: handleRegister);

    }

    private void handleCustomerLogin(){
        String username = view.getUsernameInput();
        String password = view.getPasswordInput();
        OperationResult<Customer> result = authService.LoginCustomer(username, password);
        if(!result.getSuccess()){
            view.showError(result.getMessage());
            view.clearPassword();
            return;
        }
        Customer customer = result.getData();
        ////////////////////GPT///////
        System.out.println("UI received customer.balance = " + customer.getBalance());

        CustomerMainFrame cmf = new  CustomerMainFrame(customer.getUsername());
        new CustomerController(cmf, cartService,checkOutService, customer,
             () -> {
                view.clearPassword();
                view.setVisible(true);
             });
             cmf.setVisible(true);
             view.setVisible(false);
        
    //    public CustomerController(CustomerMainFrame view, CartService cartService,CheckOutService checkOutService, Customer customer, Runnable onLogout){


    }
    private void handleAdminLogin(){
        String username = view.getUsernameInput();
        String password = view.getPasswordInput();
        OperationResult<Admin> result = authService.LoginAdmin(username, password);
        if(!result.getSuccess()){
            view.showError(result.getMessage());
            view.clearPassword();
            return;
        }
        Admin admin = result.getData();
        view.showInfo(("welcome admin: " + admin.getUsername()));
        // AdminMainFrame 

    }
    private void handleRegister(){
        String username = view.getUsernameInput();
        String password = view.getPasswordInput();
        OperationResult<Customer> result = authService.register(username, password);
        if(!result.getSuccess()){
            view.showError(result.getMessage());
            view.clearPassword();
            return;
        }
        Customer customer = result.getData();
        CustomerMainFrame cmf = new  CustomerMainFrame(customer.getUsername());
        new CustomerController(cmf, cartService,  checkOutService, customer,
             () -> {
                view.clearPassword();
                view.setVisible(true);
             });
             cmf.setVisible(true);
             view.setVisible(false);
        
        // باید بره به صفحه مشتری. ایا همان CustomerMainFrame است؟
        
        
    }
    
}

   
