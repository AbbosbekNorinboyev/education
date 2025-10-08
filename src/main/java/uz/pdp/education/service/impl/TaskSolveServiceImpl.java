package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.education.dto.TaskSolveDto;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.*;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.TaskSolveMapper;
import uz.pdp.education.mapper.TestResultMapper;
import uz.pdp.education.repository.*;
import uz.pdp.education.service.TaskSolveService;
import uz.pdp.education.utils.Util;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TaskSolveServiceImpl implements TaskSolveService {
    private final AuthUserRepository authUserRepository;
    private final TaskRepository taskRepository;
    private final TaskSolveRepository taskSolveRepository;
    private final TaskFileRepository taskFileRepository;
    private final TaskSolveMapper taskSolveMapper;
    private final GroupsRepository groupsRepository;
    private final TestResultRepository testResultRepository;
    private final TestResultMapper testResultMapper;

    @Override
    public Response<?> create(TaskSolveDto.CreateTaskSolve dto) {
        TaskSolve solve = new TaskSolve();
        AuthUser student = authUserRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("STUDENT NOT FOUND"));
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("TASK NOT FOUND"));
        solve.setStudent(student);
        solve.setTask(task);
        TaskSolve save = taskSolveRepository.save(solve);
        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            for (MultipartFile file : dto.getFiles()) {
                TaskFile taskFile = new TaskFile();
                String url = Util.getFileBytes(file);
                taskFile.setTaskSolve(save);
                taskFile.setFileUrl(url);
                taskFileRepository.save(taskFile);
            }
        }
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Successfully created")
                .success(true)
                .data(taskSolveMapper.toDto(save))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> submit(TaskSolveDto.SubmitTaskSolve dto) {
        TaskSolve taskSolve = taskSolveRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("TASK SOLVE NOT FOUND"));
        AuthUser teacher = authUserRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("TEACHER NOT FOUND"));
        Groups group = groupsRepository.findById(dto.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("GROUP NOT FOUND"));
        taskSolve.setScore(dto.getScore());
        taskSolve.setTeacher(teacher);
        taskSolve.setGroup(group);

        taskSolveRepository.save(taskSolve);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .success(true)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAllByLessonId(Long lessonId) {
        Task task = taskRepository.findByLessonId(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("TASK NOT FOUND THIS LESSON"));
        List<TaskSolve> list = taskSolveRepository.findAllByTaskId(task.getId());
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Task Solve successfully found")
                .success(true)
                .data(taskSolveMapper.dtoList(list))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> filter(Long studentId, Long teacherId, Long lessonId, Long groupId) {
        List<TaskSolve> filter = taskSolveRepository.filter(studentId, teacherId, lessonId, groupId);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .data(taskSolveMapper.dtoList(filter))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> resultsFilter(Long studentId, Long teacherId, Long lessonId, Long groupId) {
        List<TestResult> filter = testResultRepository.filter(studentId, teacherId, lessonId, groupId);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .data(testResultMapper.dtoList(filter))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
