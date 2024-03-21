package io.github.com.ruantarcisio.mscartoes.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.com.ruantarcisio.mscartoes.domain.Cartao;
import io.github.com.ruantarcisio.mscartoes.domain.dto.CartaoDto;
import io.github.com.ruantarcisio.mscartoes.mapper.CartaoMapper;
import io.github.com.ruantarcisio.mscartoes.repository.CartaoRepository;

@Service
public class CartaoService {

	@Autowired
	private CartaoRepository repository;
	@Autowired
	private CartaoMapper mapper;
	
	public CartaoDto buscarCartaoPorId(Long id) {
		var card = repository.findById(id);
		return mapper.cartaoToDto(card.get());
	}
	
	public Cartao save(Cartao card) {
		return repository.save(card);
	}
	
	public List<CartaoDto> getCartoesRendaMenorIgual(Long renda){
		var rendaBigDecimal = BigDecimal.valueOf(renda);
		return repository.findAllByRendaLessThanEqual(rendaBigDecimal).stream().map(cartao -> mapper.cartaoToDto(cartao)).collect(Collectors.toList());
	}
	
	public List<CartaoDto> getAllCartoes(){
		
		return repository.findAll().stream().map(cartao -> mapper.cartaoToDto(cartao)).collect(Collectors.toList());
	}
	
	
	
	
}
