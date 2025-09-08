package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.GroupDto;
import uz.pdp.education.entity.Groups;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMapper {
    public Groups toEntity(GroupDto dto) {
        return Groups.builder()
                .name(dto.getName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    public GroupDto toDto(Groups entity) {
        return GroupDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .students(entity.getStudents())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .build();
    }

    public List<GroupDto> dtoList(List<Groups> groups) {
        if (groups != null && !groups.isEmpty()) {
            return groups.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Groups entity, GroupDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            entity.setName(dto.getName());
        }
    }
}
