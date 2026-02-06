package service;

import model.Product;
import commons.OperationResult;
import repository.iProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final iProductRepository repo;
    private List<Product> products;

    public ProductService(iProductRepository repo){
        this.repo = repo;
        this.products = repo.findAll();
        if(this.products == null){
            this.products = new ArrayList<>();
        }
    }


    public OperationResult<List<Product>> listProducts(){
        return OperationResult.ok("ok", new ArrayList<>(products));
    }

    public List<Product> getProducts(){
        return new ArrayList<>(products);
    }


    public OperationResult<Void> newProduct(String name, String category,
                                            int price, int stockQuantity, String description, String image) {

        if(name == null || name.isBlank()) return OperationResult.fail("name needed");  
        if(category == null || category.isBlank()) return OperationResult.fail("category needed"); 
        if(price < 0) return OperationResult.fail("price not negative"); 
        if(stockQuantity < 0) return OperationResult.fail("stockQuantity not negative");                                     
        int id = products.stream()
                .mapToInt(p -> p.getProductId())
                .max()
                .orElse(0) + 1;

        Product newProduct = new Product(id, name, category, null, stockQuantity, description, image);
        products.add(newProduct);
        repo.saveAll(products);
        return OperationResult.ok( "Successfully added product", null);
    }


    public OperationResult<Void> deleteProduct(int id) {
        Product product = findById(id);
        if(product == null) return OperationResult.fail("no such product");  

        products.remove(product);
        repo.saveAll(products);
        return OperationResult.ok( "Successfully deleted product", null);
    }


    private Product findById(int id) {
        Product product = products.stream()
                .filter(p -> p.getProductId() == id)
                .findFirst()
                .orElse(null);

        return product;
    }


    public OperationResult<Void> modifyProductName(int id, String newName) {
        if(newName == null || newName.isBlank()) return OperationResult.fail("name needed");  

        Product product = findById(id);
        if (product == null) {
            return OperationResult.fail( "No such product with id " + id);
        }
        product.setName(newName);
        repo.saveAll(products);
        return  OperationResult.ok( "Successfully updated product with name " + newName, null);
    }


    public OperationResult<Void> modifyProductCategory(int id, String newCategory) {
        if(newCategory == null || newCategory.isBlank()) return OperationResult.fail("name needed");  

        Product product = findById(id);
        if (product == null) {
            return OperationResult.fail( "No such product with id " + id);
        }
        product.setCategory(newCategory);
        repo.saveAll(products);
        return OperationResult.ok( "Successfully updated product category to " + newCategory, null);
    }


    public OperationResult<Void> modifyProductPrice(int id, BigDecimal newPrice) {

        Product product = findById(id);
        if (product == null) {
            return OperationResult.fail( "No such product with id " + id);
        }
        product.setPrice(newPrice);
        repo.saveAll(products);
        return  OperationResult.ok("Successfully updated product price to " + newPrice, null);
    }


    public OperationResult<Void> modifyProductStockQuantity(int id, int newStockQuantity) {
        if(newStockQuantity <0) return OperationResult.fail("name needed");  

        Product product = findById(id);
        if (product == null) {
            return OperationResult.fail( "No such product with id " + id);
        }
        product.setStockQuantity(newStockQuantity);
        repo.saveAll(products);
        return OperationResult.ok( "Successfully updated product stock quantity to " + newStockQuantity, null);
    }


    public OperationResult<Void> modifyProductDescription(int id, String newDescription) {
        Product product = findById(id);
        if (product == null) {
            return OperationResult.fail( "No such product with id " + id);
        }
        product.setDescription(newDescription);
        repo.saveAll(products);
        return OperationResult.ok( "Successfully updated product description", null);
    }


    public OperationResult<Void> modifyProductImage(int id, String newImage) {
        Product product = findById(id);
        if (product == null) {
            return OperationResult.fail( "No such product with id " + id);
        }
        product.setImage(newImage);
        repo.saveAll(products);
        return OperationResult.ok( "Successfully updated product image", null);
    }


}
