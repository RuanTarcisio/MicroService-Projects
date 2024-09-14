package io.github.com.ruantarcisio.mscartoes.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.com.ruantarcisio.mscartoes.domain.Cartao;
import io.github.com.ruantarcisio.mscartoes.domain.dto.CartaoDto;

@Component
public class CartaoMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public Cartao dtoToCartao(CartaoDto dto) {
		
		return modelMapper.map(dto, Cartao.class);
	}
	
	public CartaoDto cartaoToDto(Cartao card) {
		
		return modelMapper.map(card, CartaoDto.class);
	}
	
	public List<CartaoDto> listCartaoToListCartaoDto(List<Cartao> listCartao){
		
		return listCartao.stream().map(cartao -> cartaoToDto(cartao)).collect(Collectors.toList());
		
	}

}
