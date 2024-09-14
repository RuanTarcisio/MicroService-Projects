package io.github.com.ruantarcisio.mscartoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.com.ruantarcisio.mscartoes.domain.ClienteCartao;
import io.github.com.ruantarcisio.mscartoes.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public final class ClienteCartaoService {

	@Autowired
	private final ClienteCartaoRepository repository;

	public List<ClienteCartao> listCartoesByCpf(String cpf) {
		List<ClienteCartao> listCartoes = repository.findByCpf(cpf);
		return listCartoes;
	}
}
