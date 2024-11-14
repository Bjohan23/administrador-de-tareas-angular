package angular_task_manager.validator;

import angular_task_manager.entity.Task;
import angular_task_manager.exception.ValidateException;

public class TaskValidator {
    public static void validate(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new ValidateException("El título es requerido");
        }
        if (task.getTitle().length() > 100) {
            throw new ValidateException("El título no debe exceder los 100 caracteres");
        }
    }
}