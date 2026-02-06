package dto;


import model.Cart;
import java.math.BigDecimal;
// صرفا برای انتقال داده از یک لایه به لایه دیگر

public class CartSummary {
    private  final Cart cart;
    private final BigDecimal totalPrice;

    public CartSummary(Cart cart, BigDecimal totalPrice){
        this.cart = cart;
        this.totalPrice = totalPrice;
    }

public Cart getCart(){return cart;}
public BigDecimal getTotalPrice(){return totalPrice;}
}

