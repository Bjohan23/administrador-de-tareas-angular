package angular_task_manager.converter;

import angular_task_manager.dto.TaskDto;
import angular_task_manager.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {

    public TaskDto fromEntity(Task entity) {
        if (entity == null) return null;
        return TaskDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(entity.getStatus().toString())
                .projectId(entity.getProject().getId())
                .createdAt(entity.getCreatedAt().toString())
                .build();
    }

    public Task fromDTO(TaskDto dto) {
        if (dto == null) return null;
        return Task.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(Task.Status.valueOf(dto.getStatus().toUpperCase()))
                .build();
    }
}
