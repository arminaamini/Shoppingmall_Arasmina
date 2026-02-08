package service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import commons.OperationResult;
import dto.CartSummary;
import model.Cart;
import model.CartItem;
import model.Customer;
import model.Product;
import repository.iCartRepository;
import repository.iProductRepository;
import repository.iUserRepository;

public class CheckOutService {
    private final iCartRepository cartRepository;
    private final iProductRepository productRepository;
    private final iUserRepository userRepository;

    public CheckOutService(iCartRepository cartRepository, iProductRepository productRepository,iUserRepository userRepository){
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }            
    
public OperationResult<CartSummary> checkOut(Customer customer){
    try{
        if(customer == null) return OperationResult.fail("invalid customer");

        String userId = customer.getUserId();
        Cart cart = cartRepository.loadCart(userId);
        if(cart.getItems() == null) cart.setItems(new ArrayList<>());

        if(cart.getItems().isEmpty()){
            return OperationResult.fail("cart is empty");
        }

        List<Product> products = productRepository.findAll();
        if(products == null) products = new ArrayList<>();

        BigDecimal total = BigDecimal.ZERO;
        for(CartItem item : cart.getItems()){
            String pId = item.getProductId();
            int quantity = item.getQuantity();

            if(pId == null || pId.isBlank()){
                return OperationResult.fail("invalid product id in cart");
            }
        
        if( quantity <= 0 ){
            return OperationResult.fail("invalid product id in cart");
        }
        Product p = findProductInList(products, pId);
        if(p == null){
            return OperationResult.fail("product not found");
        }
        if(p.getStockQuantity() < quantity){
            return OperationResult.fail(
                "not enough stock" + (quantity - p.getStockQuantity()) +
                " more is needed." 
            );
        }
        BigDecimal price = p.getPrice();
        if(price == null){
            return OperationResult.fail("inavalid price for");
        }
        total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
    }
    if(total.compareTo(BigDecimal.ZERO) <= 0){
        return OperationResult.fail("cart is empty");
    }
    long totalLong = total.longValue();
    if(customer.getBalance() < totalLong){
        return OperationResult.fail("not enough balance");
    }

    for(CartItem item : cart.getItems()){
        Product p = findProductInList(products, item.getProductId());
        p.setStockQuantity(p.getStockQuantity() - item.getQuantity());
    }

    productRepository.saveAll((products));
    customer.withdraw(totalLong);
    userRepository.save(customer);
    cartRepository.clearCart(userId);

    Cart after = cartRepository.loadCart(userId);
    if(after.getItems() == null) after.setItems(new ArrayList<>());
    BigDecimal afterTotal = BigDecimal.ZERO;

    return OperationResult.ok("checkout successfully", new CartSummary(after, afterTotal));

    }catch(RuntimeException e){
        e.printStackTrace();
        return OperationResult.fail("checkout failed due to bug");
    }
}
    private Product findProductInList(List<Product> products, String productId){
        for(Product p : products){
            if(p.getProductId().equals((productId))) return p;
        }
        return null;
    }

}












