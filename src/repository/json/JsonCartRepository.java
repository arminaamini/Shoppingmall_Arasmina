package repository.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import commons.DataPaths;
import model.Cart;
import repository.iCartRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonCartRepository implements iCartRepository{

    private final Gson gson;

    public JsonCartRepository(){
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        checkCartFile();
    }
    private void checkCartFile(){//finding filepath
        File dir = new File(DataPaths.cartsFile);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }
    private File cartFilePath(String userId){//create filepath for each customer(parentpath + childpath)
        return new File(DataPaths.cartsFile, "cart_" + userId + ".json");
    }
    @Override
    public Cart loadCart(String userId){
        File file = cartFilePath(userId);
        //if filepath doesnt exist, make an empty cart
        if(!file.exists()){
            Cart empty = new Cart();
            saveCart(userId, empty);
            return empty;
        }
//try catch for closing file
        try(Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)){
            Cart cart = gson.fromJson(reader, Cart.class);
            if(cart == null) return new Cart();

            if(cart.getItems() == null){
                cart.setItems(new java.util.ArrayList<>());
            }
            return cart;
        }catch (IOException e){
            throw new RuntimeException("cannot load cart for userId =" + userId, e);
        }  
    }


    @Override
    public void saveCart(String userId, Cart cart){
        File file = cartFilePath(userId);
        try(Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)){
            gson.toJson(cart, writer);
        }
        catch (IOException e){
            throw new RuntimeException("cannot save cart for userId =" + userId, e);
        }  
    }
    @Override
    public void clearCart(String userId){
        saveCart(userId, new Cart());
    }

}
