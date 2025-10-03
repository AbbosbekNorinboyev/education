package uz.pdp.education.service;

import org.springframework.data.domain.Pageable;
import uz.pdp.education.dto.QuestionDto;
import uz.pdp.education.dto.RequestTestDto;
import uz.pdp.education.dto.response.Response;

import java.util.List;

public interface QuestionService {
    Response<?> create(Long lessonId, QuestionDto.QuestionCreateDto dto);

    Response<?> get(Long id);

    Response<?> getAll(Pageable pageable);

    Response<?> update(QuestionDto.QuestionCreateDto dto, Long id);

    Response<?> delete(Long id);

    Response<?> getByLessonId(Pageable pageable, Long lessonId);

    Response<?> test(List<RequestTestDto> requestTestDto, Long studentId, Long lessonId);
}
