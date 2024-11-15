package angular_task_manager.rest;

import angular_task_manager.converter.ProjectConverter;
import angular_task_manager.dto.ProjectDto;
import angular_task_manager.entity.Project;
import angular_task_manager.entity.User;
import angular_task_manager.exception.NoDataFoundException;
import angular_task_manager.service.ProjectService;
import angular_task_manager.repository.UserRepository;
import angular_task_manager.util.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Autowired
    private ProjectConverter converter;

    @Autowired
    private UserRepository userRepository;  // Necesitamos el repositorio de User para encontrar el usuario por su ID

    @GetMapping
    public ResponseEntity<List<ProjectDto>> findAll() {
        List<ProjectDto> registros = service.findAll().stream()
                .map(converter::fromEntity)
                .collect(Collectors.toList());
        return new WrapperResponse(true, "success", registros).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectDto> create(@RequestBody ProjectDto projectDto) {
        try {
            // Buscar el usuario por ID
            User user = userRepository.findById(projectDto.getUserId())
                    .orElseThrow(() -> new NoDataFoundException("Usuario no encontrado con el ID: " + projectDto.getUserId()));

            // Crear y guardar el proyecto
            Project project = converter.fromDTO(projectDto);
            project.setUser(user);  // Asociar el usuario al proyecto

            ProjectDto registro = converter.fromEntity(service.save(project));

            return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.CREATED);
        } catch (NoDataFoundException e) {
            return new WrapperResponse(false, e.getMessage(), null).createResponse(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new WrapperResponse(false, "Error al crear el proyecto", null).createResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> update(@PathVariable("id") int id, @RequestBody ProjectDto projectDto) {
        try {
            Project project = converter.fromDTO(projectDto);
            project.setId(id); // Establecer el ID del proyecto a actualizar

            ProjectDto registro = converter.fromEntity(service.save(project));
            return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
        } catch (Exception e) {
            return new WrapperResponse(false, e.getMessage(), null).createResponse(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> findById(@PathVariable("id") int id) {
        ProjectDto registro = converter.fromEntity(service.findById(id));
        return new WrapperResponse(true, "success", registro).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        try {
            service.delete(id); // Llama al servicio para eliminar el proyecto por ID
            return new WrapperResponse(true, "success", "Registro eliminado").createResponse(HttpStatus.OK);
        } catch (Exception e) {
            return new WrapperResponse(false, e.getMessage(), "Registro no eliminado").createResponse(HttpStatus.BAD_REQUEST);
        }
    }
}
