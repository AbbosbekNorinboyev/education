package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.AboutMeRequest;
import uz.pdp.education.dto.response.AboutMeResponse;
import uz.pdp.education.entity.AboutMe;

import java.util.ArrayList;
import java.util.List;

@Component
public class AboutMeMapper {
    public AboutMe toEntity(AboutMeRequest request) {
        return AboutMe.builder()
                .title(request.getTitle())
                .build();
    }

    public AboutMeResponse toResponse(AboutMe entity) {
        return AboutMeResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .teacherId(entity.getTeacher() != null ? entity.getTeacher().getId() : null)
                .build();
    }

    public List<AboutMeResponse> responseList(List<AboutMe> aboutMes) {
        if (aboutMes != null && !aboutMes.isEmpty()) {
            return aboutMes.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(AboutMe entity, AboutMeRequest request) {
        if (request == null) {
            return;
        }
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            entity.setTitle(request.getTitle());
        }
    }
}
