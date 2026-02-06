package model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart(List<CartItem> items){
        this.items = items;
    }
    public Cart(){
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems(){
        return items;
    }

    public void setItems(List<CartItem> items){
        this.items = items;
    }
    
    public void addItem(CartItem item){
        items.add(item);
    }
    // add for single item
// null
//remove
// صالا جای این منطق ها در مدل هاست؟


}
