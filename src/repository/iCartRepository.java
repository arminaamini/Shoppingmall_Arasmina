package repository;
import model.Cart;

public interface  iCartRepository {
    Cart loadCart(String userId);

    void saveCart(String userId, Cart cart);

    void clearCart(String userId);
    
}
