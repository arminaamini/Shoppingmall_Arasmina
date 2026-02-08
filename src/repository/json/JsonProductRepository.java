
package repository.json;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.file.Files;////////OK\\\\\\\\\\\\\\\\\\\\\\\\
import java.nio.file.Path;
import java.nio.file.Paths;

import repository.iProductRepository;
import model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class JsonProductRepository implements iProductRepository {
    private static final Path FILE_PATH = Paths.get("data/products.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JsonProductRepository(){
        initFile();
    }

    private void initFile(){
        try{
            Path parent = FILE_PATH.getParent();
            if(parent != null && Files.notExists(parent)){
                Files.createDirectories(FILE_PATH.getParent());
            }
            if(Files.notExists(FILE_PATH)){
                Files.writeString(FILE_PATH, "[]");
            }
        }catch(IOException e){
            throw new RuntimeException("cannot initialize products.json");
        }
    }

    @Override
    public List<Product> findAll(){
        try{
            String json = Files.readString(FILE_PATH);

            Product[] arr = gson.fromJson(json,Product[].class);
            List<Product> list = new ArrayList<>();
            if(arr != null) {
                for(Product p : arr){
                    list.add(p);
                }
            }
            return list;
        }catch(IOException e){
            throw new RuntimeException("cannot read products.json");
        }
    }
    @Override
    public Product findById(String id){
        return findAll().stream()
        .filter(p -> p.getProductId().equals(id))
        .findFirst()
        .orElse(null);
    }
    @Override
    public void saveAll(List<Product> products){
        try{
            String json = gson.toJson(products);
            Files.writeString(FILE_PATH, json);
        }catch(IOException e){
            throw new RuntimeException("cannot write products.json");
        }
    }

}

   




