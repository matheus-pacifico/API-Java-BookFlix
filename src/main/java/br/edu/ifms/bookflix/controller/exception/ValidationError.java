package br.edu.ifms.bookflix.controller.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Long timestamp, String error, String message, String path) {
		super(timestamp, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
	
}
