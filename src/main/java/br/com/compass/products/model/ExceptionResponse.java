package br.com.compass.products.model;

public class ExceptionResponse extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;
	private Integer code;

	public ExceptionResponse(String message, Integer code) {
		this.message = message;
		this.code = code;
	}

}
