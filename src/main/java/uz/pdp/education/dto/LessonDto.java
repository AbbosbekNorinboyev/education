package uz.pdp.education.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonDto {
    private Long id;
    private String title;
    private String description;
    private Long groupId;
    private List<VideoDto> videos;
    private List<ResourcesDto> resources;
    private TaskDto taskDto;

    @Data
    @Builder
    public static class LessonCreteDto {
        private String title;
        private String description;
        private Long groupId;
        private List<MultipartFile> videos;
        private List<MultipartFile> resources;
        private String taskTitle;
        private String taskDescription;
        private MultipartFile taskVideo;
        private MultipartFile taskFile;
    }
}