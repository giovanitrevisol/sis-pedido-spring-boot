package com.giovanitrevisol.sispedido.services;

import java.util.List;
import java.util.Optional;

import com.giovanitrevisol.sispedido.domain.Categoria;
import com.giovanitrevisol.sispedido.dto.CategoriaDTO;
import com.giovanitrevisol.sispedido.repositories.CategoriaRepository;
import com.giovanitrevisol.sispedido.services.exception.DataIntegrateException;
import com.giovanitrevisol.sispedido.services.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado!!! - Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj){
        Categoria newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id){
        this.find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrateException("Não é possivel excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll(){
        return repo.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);
        return  repo.findAll(pageRequest);
        //neste return existe uma sobrecarga de metodos, ou seja estou chamando o mesmo metodos find passando
        //pageRequest por parametro.
    }

    public Categoria fromDTO (CategoriaDTO objDTO){
        return new Categoria(objDTO.getId(), objDTO.getNome());
    }

    private void updateData(Categoria newObj, Categoria obj){
        newObj.setNome(obj.getNome());
    }


}
