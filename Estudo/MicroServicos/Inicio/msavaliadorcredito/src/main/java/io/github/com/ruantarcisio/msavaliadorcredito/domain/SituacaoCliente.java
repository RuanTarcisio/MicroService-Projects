package io.github.com.ruantarcisio.msavaliadorcredito.domain;

import java.util.List;

import io.github.com.ruantarcisio.msavaliadorcredito.domain.dtos.CartaoClienteDto;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.dtos.DadosClienteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituacaoCliente {

	private DadosClienteDto cliente;
	private List<CartaoClienteDto> cartoes;
}
