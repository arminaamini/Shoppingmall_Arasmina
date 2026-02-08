package view;

import controller.LoginController;

import repository.iUserRepository;
import repository.iCartRepository;
import repository.iProductRepository;

import repository.json.JsonUserRepository;
import repository.json.JsonCartRepository;
import repository.json.JsonProductRepository;

import service.AuthService;
import service.CartService;
import service.CheckOutService;
import service.ProductService;

import javax.swing.SwingUtilities;

public class MainFrame {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // ---------- repositories ----------
            iUserRepository userRepo = new JsonUserRepository();
            iCartRepository cartRepo = new JsonCartRepository();
            iProductRepository productRepo = new JsonProductRepository();

            // ---------- services ----------
            AuthService authService = new AuthService(userRepo);
            ProductService productService = new ProductService(productRepo);
            CartService cartService = new CartService(cartRepo, productRepo);
            CheckOutService checkOutService =
                    new CheckOutService(cartRepo, productRepo, userRepo);

            // ---------- view ----------
            LoginFrame loginFrame = new LoginFrame();

            // ---------- controller ----------
            new LoginController(
                    loginFrame,
                    authService,
                    productService,   // فقط برای admin (Customer استفاده نمی‌کند)
                    cartService,
                    checkOutService
            );

            // ---------- start ----------
            loginFrame.setVisible(true);
        });
    }
}