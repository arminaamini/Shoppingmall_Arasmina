package service;

import model.Product;
import commons.OperationResult;
import repository.iProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductService {

    private final iProductRepository repo;
    

    public ProductService(iProductRepository repo){
        this.repo = repo;
       
    }

    public OperationResult<List<Product>> listProducts(){
        return OperationResult.ok("ok", repo.findAll());
    }

    public OperationResult<List<Product>> sortByPrice(boolean asc){
        List<Product> products = repo.findAll();

        if(asc){
            products.sort(Comparator.comparing(p -> p.getPrice()));
        }
       
        return OperationResult.ok("sorted", products);
    }

    public OperationResult<Void> add(Product p){
        if ((p.getName() == null) || p.getName().isBlank()) {
            return OperationResult.fail("name is needed");
        }
        if (p.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return OperationResult.fail("price cannot be negative");
        }
        if(p.getStockQuantity() < 0){
            return OperationResult.fail("stock cannot be negative");

        }
        List<Product> products = repo.findAll();
        p.setProductId(generateProductId(products));
        products.add(p);
        repo.saveAll(products);

        return OperationResult.ok("added", null);
    }


    public OperationResult<Void> update(Product updated){
        List<Product> products = repo.findAll();

        for(int i = 0 ; i< products.size() ; i++){
            if(products.get(i).getProductId().equals(updated.getProductId())){
                products.set(i, updated);
                repo.saveAll(products);
                return OperationResult.ok("updated", null);

            }
        }
        return OperationResult.fail("product not found");

    }

    public OperationResult<Void> delete(String productId){
        List<Product> products = repo.findAll();
        boolean removed = products.removeIf(p -> p.getProductId().equals(productId));

        if(! removed) return OperationResult.fail("product not found");

        repo.saveAll(products);
        return OperationResult.ok("deleted", null);
    }

    private String generateProductId(List<Product> products){
        int max = 0;
        for(Product p : products){
            String id = p.getProductId();
            if(id != null && id.startsWith("P")){
                int num = Integer.parseInt(id.substring(1));
                if(num > max) max = num;
            }
        }
        return String.format("P%03d",max + 1);
    }

    public List<Product> getProducts(){
        return repo.findAll();
    }

}
