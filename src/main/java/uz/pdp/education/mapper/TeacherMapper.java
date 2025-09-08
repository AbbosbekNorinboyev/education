package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.TeacherDto;
import uz.pdp.education.entity.Teacher;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeacherMapper {
    public Teacher toEntity(TeacherDto dto) {
        return Teacher.builder()
                .age(dto.getAge())
                .fullName(dto.getFullName())
                .build();
    }

    public TeacherDto toDto(Teacher entity) {
        return TeacherDto.builder()
                .id(entity.getId())
                .age(entity.getAge())
                .fullName(entity.getFullName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .build();
    }

    public List<TeacherDto> dtoList(List<Teacher> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Teacher entity, TeacherDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getFullName() != null && !dto.getFullName().trim().isEmpty()) {
            entity.setFullName(dto.getFullName());
        }
        if (dto.getAge() > 0) {
            entity.setAge(dto.getAge());
        }
    }
}
