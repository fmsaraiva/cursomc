package cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import cursomc.domain.Cliente;
import cursomc.domain.enums.TipoCliente;
import cursomc.dto.ClienteNewDTO;
import cursomc.repositories.ClienteRepository;
import cursomc.resources.exceptions.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{

	@Autowired ClienteRepository repo;
	
	public void initialize(ClienteInsert ann) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getTipo() == null) {
			list.add(new FieldMessage("tipo","Tipo não pode ser nulo"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA) && objDto.getCpfCnpj().length() != 11) {
			list.add(new FieldMessage("cpfCnpj","CPF inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA) && objDto.getCpfCnpj().length() != 14) {
			list.add(new FieldMessage("cpfCnpj","CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		
		if(aux != null) {
			list.add(new FieldMessage("email","Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
			       .addPropertyNode(e.getFiledName())
			       .addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
