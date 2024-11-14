package angular_task_manager.converter;

import angular_task_manager.dto.UserDto;
import angular_task_manager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto fromEntity(User entity) {
        if (entity == null) return null;
        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .createdAt(entity.getCreatedAt().toString())
                .build();
    }

    public User fromDTO(UserDto dto) {
        if (dto == null) return null;
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
