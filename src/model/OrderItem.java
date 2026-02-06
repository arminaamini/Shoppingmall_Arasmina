package model;

import java.math.BigDecimal;

public class OrderItem {
    
    private int productId;// String or int p001
    private int quantity;
    private BigDecimal priceAtPurchase;

    public OrderItem(){}

    public OrderItem(int productId,int quantity , BigDecimal priceAtPurchase){
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }
    
    public int getProductId(){
        return productId;
    }
    public int getQuantity(){
        return quantity;
    }
    public BigDecimal getPriceAtPurchase(){
        return priceAtPurchase;
    }
    
    public void setProductId(int productId){
        this.productId = productId;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase){
        this.priceAtPurchase = priceAtPurchase;
    }

}
