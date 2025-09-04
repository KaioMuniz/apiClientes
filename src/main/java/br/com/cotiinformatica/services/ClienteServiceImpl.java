package br.com.cotiinformatica.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.cotiinformatica.dtos.ClienteRequest;
import br.com.cotiinformatica.dtos.ClienteResponse;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.interfaces.ClienteService;
import br.com.cotiinformatica.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired ClienteRepository clienteRepository;
	@Autowired ModelMapper modelMapper;
	
	@Override
	public ClienteResponse adicionar(ClienteRequest request) {

		var cliente = modelMapper.map(request, Cliente.class);
		cliente.setId(UUID.randomUUID());
		
		clienteRepository.save(cliente);
		
		return modelMapper.map(cliente, ClienteResponse.class);
	}

	@Override
	public ClienteResponse atualizar(UUID id, ClienteRequest request) {
		
		if(!clienteRepository.existsById(id))
			throw new ResponseStatusException
				(HttpStatus.BAD_REQUEST, "Cliente não encontrado para edição.");
			
		var cliente = modelMapper.map(request, Cliente.class);
		cliente.setId(id);
		
		clienteRepository.save(cliente);
		
		return modelMapper.map(cliente, ClienteResponse.class);
	}

	@Override
	public ClienteResponse excluir(UUID id) {

		var cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException
						(HttpStatus.GONE, "Cliente não encontrado para exclusão."));
		
		clienteRepository.delete(cliente);
		
		return modelMapper.map(cliente, ClienteResponse.class);
	}

	@Override
	public Page<ClienteResponse> consultar(int pagina, int tamanho, String ordenacao) {
		
		if(tamanho > 25)
			throw new ResponseStatusException
				(HttpStatus.BAD_REQUEST, "O tamanho limite para paginação é de 25 registros.");
		
		var sort = Sort.by(ordenacao);
		var page = PageRequest.of(pagina, tamanho, sort);
		
		var clientes = clienteRepository.findAll(page);
		
		var response = clientes.getContent().stream()
				.map(cliente -> modelMapper.map(cliente, ClienteResponse.class))
				.toList();
		
		return new PageImpl<>(response, page, clientes.getTotalElements());
	}

	@Override
	public ClienteResponse obterPorId(UUID id) {

		var cliente = clienteRepository.findById(id);
		
		if(cliente.isEmpty())
			return null;
		
		return modelMapper.map(cliente.get(), ClienteResponse.class);		
	}

}






