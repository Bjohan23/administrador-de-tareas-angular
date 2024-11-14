package angular_task_manager.validator;

import angular_task_manager.entity.User;
import angular_task_manager.exception.ValidateException;

public class UserValidator {
    public static void save(User registro) {
        if(registro.getEmail()==null || registro.getEmail().trim().isEmpty()) {
            throw new ValidateException("El email es requerido");
        }
        if(registro.getEmail().length()>70) {
            throw new ValidateException("El email no debe exceder los 70 caracteres");
        }
        if(registro.getPassword()==null || registro.getPassword().trim().isEmpty()) {
            throw new ValidateException("La contraseña es requerido");
        }
    }
}
