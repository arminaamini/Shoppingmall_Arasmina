package view;

import controller.LoginController;
import repository.iCartRepository;
import repository.iProductRepository;
import repository.iUserRepository;
import repository.json.JsonProductRepository;
import repository.json.JsonCartRepository;
import repository.json.JsonUserRepository;
import service.AuthService;
import service.CartService;
import service.CheckOutService;
import repository.json.JsonProductRepository;
import service.ProductService;

import javax.swing.*;

public class MainFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // repositories
            iUserRepository userRepo = new JsonUserRepository();
            iCartRepository cartRepo = new JsonCartRepository();
            iProductRepository productRepo = new JsonProductRepository("data/products.json");
            //iProductRepository productRepo = new JsonProductRepository(); // موقت

            // services
            AuthService authService = new AuthService(userRepo);
            ProductService productService = new ProductService(productRepo);
            CartService cartService = new CartService(cartRepo, productRepo);
            CheckOutService checkOutService = new CheckOutService(cartRepo, productRepo, userRepo);

            // view
            LoginFrame loginFrame = new LoginFrame();

            // controller
            new LoginController(loginFrame, authService, cartService, checkOutService);
        
            loginFrame.setVisible(true);
        });
    }
}

