package br.com.compass.products.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.compass.products.handler.dto.FormErrorDto;

@RestControllerAdvice
public class ExceptionHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FormErrorDto> handle(MethodArgumentNotValidException exception) {
		List<FormErrorDto> dto = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			FormErrorDto error = new FormErrorDto(e.getDefaultMessage(), HttpStatus.BAD_REQUEST.value());
			dto.add(error);
		});
		
		return dto;
	}
	
}


