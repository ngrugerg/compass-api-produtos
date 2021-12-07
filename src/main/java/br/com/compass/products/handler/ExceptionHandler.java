package br.com.compass.products.handler;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.compass.products.model.ExceptionResponse;

@RestControllerAdvice
public class ExceptionHandler {

	private String errors = "";

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionResponse handle(MethodArgumentNotValidException exception) {

		exception.getBindingResult().getFieldErrors().forEach((error) -> {
			this.errors += error.getField() + ": " + error.getDefaultMessage() + ". ";
		});

		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), errors);
		this.errors = "";

		return exceptionResponse;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ExceptionResponse NotValidException(MethodArgumentTypeMismatchException exception) {
		return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Page Unavailable.");
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
	public ExceptionResponse NotValidException(EntityNotFoundException exception) {
		return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Product not Found.");
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@org.springframework.web.bind.annotation.ExceptionHandler(EmptyResultDataAccessException.class)
	public ExceptionResponse NotValidException(EmptyResultDataAccessException exception) {
		return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Product not Found.");
	}

	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	@org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ExceptionResponse NotValidException(HttpRequestMethodNotSupportedException exception) {
		return new ExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), "Method not allowed.");
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ExceptionResponse NotValidException() {
		return new ExceptionResponse (HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error.");
	}

}
