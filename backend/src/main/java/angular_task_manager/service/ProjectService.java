package angular_task_manager.service;

import angular_task_manager.entity.Project;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    public List<Project> findAll(Pageable page);
    public List<Project> findAll();
    public Project findById(int id);
    public List<Project> findByUserId(int userId);
    public Project save(Project project);
    public void delete(int id);
}
