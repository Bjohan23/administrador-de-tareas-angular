package angular_task_manager.service.imple;

import angular_task_manager.entity.Project;
import angular_task_manager.entity.Task;
import angular_task_manager.exception.GeneralException;
import angular_task_manager.exception.NoDataFoundException;
import angular_task_manager.exception.ValidateException;
import angular_task_manager.repository.ProjectRepository;
import angular_task_manager.repository.TaskRepository;
import angular_task_manager.service.TaskService;
import angular_task_manager.validator.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll(Pageable page) {
        try {
            List<Task> registros = repository.findAll(page).toList();
            return registros;
        }catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAll() {
        try {
            List<Task> registros = repository.findAll();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Task findById(int id) {
        try {
            Task registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));
            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
           }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findByTitle(String title) {
        try{
            List<Task> registros = repository.findByTitleContainingIgnoreCase(title);
            return registros;
        }catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional
    public Task save(Task task) {
        try {
            TaskValidator.validate(task);

            // Buscar el proyecto por su ID y asignarlo a la tarea
            if (task.getProject() == null && task.getProjectId() != 0) {
                Project project = projectRepository.findById(task.getProjectId())
                        .orElseThrow(() -> new NoDataFoundException("No existe un proyecto con ese ID"));
                task.setProject(project);
            }

            // Nuevo registro
            if (task.getId() == 0) {
                if (task.getCreatedAt() == null) {
                    task.setCreatedAt(LocalDateTime.now()); // Assigns the current date and time if it's a new record
                }
                Task nuevo = repository.save(task);
                return nuevo;
            }

            // Editar registro
            Task registro = repository.findById(task.getId())
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));
            registro.setDescription(task.getDescription());
            registro.setTitle(task.getTitle());
            registro.setStatus(task.getStatus());
            registro.setProject(task.getProject());
            return repository.save(registro);
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GeneralException("Error del servidor: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            Task registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));
            repository.delete(registro);
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }
}
