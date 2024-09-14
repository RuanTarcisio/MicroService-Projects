package io.github.com.ruantarcisio.mscartoes.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.com.ruantarcisio.mscartoes.domain.Cartao;

public interface CartaoRepository  extends JpaRepository<Cartao, Long>{

	List<Cartao> findAllByRendaLessThanEqual(BigDecimal rendaBigDecimal);

}
