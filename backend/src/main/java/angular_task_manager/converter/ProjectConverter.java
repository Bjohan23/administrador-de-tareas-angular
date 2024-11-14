package angular_task_manager.converter;

import angular_task_manager.dto.ProjectDto;
import angular_task_manager.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter {

    public ProjectDto fromEntity(Project entity) {
        if (entity == null) return null;
        return ProjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .userId(entity.getUser().getId())
                .createdAt(entity.getCreatedAt().toString())
                .build();
    }

    public Project fromDTO(ProjectDto dto) {
        if (dto == null) return null;
        return Project.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
