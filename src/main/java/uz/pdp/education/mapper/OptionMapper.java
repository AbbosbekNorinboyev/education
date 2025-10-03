package uz.pdp.education.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.OptionsDto;
import uz.pdp.education.entity.Options;

import java.util.ArrayList;
import java.util.List;

@Component
public class OptionMapper {
    public Options toEntity(OptionsDto.OptionsCreteDto optionsDto) {
        return Options.builder()
                .option(optionsDto.getOption())
                .isCorrect(optionsDto.getIsCorrect())
                .build();
    }

    public OptionsDto toDto(Options option) {
        return OptionsDto.builder()
                .id(option.getId())
                .option(option.getOption())
                .isCorrect(option.getIsCorrect())
                .questionId(option.getQuestion() != null ? option.getQuestion().getId() : null)
                .build();
    }

    public List<OptionsDto> dtoList(List<Options> list) {
        if (list != null && !list.isEmpty()) {
            return list.stream().map(this::toDto).toList();
        }
        return new ArrayList<>();
    }
}
