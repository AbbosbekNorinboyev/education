package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.ApiLog;
import uz.pdp.education.repository.ApiLogRepository;
import uz.pdp.education.service.ApiLogService;

import java.time.LocalDateTime;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ApiLogServiceImpl implements ApiLogService {
    private final ApiLogRepository apiLogRepository;

    @Override
    public Response<?> saveLog(String username, String method, String path, LocalDateTime time, long duration) {
        ApiLog apiLog = ApiLog.builder()
                .username(username)
                .method(method)
                .path(path)
                .timestamp(time)
                .durationMs(duration)
                .build();
        apiLogRepository.save(apiLog);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("ApiLog successfully saved")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAll(Pageable pageable) {
        Page<ApiLog> apiLogs = apiLogRepository.findAll(pageable);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("ApiLog list successfully found")
                .data(apiLogs)
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
