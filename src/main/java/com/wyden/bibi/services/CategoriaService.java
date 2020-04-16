package com.wyden.bibi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wyden.bibi.dto.CategoriaDTO;
import com.wyden.bibi.model.Categoria;
import com.wyden.bibi.repositories.CategoriaRepository;
import com.wyden.bibi.services.exceptions.DataIntegrityException;
import com.wyden.bibi.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {
	@Autowired
    private CategoriaRepository  repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Categoria não encontrada! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		}
	
	
	public Categoria insert(Categoria obj ) {
		//o id precisa estar null para que esse metod entenda que e uma insercao e nao uma atualizacao.
		obj.setId_categoria(null);
		return repo.save(obj);
	}
	
	
	public Categoria update(Categoria obj ) {
		//chamando o metodo find caso nao exista o id ele me retorna a excecao.
		find(obj.getId_categoria());
		return repo.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			//Tratamento de exceção personalizado para alertar a falha ao excluir uma categoria deu possui livros atrelas a ela..
			throw new DataIntegrityException("Não é possivel excluir uma categoria que contem livros.");
			
		}
		
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
		
	}
	
	//**PAGINACAO**//
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
	    //PAGEREQUEST PREPARA AS REQUISICOES PARA FAZER A CONSULTA NO BANCO DE DADOS.
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),
				orderBy);
		//AQUI RETONA A PAGINA A PARTIR DO pageRequest.
		return repo.findAll(pageRequest);
		
	}
	
	//METODO AUXILIAR QUE INSTANCIA UMA CATEGORIA A APARTIR DE UM DTO.
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId_categoria(), objDTO.getNome());		
	}
	
}
