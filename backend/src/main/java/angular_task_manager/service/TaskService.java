package angular_task_manager.service;

import angular_task_manager.entity.Task;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    public List<Task> findAll(Pageable page);
    public List<Task> findAll();
    public Task findById(int id);
    public List<Task> findByTitle(String title);
    public Task save(Task task);
    public void delete(int id);
}
