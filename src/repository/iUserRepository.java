package repository;
import java.util.List;
import java.util.Optional;
import model.User;

public interface  iUserRepository {

    List<User> findAll();

    Optional<User> findByUsername(String username);
/// فایده اپشن
    void save(User user); // add or update

    void deleteByUsername(String username);

    void saveAll(List<User> users) ;

}
