package io.github.com.ruantarcisio.msavaliadorcredito.controller;

import io.github.com.ruantarcisio.msavaliadorcredito.rabbit.events.SolicitacaoEmissaoCartaoEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.com.ruantarcisio.msavaliadorcredito.domain.RetornoAvaliacaoCliente;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.SituacaoCliente;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.dtos.DadosAvaliacaoDto;
import io.github.com.ruantarcisio.msavaliadorcredito.service.AvaliadorCreditoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {
	
	private final AvaliadorCreditoService avaliadorCreditoService;	 
		
	
	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf){
		SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
	return ResponseEntity.ok(situacaoCliente);
	}
	
	@PostMapping
	public ResponseEntity<RetornoAvaliacaoCliente> realizarAvaliacao( @RequestBody DadosAvaliacaoDto dados) {
		RetornoAvaliacaoCliente retornoAvaliacao = avaliadorCreditoService.realizarAvaliacao(dados);
		return ResponseEntity.ok(retornoAvaliacao);
	}

	@PostMapping("solicitacoes-cartao")
	public ResponseEntity solicitarCartao(@RequestBody SolicitacaoEmissaoCartaoEvent dados){
		try{
			String protocoloSolicitacaoCartao = avaliadorCreditoService
					.solicitarEmissaoCartao(dados);
			return ResponseEntity.ok(protocoloSolicitacaoCartao);
		}catch (Exception e){
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
