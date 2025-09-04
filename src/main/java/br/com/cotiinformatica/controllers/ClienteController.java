package br.com.cotiinformatica.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.ClienteRequest;
import br.com.cotiinformatica.dtos.ClienteResponse;
import br.com.cotiinformatica.interfaces.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

	@Autowired ClienteService clienteService;
	
	@PostMapping
	public ResponseEntity<ClienteResponse> post(@RequestBody @Valid ClienteRequest request) {
		return ResponseEntity.ok(clienteService.adicionar(request));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ClienteResponse> put(@PathVariable UUID id, @RequestBody @Valid ClienteRequest request) {
		return ResponseEntity.ok(clienteService.atualizar(id, request));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ClienteResponse> delete(@PathVariable UUID id) {
		return ResponseEntity.ok(clienteService.excluir(id));
	}
	
	@GetMapping
	public ResponseEntity<Page<ClienteResponse>> getAll(
			@RequestParam(defaultValue = "0") int pagina,
			@RequestParam(defaultValue = "25") int tamanho,
			@RequestParam(defaultValue = "nome") String ordenacao
			) {		
		return ResponseEntity.ok(clienteService.consultar(pagina, tamanho, ordenacao));
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ClienteResponse> getById(@PathVariable UUID id) {
		var cliente = clienteService.obterPorId(id);
		if(cliente != null)
			return ResponseEntity.ok(cliente);
		
		return ResponseEntity.status(204).build();			
	}
}







