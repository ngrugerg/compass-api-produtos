package br.com.compass.products.handler.dto;

public class FormErrorDto {

	private String message;
	private Integer code;

	public FormErrorDto(String message, Integer code) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}

}
