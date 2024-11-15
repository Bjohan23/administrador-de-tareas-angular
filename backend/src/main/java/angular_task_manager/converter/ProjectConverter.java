// ProjectConverter.java
package angular_task_manager.converter;

import angular_task_manager.dto.ProjectDto;
import angular_task_manager.entity.Project;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProjectConverter {

    public ProjectDto fromEntity(Project entity) {
        ProjectDto dto = new ProjectDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setUserId(entity.getUser().getId());
        dto.setCreatedAt(entity.getCreatedAt().toString());
        return dto;
    }

    public Project fromDTO(ProjectDto dto) {
        Project entity = new Project();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUserId(dto.getUserId());
        entity.setCreatedAt(LocalDateTime.parse(dto.getCreatedAt()));
        return entity;
    }
}