package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.StudentDto;
import uz.pdp.education.entity.Student;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentMapper {
    public Student toEntity(StudentDto dto) {
        return Student.builder()
                .fullName(dto.getFullName())
                .age(dto.getAge())
                .build();
    }

    public StudentDto toDto(Student entity) {
        return StudentDto.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .age(entity.getAge())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .build();
    }

    public List<StudentDto> dtoList(List<Student> students) {
        if (students != null && !students.isEmpty()) {
            return students.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Student entity, StudentDto dto) {
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
