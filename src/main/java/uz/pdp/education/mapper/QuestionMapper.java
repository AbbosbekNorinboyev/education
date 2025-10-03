package uz.pdp.education.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.education.dto.QuestionDto;
import uz.pdp.education.entity.Options;
import uz.pdp.education.entity.Question;
import uz.pdp.education.repository.OptionsRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionMapper {
    private final OptionsRepository optionsRepository;
    private final OptionMapper optionMapper;

    public Question toEntity(QuestionDto.QuestionCreateDto questionDto) {
        return Question.builder()
                .text(questionDto.getText())
                .build();
    }

    public QuestionDto toDto(Question question) {
        List<Options> list = optionsRepository.findAllByQuestionId(question.getId());
        return QuestionDto.builder()
                .id(question.getId())
                .text(question.getText())
                .lessonId(question.getLesson() != null ? question.getLesson().getId() : null)
                .options(optionMapper.dtoList(list))
                .build();
    }

    public List<QuestionDto> dtoList(List<Question> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }

    public void update(Question entity, QuestionDto.QuestionCreateDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getText() != null && !dto.getText().trim().isEmpty()) {
            entity.setText(dto.getText());
        }
    }
}
