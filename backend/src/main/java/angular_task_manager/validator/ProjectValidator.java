package angular_task_manager.validator;

import angular_task_manager.entity.Project;
import angular_task_manager.exception.ValidateException;

public class ProjectValidator {
    public static void validate(Project project) {
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new ValidateException("El nombre es requerido");
        }
        if (project.getName().length() > 100) {
            throw new ValidateException("El nombre no debe exceder los 100 caracteres");
        }
    }
}