package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
      
    private String id;
    private String customerUsername;
    private LocalDateTime timeOfPurchase;
    private List<OrderItem> orderItems;
    private BigDecimal price;
   

    public Order(){
        this.orderItems = new ArrayList<>();
    }

    public Order(String id, String customerUsername, 
        LocalDateTime timeOfPurchase, List<OrderItem> orderItems, BigDecimal price){
        this.id = id;
        this.customerUsername = customerUsername;
        this.timeOfPurchase = timeOfPurchase;
        this.orderItems = orderItems;
        this.price = price;
        
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getCustomerUsername(){
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername){
        this.customerUsername = customerUsername;
    } 
    
    public LocalDateTime getTimeOfPurchase(){
        return timeOfPurchase;
    }
    public void setTimeOfPurchase(LocalDateTime timeOfPurchase){
        this.timeOfPurchase = timeOfPurchase;
    } 

    public List<OrderItem> getOrderItems(){
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems){
        this.orderItems = orderItems;
    }  

    public BigDecimal getPrice(){
        return price;
    }
    public void setPrice(BigDecimal price){
        this.price = price;
    } 
}
