package br.com.cotiinformatica.interfaces;

import java.util.UUID;

import org.springframework.data.domain.Page;

import br.com.cotiinformatica.dtos.ClienteRequest;
import br.com.cotiinformatica.dtos.ClienteResponse;

public interface ClienteService {

	ClienteResponse adicionar(ClienteRequest request);
	
	ClienteResponse atualizar(UUID id, ClienteRequest request);
	
	ClienteResponse excluir(UUID id);
	
	Page<ClienteResponse> consultar(int pagina, int tamanho, String ordenacao);
	
	ClienteResponse obterPorId(UUID id);
}
