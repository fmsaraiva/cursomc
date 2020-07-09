package cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cursomc.domain.Categoria;
import cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	@Transactional(readOnly = true)
	@Query("select distinct obj from Produto obj inner join obj.categorias cat " +
		   "where obj.nome like %:nome% and cat in :categorias")
	public Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	//Esse métod faz a mesma coisa que o "SEARCH", só que usando o padrão por nomes
	//Procurar Spring Data no google (https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference)
	@Transactional(readOnly = true)
	public Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
