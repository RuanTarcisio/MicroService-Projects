package io.github.ruantarcisio.msclientes.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.ruantarcisio.msclientes.domain.Cliente;
import io.github.ruantarcisio.msclientes.infra.repository.ClienteRepository;

@Service
public class ClienteService {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> findByCpf(String cpf){
		return clienteRepository.findByCpf(cpf);
	}
}
