package io.github.com.ruantarcisio.mscartoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.com.ruantarcisio.mscartoes.domain.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {

	List<ClienteCartao> findByCpf(String cpf);
}
