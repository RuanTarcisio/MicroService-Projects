package io.github.ruantarcisio.msclientes.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.ruantarcisio.msclientes.domain.dto.ClienteDto;
import io.github.ruantarcisio.msclientes.mapper.ClienteMapper;
import io.github.ruantarcisio.msclientes.service.ClienteService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("clientes")
@Slf4j
public class ClienteController {

	@Autowired
	private ClienteMapper clienteMapper;

	@Autowired
	private ClienteService clienteService;

	
//	@GetMapping
	public String status() {
		log.info("obetendo status");
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<ClienteDto> getCliente(@RequestBody ClienteDto clienteDto) {

		var cliente = clienteMapper.modelToCliente(clienteDto);

		cliente = clienteService.save(cliente);

		URI headerLocation = ServletUriComponentsBuilder.fromCurrentContextPath().query("cpf={cpf}")
				.buildAndExpand(cliente.getCpf()).toUri();

		return ResponseEntity.created(headerLocation).build();
	}

	@GetMapping
	public ResponseEntity<ClienteDto> buscaCliente(@RequestParam("cpf") String cpf) {

		var cliente = clienteService.findByCpf(cpf);

		if (cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(clienteMapper.clienteToDTO(cliente.get()));
	}

}
