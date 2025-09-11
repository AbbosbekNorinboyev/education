package uz.pdp.education.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.education.dto.request.AboutUsRequest;
import uz.pdp.education.dto.response.Response;
import uz.pdp.education.entity.AboutUs;
import uz.pdp.education.exception.ResourceNotFoundException;
import uz.pdp.education.mapper.AboutUsMapper;
import uz.pdp.education.repository.AboutUsRepository;
import uz.pdp.education.repository.AuthUserRepository;
import uz.pdp.education.service.AboutUsService;

import java.time.LocalDateTime;
import java.util.List;

import static uz.pdp.education.utils.Util.localDateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AboutUsServiceImpl implements AboutUsService {
    private final AboutUsMapper aboutUsMapper;
    private final AboutUsRepository aboutUsRepository;
    private final AuthUserRepository authUserRepository;

    @Override
    public Response<?> createUs(AboutUsRequest request) {
        AboutUs aboutUs = aboutUsMapper.toEntity(request);
        aboutUsRepository.save(aboutUs);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutUs successfully created")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getUs(Long id) {
        AboutUs aboutUs = aboutUsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found: " + id));
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutUs successfully found")
                .data(aboutUsMapper.toResponse(aboutUs))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> getAllUs() {
        List<AboutUs> aboutUses = aboutUsRepository.findAll();
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutUs list successfully found")
                .data(aboutUsMapper.responseList(aboutUses))
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> updateUs(AboutUsRequest request, Long id) {
        AboutUs aboutUs = aboutUsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found: " + id));
        aboutUsMapper.update(aboutUs, request);
        aboutUsRepository.save(aboutUs);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutUs successfully updated")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }

    @Override
    public Response<?> deleteUs(Long id) {
        AboutUs aboutUs = aboutUsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AboutUs not found: " + id));
        aboutUsRepository.delete(aboutUs);
        return Response.builder()
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .success(true)
                .message("AboutUs successfully deleted")
                .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                .build();
    }
}
