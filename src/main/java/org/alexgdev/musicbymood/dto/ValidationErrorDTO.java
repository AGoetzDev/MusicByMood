package org.alexgdev.musicbymood.dto;

import java.util.ArrayList;
import java.util.List;



import lombok.Data;

@Data
public class ValidationErrorDTO {
	
	private List<FieldErrorDTO> fieldErrors = new ArrayList<>();


    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }

}