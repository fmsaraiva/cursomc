package cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
