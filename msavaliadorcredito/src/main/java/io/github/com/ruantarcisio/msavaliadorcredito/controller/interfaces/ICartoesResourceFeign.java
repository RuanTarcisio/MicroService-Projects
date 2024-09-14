package io.github.com.ruantarcisio.msavaliadorcredito.controller.interfaces;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.com.ruantarcisio.msavaliadorcredito.domain.Cartao;
import io.github.com.ruantarcisio.msavaliadorcredito.domain.dtos.CartaoClienteDto;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface ICartoesResourceFeign {
	
	@GetMapping(params = "cpf")
	 ResponseEntity<List<CartaoClienteDto>> getCartoesByCliente(@RequestParam ("cpf") String cpf);
	
	 @GetMapping(params = "renda")
	 public ResponseEntity<List<Cartao>> buscaTodosCartoesPorRenda(@RequestParam ("renda") Long renda);
}
