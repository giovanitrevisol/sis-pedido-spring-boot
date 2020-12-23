package com.giovanitrevisol.sispedido.services;

import com.giovanitrevisol.sispedido.domain.Categoria;
import com.giovanitrevisol.sispedido.domain.Pedido;
import com.giovanitrevisol.sispedido.domain.Produto;
import com.giovanitrevisol.sispedido.repositories.CategoriaRepository;
import com.giovanitrevisol.sispedido.repositories.PedidoRepository;
import com.giovanitrevisol.sispedido.repositories.ProdutoRepository;
import com.giovanitrevisol.sispedido.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto buscar(Integer id) {
        Optional<Produto> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado!!! - Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),
                orderBy);

        List<Categoria> categorias = categoriaRepository.findAll();
        return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
