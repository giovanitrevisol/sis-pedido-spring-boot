package com.giovanitrevisol.sispedido.resources;



import com.giovanitrevisol.sispedido.domain.Categoria;
import com.giovanitrevisol.sispedido.domain.Cliente;
import com.giovanitrevisol.sispedido.dto.CategoriaDTO;
import com.giovanitrevisol.sispedido.dto.ClienteDTO;
import com.giovanitrevisol.sispedido.dto.ClienteNewDTO;
import com.giovanitrevisol.sispedido.services.ClienteService;
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
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto){
        Cliente obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        //este metodo a cima retorna a URI com o ID do objeto inserido
        return ResponseEntity.created(uri).build();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
        Cliente obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }
    //metodo para atualizar uma Cliente exixtente
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        //converter esta lista de outra forma - 37
        List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    //nesta funcao os parametros são passados como parâmetros
    // - /Clientes/page?page=0&lineperpage=20...
    @GetMapping(value = "/page")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        Page<Cliente> list = service.findPage(page,linesPerPage, orderBy, direction);
        Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
        return ResponseEntity.ok().body(listDTO);
    }
}
