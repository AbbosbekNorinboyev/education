package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.request.AboutUsRequest;
import uz.pdp.education.dto.response.AboutUsResponse;
import uz.pdp.education.entity.AboutUs;

import java.util.ArrayList;
import java.util.List;

@Component
public class AboutUsMapper {
    public AboutUs toEntity(AboutUsRequest request) {
        return AboutUs.builder()
                .name(request.getName())
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
    }

    public AboutUsResponse toResponse(AboutUs entity) {
        return AboutUsResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .build();
    }

    public List<AboutUsResponse> responseList(List<AboutUs> aboutUses) {
        if (aboutUses != null && !aboutUses.isEmpty()) {
            return aboutUses.stream().map(this::toResponse).toList();
        }
        return new ArrayList<>();
    }

    public void update(AboutUs entity, AboutUsRequest request) {
        if (request == null) {
            return;
        }
        if (request.getName() != null && !request.getName().isEmpty()) {
            entity.setName(request.getName());
        }
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            entity.setTitle(request.getTitle());
        }
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            entity.setDescription(request.getDescription());
        }
    }
}
