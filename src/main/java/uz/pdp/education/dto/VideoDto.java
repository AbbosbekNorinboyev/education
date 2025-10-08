package uz.pdp.education.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDto {
    private Long id;
    private String url;
    private Long lessonId;

    @Data
    @Builder
    @AllArgsConstructor
    public static class VideoCreteDto {
        private MultipartFile file;
    }
}