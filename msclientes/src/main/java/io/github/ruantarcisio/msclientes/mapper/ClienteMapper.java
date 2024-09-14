package io.github.ruantarcisio.msclientes.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ruantarcisio.msclientes.domain.Cliente;
import io.github.ruantarcisio.msclientes.domain.dto.ClienteDto;

@Component
public class ClienteMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public Cliente modelToCliente(ClienteDto dto) {
		
		return modelMapper.map(dto, Cliente.class);
	}
	
	public ClienteDto clienteToDTO(Cliente cliente) {
		
		return modelMapper.map(cliente, ClienteDto.class);
	}


}
