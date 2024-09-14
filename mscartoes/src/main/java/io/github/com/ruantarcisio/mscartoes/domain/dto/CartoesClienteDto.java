package io.github.com.ruantarcisio.mscartoes.domain.dto;

import java.math.BigDecimal;

import io.github.com.ruantarcisio.mscartoes.domain.ClienteCartao;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartoesClienteDto {
	
	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
	
	public static CartoesClienteDto fromModel (ClienteCartao model) {
		return CartoesClienteDto
				.builder()
				.nome(model.getNome())
				.bandeira(model.getCartao().getBandeira().toString())
				.limiteLiberado(model.getLimite())
				.build();
	}

}
