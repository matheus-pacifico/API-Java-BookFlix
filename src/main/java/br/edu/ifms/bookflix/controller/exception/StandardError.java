package br.edu.ifms.bookflix.controller.exception;

import java.io.Serializable;

public class StandardError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long timestamp;
	private String error;
	private String message;
	private String path;
	
	public StandardError(Long timestamp, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
