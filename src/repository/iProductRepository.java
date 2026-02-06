package repository;
import model.Product;
import java.util.List;
public interface  iProductRepository {

    List<Product> findAll();

    Product findById(String id);
    
    void saveAll(List<Product> products);
}
