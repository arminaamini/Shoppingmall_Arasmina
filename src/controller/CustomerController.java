package controller;

import commons.OperationResult;
import dto.CartSummary;
import model.Customer;
import service.CartService;
import service.CheckOutService;
import view.CustomerMainFrame;

public class CustomerController {
    private final CustomerMainFrame view;
    private final CartService cartService;
    private final CheckOutService checkOutService;
    private final Customer customer;
    private final Runnable onLogout;



    public CustomerController(CustomerMainFrame view, CartService cartService,CheckOutService checkOutService, Customer customer, Runnable onLogout){
        this.view = view;
        this.cartService = cartService; 
        this.checkOutService = checkOutService;       
        this.customer = customer;
        this.onLogout = onLogout;

        wire();
        view.setBalanceText(String.valueOf(customer.getBalance()));
        refreshCart();
    }

    private void wire(){
        view.onAdd(this::handleAdd);
        view.onRemove(this::handleRemove);
        view.onPlus(() -> handleDelta(+1));
        view.onMinus(() -> handleDelta(-1));

        view.onClear(this::handleClear);
        view.onRefresh(this::refreshCart);

        view.onProfile(this::handleProfile);
        view.onLogout(this::handleLogout);
        view.onCheckOut(this::handleCheckOut);


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

private void handleDelta(int delta){
    String productId = view.getSelectedProductFromTable();
    if(productId == null){
        view.showError("Select an item. ");
        return;
    }

    OperationResult<CartSummary> result = cartService.changeQuantity(customer.getUserId(), productId, delta);

    if(!result.getSuccess()){
        view.showError(result.getMessage());
        return;
    }

    CartSummary summary = result.getData();
    view.setcartRows(summary.getCart().getItems());
    view.setTotalText(summary.getTotalPrice().toString());
    view.setBalanceText(String.valueOf(customer.getBalance()));

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