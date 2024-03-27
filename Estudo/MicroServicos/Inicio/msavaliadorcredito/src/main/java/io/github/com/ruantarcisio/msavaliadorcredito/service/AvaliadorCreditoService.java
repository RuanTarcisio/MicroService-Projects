package io.github.com.ruantarcisio.msavaliadorcredito.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException;
import io.github.com.ruantarcisio.msavaliadorcredito.controller.interfaces.ICartoesResourceFeign;
import io.github.com.ruantarcisio.msavaliadorcredito.controller.interfaces.IClienteResourceFeign;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.Cartao;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.CartaoAprovado;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.RetornoAvaliacaoCliente;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.SituacaoCliente;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.dtos.CartaoClienteDto;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.dtos.DadosAvaliacaoDto;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.dtos.DadosClienteDto;
import io.github.com.ruantarcisio.msavaliadorcredito.service.exception.DadosClienteNotFoundException;
import io.github.com.ruantarcisio.msavaliadorcredito.service.exception.ErroComunicacaoMicroServiceException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

	private final IClienteResourceFeign clienteResourceFeign;
	private final ICartoesResourceFeign cartoesResourceFeign;

	public SituacaoCliente obterSituacaoCliente(String cpf)
			throws DadosClienteNotFoundException, ErroComunicacaoMicroServiceException {

		try {
			ResponseEntity<DadosClienteDto> dadosClienteDto = clienteResourceFeign.buscaCliente(cpf);
			ResponseEntity<List<CartaoClienteDto>> cartoesClienteDto = cartoesResourceFeign.getCartoesByCliente(cpf);

			return SituacaoCliente.builder().cliente(dadosClienteDto.getBody()).cartoes(cartoesClienteDto.getBody())
					.build();
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException("Dados do cliente não encontrado.");
			}
			throw new ErroComunicacaoMicroServiceException(e.getMessage(), status);
		}

	}

	public RetornoAvaliacaoCliente realizarAvaliacao(DadosAvaliacaoDto dados) {

		try {
			ResponseEntity<DadosClienteDto> dadosClienteDto = clienteResourceFeign.buscaCliente(dados.getCpf());
			ResponseEntity<List<Cartao>> cartoesDto = cartoesResourceFeign.buscaTodosCartoesPorRenda(dados.getRenda());

			List<Cartao> cartoes = cartoesDto.getBody();
			var listaCartoesAprovados = cartoes.stream().map(cartao -> {
				DadosClienteDto dadosCliente = dadosClienteDto.getBody();

				BigDecimal limiteBasico = cartao.getLimiteBasico();
				BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
				var fator = idadeBD.divide(BigDecimal.valueOf(10));
				var limiteAprovado = fator.multiply(limiteBasico);

				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(cartao.getNome());
				aprovado.setBandeira(cartao.getBandeira());
				aprovado.setLimiteAprovado(limiteAprovado);

				return aprovado;
			}).collect(Collectors.toList());

			return new RetornoAvaliacaoCliente(listaCartoesAprovados);

		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException("Dados do cliente não encontrado.");
			}
			throw new ErroComunicacaoMicroServiceException(e.getMessage(), status);
		}
	}


}
