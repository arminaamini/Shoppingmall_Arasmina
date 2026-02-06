package repository;

import java.util.List;
import model.Order;

public interface iOrderRepository {
    List<Order> findAll();

    void save(Order order);
}
