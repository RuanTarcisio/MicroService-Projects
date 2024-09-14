package io.github.com.ruantarcisio.msavaliadorcredito.controller.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.com.ruantarcisio.msavaliadorcredito.domain.dtos.DadosClienteDto;

@FeignClient(value = "msclientes", path = "/clientes")
public interface IClienteResourceFeign {
	
	@GetMapping
	ResponseEntity<DadosClienteDto> buscaCliente(@RequestParam("cpf") String cpf);
	
	
}
