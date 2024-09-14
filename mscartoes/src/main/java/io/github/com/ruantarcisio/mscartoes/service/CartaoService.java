package io.github.com.ruantarcisio.mscartoes.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.com.ruantarcisio.mscartoes.domain.ClienteCartao;
import io.github.com.ruantarcisio.mscartoes.rabbit.events.SolicitacaoEmissaoCartaoEvent;
import io.github.com.ruantarcisio.mscartoes.repository.ClienteCartaoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.com.ruantarcisio.mscartoes.domain.Cartao;
import io.github.com.ruantarcisio.mscartoes.domain.dto.CartaoDto;
import io.github.com.ruantarcisio.mscartoes.mapper.CartaoMapper;
import io.github.com.ruantarcisio.mscartoes.repository.CartaoRepository;

@Service
public class CartaoService {

	@Autowired
	private ClienteCartaoRepository clienteCartaoRepository;
	@Autowired
	private CartaoMapper mapper;
    @Autowired
    private CartaoRepository cartaoRepository;

	public CartaoDto buscarCartaoPorId(Long id) {
		var card = cartaoRepository.findById(id);
		return mapper.cartaoToDto(card.get());
	}
	
	public Cartao save(Cartao card) {
		return cartaoRepository.save(card);
	}
	
	public List<CartaoDto> getCartoesRendaMenorIgual(Long renda){
		var rendaBigDecimal = BigDecimal.valueOf(renda);
		return cartaoRepository.findAllByRendaLessThanEqual(rendaBigDecimal).stream().map(cartao -> mapper.cartaoToDto(cartao)).collect(Collectors.toList());
	}
	
	public List<CartaoDto> getAllCartoes(){
		
		return cartaoRepository.findAll().stream().map(cartao -> mapper.cartaoToDto(cartao)).collect(Collectors.toList());
	}

	@RabbitListener(queues = "mscartoes.emissao-cartao")
	public void receberSolicitacaoEmissao(SolicitacaoEmissaoCartaoEvent event){

		Cartao card = cartaoRepository.findById(event.getIdCartao()).orElseThrow();
		ClienteCartao clienteCartao = new ClienteCartao();
		clienteCartao.setCartao(card);
		clienteCartao.setCpf(event.getCpf());
		clienteCartao.setLimite(event.getLimiteLiberado());

		clienteCartaoRepository.save(clienteCartao);
//		List<UsuarioDto> users = usuarioService.queSeguemPais(event.getCountryCode());
//		List<String> listEmails = new ArrayList<>();
//		String mensagem = event.getCountryName() + " acabou de ganhar uma medalha de "
//				+event.getTypeMedal() + " no sport " + event.getSportName();
//		String assunto = event.getCountryName() + " acabou de ganhar uma medalha de " + event.getTypeMedal();
//
//		for(UsuarioDto usuarioDto : users){
//			listEmails.add(usuarioDto.email());
//		}
//		sendEmail(listEmails,assunto, mensagem);
	}
	
	
}
