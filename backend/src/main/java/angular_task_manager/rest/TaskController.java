package angular_task_manager.rest;
import angular_task_manager.converter.TaskConverter;
import angular_task_manager.dto.TaskDto;
import angular_task_manager.entity.Task;
import angular_task_manager.service.TaskService;
import angular_task_manager.util.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/v1/tareas")
public class TaskController {
    @Autowired
    private TaskService service;
    @Autowired
    private TaskConverter converter;
    @GetMapping
    public ResponseEntity<List<TaskDto>> findAll() {
        List<TaskDto> registros = service.findAll().stream()
                .map(converter::fromEntity)
                .collect(Collectors.toList());
        return new WrapperResponse(true, "success", registros).createResponse(HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TaskDto> create (@RequestBody Task task){
        TaskDto registro = converter.fromEntity(service.save(task));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(@PathVariable("id") int id,@RequestBody Task task){
        TaskDto registro = converter.fromEntity(service.save(task));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable("id") int id){
        TaskDto registro=converter.fromEntity(service.findById(id));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
    }
    @GetMapping("/title/{title}")
    public ResponseEntity<List<TaskDto>> findByTitle(@PathVariable("title") String title){
        List<TaskDto> registros = service.findByTitle(title).stream()
                .map(converter::fromEntity)
                .collect(Collectors.toList());
        return new WrapperResponse(true, "success", registros).createResponse(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        try {
            service.delete(id); // Llama al servicio para eliminar el usuario por ID
            return new WrapperResponse(true, "Usuario eliminado exitosamente", null).createResponse(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new WrapperResponse(false, e.getMessage(), null).createResponse(HttpStatus.BAD_REQUEST);
        }
    }
}
