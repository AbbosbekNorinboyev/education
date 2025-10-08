package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.ResourcesDto;
import uz.pdp.education.entity.Resources;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceMapper {
    public Resources toEntity(ResourcesDto.ResourcesCreteDto resourcesDto) {
        return Resources.builder()
                .type(resourcesDto.getType())
                .build();
    }

    public ResourcesDto toDto(Resources resources) {
        return ResourcesDto.builder()
                .id(resources.getId())
                .url(resources.getUrl())
                .type(resources.getType())
                .lessonId(resources.getLesson() != null ? resources.getLesson().getId() : null)
                .build();
    }

    public List<ResourcesDto> dtoList(List<Resources> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Resources entity, ResourcesDto.ResourcesCreteDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getType() != null && !dto.getType().trim().isEmpty()) {
            entity.setType(dto.getType());
        }
    }
}