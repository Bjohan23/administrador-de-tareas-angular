package angular_task_manager.service.imple;

import angular_task_manager.entity.Project;
import angular_task_manager.exception.NoDataFoundException;
import angular_task_manager.repository.ProjectRepository;
import angular_task_manager.service.ProjectService;
import angular_task_manager.validator.ProjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll(Pageable page) {
        return repository.findAll(page).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Project findById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Project not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Project save(Project project) {
        ProjectValidator.validate(project);
        return repository.save(project);
    }

    @Override
    @Transactional
    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new NoDataFoundException("Project not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
