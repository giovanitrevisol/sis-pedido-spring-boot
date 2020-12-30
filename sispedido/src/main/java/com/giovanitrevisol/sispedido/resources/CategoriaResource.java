package com.giovanitrevisol.sispedido.resources;

import com.giovanitrevisol.sispedido.domain.Categoria;
import com.giovanitrevisol.sispedido.dto.CategoriaDTO;
import com.giovanitrevisol.sispedido.services.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    // caminho para acessar = http://localhost:8080/categorias
    // @RequestMapping(method = RequestMethod.GET)


    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }


    //metodo para receber uma nova categoria e inserir no banco
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){
        Categoria obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        //este metodo a cima retorna a URI com o ID do objeto inserido
        return ResponseEntity.created(uri).build();
    }

    //metodo para atualizar uma categoria exixtente
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id){
        Categoria obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
    //metodo para atualizar uma categoria exixtente
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
       service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> list = service.findAll();
        //converter esta lista de outra forma - 37
        List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    //nesta funcao os parametros são passados como parâmetros
    // - /categorias/page?page=0&lineperpage=20...
    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        Page<Categoria> list = service.findPage(page,linesPerPage, orderBy, direction);
        Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }
}
