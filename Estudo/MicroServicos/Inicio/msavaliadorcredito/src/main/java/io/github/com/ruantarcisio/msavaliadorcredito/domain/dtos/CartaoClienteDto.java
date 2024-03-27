package io.github.com.ruantarcisio.msavaliadorcredito.domain.dtos;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class CartaoClienteDto {

	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
}
