package angular_task_manager.service.imple;

import angular_task_manager.entity.Project;
import angular_task_manager.entity.User;
import angular_task_manager.exception.GeneralException;
import angular_task_manager.exception.NoDataFoundException;
import angular_task_manager.exception.ValidateException;
import angular_task_manager.repository.ProjectRepository;
import angular_task_manager.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll(Pageable page) {
        try {
            List<Project> registros = repository.findAll(page).toList();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        try {
            List<Project> registros = repository.findAll();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Project findById(int id) {
        try {
            Project registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un proyecto con ese ID"));
            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findByUserId(int userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoDataFoundException("No existe un usuario con ese ID"));
            return repository.findByUser(user);
        } catch (NoDataFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional
    public Project save(Project project) {
        try {
            ProjectValidator.validate(project);

            // Verificar si el userId es válido
            if (project.getUserId() == 0) {
                throw new ValidateException("El ID del usuario no puede ser 0");
            }

            // Recuperar el objeto User usando userId
            User user = userRepository.findById(project.getUserId())
                    .orElseThrow(() -> new NoDataFoundException("No existe un usuario con ese ID"));
            project.setUser(user);

            if (project.getId() != 0) {
                Project existingProject = repository.findById(project.getId())
                        .orElseThrow(() -> new NoDataFoundException("No existe un proyecto con ese ID"));
                existingProject.setName(project.getName());
                existingProject.setDescription(project.getDescription());
                existingProject.setUser(user);
                return repository.save(existingProject);
            } else {
                return repository.save(project);
            }
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException("Error del servidor");
        }
    }
    @Override
    @Transactional
    public void delete(int id) {
        try {
            Project registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un proyecto con ese ID"));
            repository.delete(registro);
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException("Error del servidor");
        }
    }
}
