package uz.pdp.education.validation;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.ErrorDTO;
import uz.pdp.education.dto.TeacherCreateDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeacherValidation {
    public List<ErrorDTO> validate(TeacherCreateDTO teacherCreateDTO) {
        List<ErrorDTO> errors = new ArrayList<>();
        if (teacherCreateDTO.getAge() <= 0) {
            errors.add(new ErrorDTO("age", "age can not be negative or zero"));
        }
        return errors;
    }
}
