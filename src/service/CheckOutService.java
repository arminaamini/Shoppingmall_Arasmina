package service;

import java.math.BigDecimal;
import java.util.ArrayList;

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
        BigDecimal total = calculateTotal(cart);

        if(total.compareTo(BigDecimal.ZERO) == 0){
            return OperationResult.fail("cart is empty");
        }
        long totalLong = total.longValue();
        if(customer.getBalance() < totalLong){
            return OperationResult.fail("not enough balance. Need: " + totalLong
                +" , you have: "+ customer.getBalance()
            );
        }
        customer.withdraw(totalLong);
        userRepository.save(customer);
        cartRepository.clearCart(userId);

        Cart after = cartRepository.loadCart(userId);
        BigDecimal afterTotal = calculateTotal(after);
        return OperationResult.ok("checkout successfully", new CartSummary(after, afterTotal));

    }catch(RuntimeException e){
        e.printStackTrace();
        return OperationResult.fail("checkout due to system bug");
    }
}
private BigDecimal calculateTotal(Cart cart){
    BigDecimal total = BigDecimal.ZERO;
    if(cart.getItems() == null) return total;

    for(CartItem item : cart.getItems()){
        Product p = productRepository.findById(item.getProductId());
        if (p == null) continue;

        BigDecimal price = p.getPrice();
        total = total.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));   
        
    }
    return total;
}
}