package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.VideoDto;
import uz.pdp.education.entity.Video;

import java.util.ArrayList;
import java.util.List;

@Component
public class VideoMapper {
    public Video toEntity(VideoDto.VideoCreteDto videoDto) {
        return Video.builder()
                .build();
    }

    public VideoDto toDto(Video video) {
        return VideoDto.builder()
                .id(video.getId())
                .url(video.getUrl())
                .lessonId(video.getLesson() != null ? video.getLesson().getId() : null)
                .build();
    }

    public List<VideoDto> dtoList(List<Video> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Video entity, VideoDto.VideoCreteDto dto) {
        if (dto == null) {
            return;
        }
    }
}
