package com.wyden.bibi.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wyden.bibi.model.Categoria;
import com.wyden.bibi.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// metodo que encapsula todas as erequisicoes HTTP. ? para dizer que pode ser
	// qualquer um.
	public ResponseEntity<?> buscar(@PathVariable Integer id) {

		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);

	}
 
	@RequestMapping(method = RequestMethod.POST)
	//@RequestBody anotcao que faz o Json ser convertido para um objeto automaticamente.
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		obj = service.insert(obj);
		// pegando a url e passando o numero de resposta.
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId_categoria()).toUri();
		
	   return ResponseEntity.created(uri).build();
	}

}
