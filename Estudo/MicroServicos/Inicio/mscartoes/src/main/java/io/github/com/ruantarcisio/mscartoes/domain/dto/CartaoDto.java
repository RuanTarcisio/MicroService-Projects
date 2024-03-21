package io.github.com.ruantarcisio.mscartoes.domain.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartaoDto {
	
	private String nome;
	private String bandeira;
	private BigDecimal renda;
	private BigDecimal limiteBasico;

}
