package angular_task_manager.service.imple;

import angular_task_manager.entity.User;
import angular_task_manager.exception.GeneralException;
import angular_task_manager.exception.NoDataFoundException;
import angular_task_manager.exception.ValidateException;
import angular_task_manager.repository.UserRepository;
import angular_task_manager.service.UserService;
import angular_task_manager.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(Pageable page) {
        try {
            List<User> registros = repository.findAll(page).toList();
            return registros;
        }catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        try {
            List<User> registros = repository.findAll();
            return registros;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        try {
            User registro = repository.findByEmail(email);
            if (registro == null) {
                throw new NoDataFoundException("Error del servidor");
            }
            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(int id) {
        try {
            User registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));
            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }

}

    @Override
    @Transactional
    public User save(User user) {
        try {

            UserValidator.save(user);
            // Nuevo registro
            if (user.getId() == 0) {
                user.setPassword(user.getPassword());
                User nuevo = repository.save(user);
                return nuevo;
            }
            // editar registro
            User registro = repository.findById(user.getId())
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));
            registro.setEmail(user.getEmail());
            registro.setPassword(user.getPassword());
            repository.save(registro);
            return registro;
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            User registro = repository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));
            repository.delete(registro);
        } catch (ValidateException | NoDataFoundException e) {
            throw e;
        } catch (GeneralException e) {
            throw new GeneralException("Error del servidor");
        }
    }
}
