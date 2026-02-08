package controller;



import commons.OperationResult;
import dto.CartSummary;
import model.Customer;
import service.CartService;
import service.CheckOutService;
import service.ProductService;
import view.CustomerMainFrame;
import model.Product;
import java.util.List;

public class CustomerController {
    private final CustomerMainFrame view;
    private final CartService cartService;
    private final CheckOutService checkOutService;
    private final ProductService productService;
    private final Customer customer;
    private final Runnable onLogout;



    public CustomerController(CustomerMainFrame view, CartService cartService,CheckOutService checkOutService,ProductService productService, Customer customer, Runnable onLogout){
        this.view = view;
        this.cartService = cartService; 
        this.checkOutService = checkOutService;   
        this.productService = productService;    
        this.customer = customer;
        this.onLogout = onLogout;

        wire();
        view.setBalanceText(String.valueOf(customer.getBalance()));
        refreshCart();
    }

    private void wire(){
        view.onAdd(this::handleAdd);
        view.onRemove(this::handleRemove);
       

        view.onClear(this::handleClear);
        view.onRefresh(this::refreshCart);

        view.onProfile(this::handleProfile);
        view.onLogout(this::handleLogout);
        view.onCheckOut(this::handleCheckOut);
        view.onShowProduct(this::handleShowProduct);


    }

    private void handleAdd(){
        String productId = view.getProductIdInput();
        int quantity = view.getQuantityInput();

        OperationResult<CartSummary> result = cartService.addToCart(customer.getUserId(), productId, quantity);

        if(!result.getSuccess()){
            view.showError(result.getMessage());
            return;
        }
        CartSummary summary = result.getData();
        view.setcartRows(summary.getCart().getItems());
        view.setTotalText(summary.getTotalPrice().toString());
        view.setBalanceText(String.valueOf(customer.getBalance()));
        view.showInfo("Added to cart. ");
    }
    private void handleRemove(){
        String productId = view.getSelectedProductFromTable();
        if(productId == null){
            view.showError("Select a row from cart table first. ");
            return;
        }
    
    OperationResult<CartSummary> result = cartService.removeFromCart(customer.getUserId(), productId);

    if(!result.getSuccess()){
        view.showError(result.getMessage());
        return;
    }
    
    CartSummary summary = result.getData();
    view.setcartRows(summary.getCart().getItems());
    view.setTotalText(summary.getTotalPrice().toString());
    view.setBalanceText(String.valueOf(customer.getBalance()));
    view.showInfo("Removed to cart. ");
    
}


private void handleClear(){
    
    OperationResult<CartSummary> result = cartService.clearCart(customer.getUserId());

    if(!result.getSuccess()){
        view.showError(result.getMessage());
        return;
    }

    CartSummary summary = result.getData();
    view.setcartRows(summary.getCart().getItems());
    view.setTotalText(summary.getTotalPrice().toString());
    view.setBalanceText(String.valueOf(customer.getBalance()));
    view.showInfo("cart cleared. ");


}

private void handleCheckOut(){
    
    OperationResult<CartSummary> result = checkOutService.checkOut(customer);

    if(!result.getSuccess()){
        view.showError(result.getMessage());
        return;
    }

    CartSummary summary = result.getData();
    view.setcartRows(summary.getCart().getItems());
    view.setTotalText(summary.getTotalPrice().toString());
    view.setBalanceText(String.valueOf(customer.getBalance()));
    view.showInfo("Checkout succussful. new balance: " + customer.getBalance());


}

private void handleShowProduct(){
    List<Product> products = productService.getProducts();

    StringBuilder sb = new StringBuilder();
    for(Product p : products){
        sb.append(p.getProductId())
        .append(" | ")
        .append(p.getName())
        .append(" -price: ")
        .append(p.getPrice())
        .append(" -Stock: ")
        .append(p.getStockQuantity())
        .append("\n");
        
    }
    view.showInfo(sb.toString());
}

private void refreshCart(){
    OperationResult<CartSummary> result = cartService.getCartSummary(customer.getUserId());
    if(!result.getSuccess()){
        view.showError(result.getMessage());
        return;
    }
    CartSummary summary = result.getData();
    view.setcartRows(summary.getCart().getItems());
    view.setTotalText(summary.getTotalPrice().toString());
}
private void handleProfile(){
    view.showProfile(customer.getUsername(), customer.getRole().name(), customer.getBalance());
}
private void handleLogout(){
    view.dispose();
    onLogout.run();
}
}