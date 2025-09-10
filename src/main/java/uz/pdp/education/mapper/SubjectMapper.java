package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.SubjectDto;
import uz.pdp.education.entity.Subject;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectMapper {
    public Subject toEntity(SubjectDto dto) {
        return Subject.builder()
                .name(dto.getName())
                .build();
    }

    public SubjectDto toDto(Subject entity) {
        return SubjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public List<SubjectDto> dtoList(List<Subject> subjects) {
        if (subjects != null && !subjects.isEmpty()) {
            return subjects.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Subject entity, SubjectDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            entity.setName(dto.getName());
        }
    }
}
