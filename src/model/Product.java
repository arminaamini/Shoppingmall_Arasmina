

package model;

import java.math.BigDecimal;

public class Product {

    private String productId;
    private String name;
    private String category;
    private BigDecimal price;
    private int stockQuantity;
    private String description;
    private String image;

    public Product(){}

    public Product(String productId, String name, String category, BigDecimal price, int stockQuantity, String description, String image) {
        this.productId= productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.image = image;
    }


    public String getProductId(){
        return productId;
    }
    public void setProductId(String id){
        this.productId = id;
    }


    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name= name;
    }


    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }


    public BigDecimal getPrice(){
        return price;
    }
    public void setPrice(BigDecimal price){
        this.price = price;
    }


    public int getStockQuantity(){
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity){
        this.stockQuantity = stockQuantity;
    }


    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }


    public  String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }
}
