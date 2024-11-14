package angular_task_manager.rest;

import angular_task_manager.converter.UserConverter;
import angular_task_manager.dto.UserDto;
import angular_task_manager.entity.User;
import angular_task_manager.service.UserService;
import angular_task_manager.util.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/usuarios")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private UserConverter converter;

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> registros = service.findAll().stream()
                .map(converter::fromEntity)
                .collect(Collectors.toList());
        return new WrapperResponse(true, "success", registros).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> create (@RequestBody User usuario){
        UserDto registro = converter.fromEntity(service.save(usuario));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") int id,@RequestBody User usuario){
        UserDto registro = converter.fromEntity(service.save(usuario));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") int id){
        UserDto registro=converter.fromEntity(service.findById(id));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable("email") String email){
        UserDto registro=converter.fromEntity(service.findByEmail(email));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        try {
            service.delete(id); // Llama al servicio para eliminar el usuario por ID
            return new WrapperResponse(true, "Usuario eliminado exitosamente", null).createResponse(HttpStatus.OK);
        } catch (Exception e) {
            return new WrapperResponse(false, e.getMessage(), null).createResponse(HttpStatus.BAD_REQUEST);
        }
    }
}
