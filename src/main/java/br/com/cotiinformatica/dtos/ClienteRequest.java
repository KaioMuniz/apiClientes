package br.com.cotiinformatica.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteRequest {

	@NotBlank(message = "Nome é obrigatório.")
	@Size(min = 8, max = 150, message = "O nome deve ter de 8 a 150 caracteres.")
	private String nome;
	
	@NotBlank(message = "Email é obrigatório.")
	@Email(message = "Informe um endereço de email válido.")
	private String email;
	
	@NotBlank(message = "Endereço é obrigatório.")
	@Size(min = 8, max = 250, message = "O endereço deve ter de 8 a 250 caracteres.")
	private String endereco;
	
	@NotNull(message = "Data de nascimento é obrigatória.")
	@Pattern(
			regexp = "^\\d{4}-\\d{2}-\\d{2}$",
			message = "A data de nascimento deve estar no formato 'yyyy-MM-dd'"
	)
	private String dataNascimento;
}
