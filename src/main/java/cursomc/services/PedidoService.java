package cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cursomc.domain.ItemPedido;
import cursomc.domain.PagamentoComBoleto;
import cursomc.domain.Pedido;
import cursomc.domain.Produto;
import cursomc.domain.enums.EstadoPagamento;
import cursomc.repositories.ItemPedidoRepository;
import cursomc.repositories.PagamentoRepository;
import cursomc.repositories.PedidoRepository;
import cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	@Autowired
	ClienteService clienteService;
	@Autowired
	BoletoService boletoService;
	@Autowired
	PagamentoRepository pagmentoRepository;
	@Autowired
	ProdutoService produtoService;
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {		
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto n�o encontrado! Id: " + id));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null); //Pra garantir que vai fazer um insert
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find( obj.getCliente().getId() ));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagmentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			Produto p = produtoService.find(ip.getProduto().getId());
			ip.setProduto(p);
			ip.setPreco(p.getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		System.out.println(obj);
		return obj;
	}
}
