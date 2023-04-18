package br.edu.ifms.bookflix.controller.exception;

import br.edu.ifms.bookflix.service.exception.DataIntegrityException;
import br.edu.ifms.bookflix.service.exception.ObjectNotFoundException;
import br.edu.ifms.bookflix.service.exception.FileNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URISyntaxException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(Instant.now().toEpochMilli(),
				"Não encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {

		StandardError err = new StandardError(Instant.now().toEpochMilli(),
				"Integridade de dados", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(Instant.now().toEpochMilli(),
				"Erro de validação", e.getMessage(), request.getRequestURI());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(Instant.now().toEpochMilli(), 
				"Parâmetro inválido", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(URISyntaxException.class)
	public ResponseEntity<StandardError> uriSyntax(URISyntaxException e, HttpServletRequest request) {

		StandardError err = new StandardError(Instant.now().toEpochMilli(),
				"URI Syntax Exception", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<StandardError> maxUploadSize(MaxUploadSizeExceededException e, HttpServletRequest request) {

		StandardError err = new StandardError(Instant.now().toEpochMilli(),
				"Tamanho do arquivo", "O tamanho máximo do arquivo é de 10MB", request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<StandardError> fileNotFound(FileNotFoundException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(Instant.now().toEpochMilli(), 
				"Arquivo não Encontrado", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> unkownError(Exception e, HttpServletRequest request) {
		
		StandardError err = new StandardError(Instant.now().toEpochMilli(), 
				"Um erro desconhecido ocorreu", e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

}
