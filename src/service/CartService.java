package service;
import  commons.OperationResult;
import dto.CartSummary;
import java.math.BigDecimal;
import java.util.ArrayList;

import model.Cart;
import model.CartItem;
import model.Product;

import repository.iCartRepository;
import repository.iProductRepository;

public class CartService {
    private final iCartRepository cartRepository;
    private final iProductRepository productRepository;

    public CartService(iCartRepository cartRepository, iProductRepository productRepository){
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public OperationResult<CartSummary> addToCart(String userId, String productId, int quantity){
        try{
            if (userId == null || userId.isEmpty())return OperationResult.fail("invalid user");
            if (productId == null || productId.isEmpty())return OperationResult.fail("invalid product");
            if(quantity <= 0) return  OperationResult.fail("quantity must be positive");
//پیاده سازی متد در پروداکت ریپو

            Product p = productRepository.findById(productId.trim());
            if(p == null ) return OperationResult.fail("product not found");
            
            if(quantity > p.getStockQuantity()){
                return OperationResult.fail("not enough stock. Available: " + p.getStockQuantity());
            }
            Cart cart = cartRepository.loadCart((userId));

            CartItem existingItem = null;
            for(CartItem item : cart.getItems()){
                if(productId.equals(item.getProductId())){
                    existingItem = item;
                    break;
                }
            }
            int newQuantity = quantity;
            if(existingItem != null){
                newQuantity = existingItem.getQuantity() + quantity;
            }
            if(newQuantity > p.getStockQuantity()){
                return OperationResult.fail("not enough stock for total quantity. Available: " + p.getStockQuantity());
            }
            if(existingItem != null){
                existingItem.setQuantity(newQuantity);
            }
            else{
                cart.getItems().add(new CartItem(productId, quantity));
            }
            cartRepository.saveCart(userId, cart);

            BigDecimal totalPrice = calculatetotal(cart);
            return OperationResult.ok( "item added", new CartSummary(cart, totalPrice));

        }catch(RuntimeException e){
            return OperationResult.fail("cart operation failed due to system bug");
        }
    }

    public OperationResult<CartSummary> removeFromCart(String userId, String productId){
        try{
            if (userId == null || userId.isEmpty())return OperationResult.fail("invalid user");
            if (productId == null || productId.isEmpty())return OperationResult.fail("invalid product");

            Cart cart = cartRepository.loadCart(userId);

            boolean removed = cart.getItems().removeIf(i -> productId.equals(i.getProductId()));
            if(!removed) return  OperationResult.fail("product nit found");

            cartRepository.saveCart(userId, cart);
            BigDecimal totalPrice = calculatetotal(cart);
            return  OperationResult.ok("removed successfully", new CartSummary(cart, totalPrice));

        }catch(RuntimeException e){
            return OperationResult.fail("cart operation failed due to system bug");
        }
    }

    public OperationResult<CartSummary> getCartSummary(String userId){
        try{
            if (userId == null || userId.isEmpty())return OperationResult.fail("invalid user");
            Cart cart = cartRepository.loadCart(userId);
            if(cart.getItems() == null ) cart.setItems(new ArrayList<>());
            return OperationResult.ok(new CartSummary(cart, calculatetotal(cart)));
            
        }catch(RuntimeException e){
            return OperationResult.fail("cannot load due to system bug");
        }
    }
    
    private  BigDecimal calculatetotal(Cart cart){
        BigDecimal total = BigDecimal.ZERO;

        if(cart.getItems() == null){return total;}

        for(CartItem item : cart.getItems()){
            Product p = productRepository.findById(item.getProductId());
            if(p == null) continue;
            BigDecimal price = p.getPrice();
            BigDecimal totalItemPrice = price.multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(totalItemPrice);
        }
        return total;
    }

    public OperationResult<CartSummary> clearCart(String userId){
        try{
            if (userId == null || userId.isEmpty())return OperationResult.fail("invalid user");
            cartRepository.clearCart(userId);
            Cart cart = cartRepository.loadCart(userId);

            BigDecimal totalPrice = calculatetotal(cart);
            return  OperationResult.ok("removed successfully", new CartSummary(cart, totalPrice));

        }catch(RuntimeException e){
            return OperationResult.fail("cart operation failed due to system bug");
        }
    }
    public OperationResult<CartSummary> changeQuantity(String userId, String productId, int delta){
        try{
            if (userId == null || userId.isEmpty())return OperationResult.fail("invalid user");
            if (productId == null || productId.isEmpty())return OperationResult.fail("invalid product");
            if (delta == 0)return OperationResult.fail("delta cannot be zero");

            Cart cart = cartRepository.loadCart(userId);

            CartItem found = null;
            for(CartItem item : cart.getItems()){
                if(productId.equals(item.getProductId())){
                    found = item;
                    break;
                }
            }
            if(found == null) return  OperationResult.fail("product not found");

            int newQuantity = found.getQuantity() + delta;
            if(newQuantity <= 0){
                cart.getItems().removeIf(i -> productId.equals(i.getProductId()));
                cartRepository.saveCart(userId, cart);
                BigDecimal totalPrice = calculatetotal(cart);
               
                return OperationResult.ok("item removed", new CartSummary(cart, totalPrice));
            }
            Product p = productRepository.findById(productId);
            if(p == null) return OperationResult.fail("product not found");
            if(newQuantity > p.getStockQuantity()){
                return OperationResult.fail("not enough stock. Available: " + p.getStockQuantity());
            };
            found.setQuantity(newQuantity);
            cartRepository.saveCart(userId, cart);

            return OperationResult.ok("quantity updated", new CartSummary(cart, null));
            
        }catch(RuntimeException e){
            return OperationResult.fail("cart operation failed due to system bug");
        }
    }

}
