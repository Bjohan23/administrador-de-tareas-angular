package angular_task_manager.service;

import angular_task_manager.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public List<User> findAll(Pageable page);
    public List<User> findAll();
    public User findByEmail(String email);
    public User findById(int id);
    public User save(User user);
    public void delete(int id);
}
