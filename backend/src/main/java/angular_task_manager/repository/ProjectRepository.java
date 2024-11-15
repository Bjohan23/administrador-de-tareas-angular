package angular_task_manager.repository;

import angular_task_manager.entity.Project;
import angular_task_manager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByUser(User user);  // Cambiado para usar el objeto User en lugar de userId
}
