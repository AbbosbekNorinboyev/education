package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.request.AboutMeRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AboutMe;
import uz.pdp.education.entity.AuthUser;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.AboutMeMapper;
import uz.pdp.education.repository.AboutMeRepository;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.service.AboutMeService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AboutMeServiceImpl implements AboutMeService {
    private final AboutMeMapper aboutMeMapper;
    private final AboutMeRepository aboutMeRepository;
    private final AuthUserRepository authUserRepository;

    @Override
    public Response<?> createMe(AboutMeRequest request) {
        AuthUser teacher = authUserRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found: " + request.getTeacherId()));
        AboutMe aboutMe = aboutMeMapper.toEntity(request);
        if (!teacher.getRole().name().equals("TEACHER")) {
            return Response.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .status(HttpStatus.BAD_REQUEST)
                    .success(false)
                    .message("Teacher not found by role")
                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                    .build();
        }
        aboutMe.setTeacher(teacher);
        aboutMeRepository.save(aboutMe);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutMe successfully created")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getMe(Long id) {
        AboutMe aboutMe = aboutMeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutMe not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutMe successfully found")
                .data(aboutMeMapper.toResponse(aboutMe))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAllMe(Pageable pageable) {
        List<AboutMe> aboutMes = aboutMeRepository.findAll(pageable).getContent();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutMe list successfully found")
                .data(aboutMeMapper.responseList(aboutMes))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> updateMe(AboutMeRequest request, Long id) {
        AboutMe aboutMe = aboutMeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutMe not found: " + id));
        aboutMeMapper.update(aboutMe, request);
        aboutMeRepository.save(aboutMe);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutMe successfully update")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> deleteMe(Long id) {
        AboutMe aboutMe = aboutMeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutMe not found: " + id));
        aboutMeRepository.delete(aboutMe);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutMe successfully deleted")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
