package io.github.com.ruantarcisio.msavaliadorcredito.service.exception;

import lombok.Getter;

@Getter
public class ErroComunicacaoMicroServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private Integer status;
	
	public ErroComunicacaoMicroServiceException(String message, Integer status) {
		super(message);
		this.status = status;
		
	}
	
	
	
}
