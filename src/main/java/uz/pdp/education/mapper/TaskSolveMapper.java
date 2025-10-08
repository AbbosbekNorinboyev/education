package uz.pdp.education.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.education.dto.TaskSolveDto;
import uz.pdp.education.entity.TaskFile;
import uz.pdp.education.entity.TaskSolve;
import uz.pdp.education.repository.TaskFileRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskSolveMapper {
    private final TaskFileRepository taskFileRepository;

    public TaskSolveDto toDto(TaskSolve taskSolve) {
        List<TaskFile> list = taskFileRepository.findAllByTaskSolveId(taskSolve.getId());
        List<String> files = list.stream().map(TaskFile::getFileUrl).toList();
        return TaskSolveDto.builder()
                .id(taskSolve.getId())
                .teacherId(taskSolve.getTeacher() != null ? taskSolve.getTeacher().getId() : null)
                .taskId(taskSolve.getTask() != null ? taskSolve.getTask().getId() : null)
                .studentId(taskSolve.getStudent() != null ? taskSolve.getStudent().getId() : null)
                .teacherId(taskSolve.getTeacher() != null ? taskSolve.getTeacher().getId() : null)
                .groupId(taskSolve.getGroup() != null ? taskSolve.getGroup().getId() : null)
                .description(taskSolve.getDescription())
                .score(taskSolve.getScore())
                .files(files)
                .build();
    }

    public List<TaskSolveDto> dtoList(List<TaskSolve> taskSolves) {
        if (taskSolves != null && !taskSolves.isEmpty()) {
            return taskSolves.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }
}
