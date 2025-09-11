package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.GroupDayRequest;
import uz.pdp.education.dto.response.GroupDayResponse;
import uz.pdp.education.entity.GroupDay;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupDayMapper {
    public GroupDay toEntity(GroupDayRequest request) {
        return GroupDay.builder()
                .weeks(request.getWeeks())
                .groupTime(request.getGroupTime())
                .build();
    }

    public GroupDayResponse toResponse(GroupDay entity) {
        return GroupDayResponse.builder()
                .id(entity.getId())
                .weeks(entity.getWeeks())
                .groupTime(entity.getGroupTime())
                .groupId(entity.getGroup() != null ? entity.getGroup().getId() : null)
                .build();
    }

    public List<GroupDayResponse> responseList(List<GroupDay> groupDays) {
        if (groupDays != null && !groupDays.isEmpty()) {
            return groupDays.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(GroupDay entity, GroupDayRequest request) {
        if (request == null) {
            return;
        }
        if (request.getGroupTime() != null && !request.getGroupTime().trim().isEmpty()) {
            entity.setGroupTime(request.getGroupTime());
        }
        if (request.getWeeks() != null) {
            entity.setWeeks(request.getWeeks());
        }
    }
}
