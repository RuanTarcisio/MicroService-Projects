package io.github.com.ruantarcisio.mscartoes.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.com.ruantarcisio.mscartoes.domain.dto.CartaoDto;
import io.github.com.ruantarcisio.mscartoes.domain.dto.CartoesClienteDto;
import io.github.com.ruantarcisio.mscartoes.mapper.CartaoMapper;
import io.github.com.ruantarcisio.mscartoes.service.CartaoService;
import io.github.com.ruantarcisio.mscartoes.service.ClienteCartaoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cartoes")
@Slf4j
public class CartaoController {

	@Autowired
	private CartaoMapper cartaoMapper;

	@Autowired
	private CartaoService cartaoService;
@Autowired
	private ClienteCartaoService clienteCartaoService;
	
	@GetMapping
	public String status() {
		log.info("obetendo status");
		return "ok";
	}
		
	 @GetMapping(value = "/{id}")
	 public ResponseEntity<CartaoDto> buscaCartaoPorId(@PathVariable Long id) {
	
	   var cartaoDto = cartaoService.buscarCartaoPorId(id);
	   return ResponseEntity.ok(cartaoDto);
	 }

	 @PostMapping
	 public ResponseEntity<CartaoDto> salvarCartao(@RequestBody CartaoDto cartaoDto){
	
	   var cartao = cartaoMapper.dtoToCartao(cartaoDto);
	   cartao = cartaoService.save(cartao);
	   
	   URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cartao.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	   
	 }
	 
	 @GetMapping(value = "/buscartodos")
	 public ResponseEntity<List<CartaoDto>> buscaTodosCartoes(){
	
	   var cartoes = cartaoService.getAllCartoes();
	   List<CartaoDto> listDto = cartoes;
	   return ResponseEntity.ok(listDto);
	 }
	 
	 @GetMapping(params = "renda")
	 public ResponseEntity<List<CartaoDto>> buscaTodosCartoesPorRenda(@RequestParam ("renda") Long renda){
	
	   var cartoes = cartaoService.getCartoesRendaMenorIgual(renda);
	   List<CartaoDto> listDto = cartoes;
	   return ResponseEntity.ok(listDto);
	 }
	 
	 @GetMapping(params = "cpf")
	 public ResponseEntity<List<CartoesClienteDto>> getCartoesByCliente(@RequestParam ("cpf") String cpf){
	
	   var cartoes = clienteCartaoService.listCartoesByCpf(cpf);
	   List<CartoesClienteDto> listDto = cartoes.stream()
			   .map(cartao -> CartoesClienteDto.fromModel(cartao)).collect(Collectors.toList());
	   return ResponseEntity.ok(listDto);
	 }
	 
	 
}

