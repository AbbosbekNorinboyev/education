package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.OptionsDto;
import uz.pdp.education.dto.QuestionDto;
import uz.pdp.education.dto.RequestTestDto;
import uz.pdp.education.dto.ResultTestDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.*;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.OptionMapper;
import uz.pdp.education.mapper.QuestionMapper;
import uz.pdp.education.repository.*;
import uz.pdp.education.service.QuestionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final OptionsRepository optionsRepository;
    private final LessonRepository lessonRepository;
    private final OptionMapper optionMapper;
    private final AuthUserRepository authUserRepository;
    private final TestResultRepository resultRepository;

    @Override
    public Response<?> create(Long lessonId, QuestionDto.QuestionCreateDto dto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("LESSON NOT FOUND"));
        Question entity = questionMapper.toEntity(dto);
        entity.setLesson(lesson);
        Question question = questionRepository.save(entity);

        List<OptionsDto.OptionsCreteDto> options = dto.getOptions();
        if (options != null && !options.isEmpty()) {
            for (OptionsDto.OptionsCreteDto creteDto : options) {
                Options option = optionMapper.toEntity(creteDto);
                option.setQuestion(question);

                optionsRepository.save(option);
            }
        }
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("SUCCESS")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> get(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("SUCCESSFULLY FOUND")
                .data(questionMapper.toDto(question))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll(Pageable pageable) {
        List<Question> questions = questionRepository.findAll(pageable).getContent();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("SUCCESSFULLY FOUND")
                .data(questionMapper.dtoList(questions))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> update(QuestionDto.QuestionCreateDto dto, Long id) {
        Question questionFound = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QUESTION NOT FOUND"));
        questionMapper.update(questionFound, dto);
        questionRepository.save(questionFound);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("SUCCESSFULLY UPDATED")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> delete(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QUESTION NOT FOUND"));
        List<Options> options = optionsRepository.findAllByQuestionId(question.getId());
        optionsRepository.deleteAll(options);
        questionRepository.delete(question);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("SUCCESSFULLY DELETED")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getByLessonId(Pageable pageable, Long lessonId) {
        List<Question> questions = questionRepository.findAllByLessonId(pageable, lessonId);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("SUCCESSFULLY FOUND BY LESSON ID")
                .data(questionMapper.dtoList(questions))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> test(List<RequestTestDto> request, Long studentId, Long lessonId) {
        lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("LESSON TOPILMADI"));
        AuthUser student = authUserRepository.findById(studentId).orElseThrow(()
                -> new ResourceNotFoundException("STUDENT NOT FOUND"));
        TestResult test = resultRepository.findByStudentAndLessonId(student, lessonId).orElse(null);
        if (test != null && test.getAttemptCount() > 3) {
            return Response.builder()
                    .code(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .success(false)
                    .message("Can not try more than 3 times")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        long count = 0L;
        if (test != null) {
            count = test.getAttemptCount() + 1;
        } else
            count++;
        Long correctCount = 0L, inCorrectCount = 0L;
        List<OptionsDto> optionsDtos = new ArrayList<>();
        for (RequestTestDto requestTestDto : request) {
            Optional<Options> optionalOptions = optionsRepository.findById(requestTestDto.getOptionId());
            if (optionalOptions.isPresent()) {
                Options options = optionalOptions.get();
                if (options.getIsCorrect()) {
                    OptionsDto optionsDto = optionMapper.toDto(options);
                    optionsDtos.add(optionsDto);
                    correctCount++;
                } else {

                    inCorrectCount++;
                }
            }
        }
        resultRepository.save(TestResult.builder()
                .totalIncorrect(inCorrectCount)
                .totalCorrect(correctCount)
                .student(student)
                .lessonId(lessonId)
                .attemptCount(count)
                .build());

        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("SUCCESSFULLY TESTED")
                .data(ResultTestDto.builder()
                        .studentId(studentId)
                        .correctCount(correctCount)
                        .incorrectCount(inCorrectCount)
                        .attemptCount(count)
                        .lessonId(lessonId)
                        .correctOptionIds(optionsDtos)
                        .build())
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
