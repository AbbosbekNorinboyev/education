package uz.pdp.education.validation;

import org.springframework.stereotype.Component;
import uz.pdp.education.dto.ErrorDTO;
import uz.pdp.education.dto.StudentCreateDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentValidation {
    public List<ErrorDTO> validate(StudentCreateDTO studentCreateDTO) {
        List<ErrorDTO> errors = new ArrayList<>();
        if (studentCreateDTO.getAge()<=0){
            errors.add(new ErrorDTO("age", "age can not be nagative or zero"));
        }
        return errors;
    }
}
