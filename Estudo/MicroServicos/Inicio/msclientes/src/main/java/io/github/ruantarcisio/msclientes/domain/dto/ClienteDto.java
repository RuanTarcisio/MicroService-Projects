package io.github.ruantarcisio.msclientes.domain.dto;

import lombok.Data;

@Data
public class ClienteDto{
	private String cpf;
	private String nome;
	private Integer idade;
}
