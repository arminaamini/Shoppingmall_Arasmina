package controller;
import commons.OperationResult;
import model.Admin;
import model.Customer;
import service.AuthService;
import service.CartService;
import service.CheckOutService;
import service.ProductService;
import view.AdminProductFrame;
import view.CustomerMainFrame;
import view.LoginFrame;
import view.ProductManagementPanel;

public class LoginController{

    private final LoginFrame view;
    private final AuthService authService;
    private final ProductService productService;
    private final CartService cartService;//USAGEEE
    private final CheckOutService checkOutService;
    

    public LoginController(LoginFrame view, AuthService authService,ProductService productService, CartService cartService, CheckOutService checkOutService){
        this.view = view;
        this.authService = authService;
        this.productService = productService;
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
        
        CustomerMainFrame cmf = new  CustomerMainFrame(customer.getUsername());
        new CustomerController(cmf, cartService,checkOutService,productService, customer,
             () -> {
                view.clearPassword();
                view.setVisible(true);
             });
             cmf.setVisible(true);
             view.setVisible(false);
        


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

        view.clearPassword();
        view.setVisible(false);

        AdminProductFrame adminFrame = new AdminProductFrame();
        ProductManagementPanel panel = new ProductManagementPanel();

        adminFrame.setContentPane(panel);
        new AdminProductController(adminFrame, panel, productService);
        adminFrame.setVisible(true);

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
        new CustomerController(cmf, cartService,  checkOutService,productService, customer,
             () -> {
                view.clearPassword();
                view.setVisible(true);
             });
             cmf.setVisible(true);
             view.setVisible(false);
        
        // باید بره به صفحه مشتری. ایا همان CustomerMainFrame است؟
        
        
    }
    
}

   
