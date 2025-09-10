package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.GroupRequest;
import uz.pdp.education.dto.response.GroupResponse;
import uz.pdp.education.entity.Groups;
import uz.pdp.education.enums.GroupStatus;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMapper {
    public Groups toEntity(GroupRequest request) {
        return Groups.builder()
                .name(request.getName())
                .price(request.getPrice())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(GroupStatus.ACTIVE)
                .build();
    }

    public GroupResponse toResponse(Groups entity) {
        return GroupResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .subjectId(entity.getSubject() != null ? entity.getSubject().getId() : null)
                .teacherId(entity.getTeacher() != null ? entity.getTeacher().getId() : null)
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .build();
    }

    public List<GroupResponse> dtoList(List<Groups> groups) {
        if (groups != null && !groups.isEmpty()) {
            return groups.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(Groups entity, GroupRequest request) {
        if (request == null) {
            return;
        }
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            entity.setName(request.getName());
        }
        if (request.getPrice() != null && request.getPrice() >= 0) {
            entity.setPrice(request.getPrice());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.setEndDate(request.getEndDate());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
    }
}
