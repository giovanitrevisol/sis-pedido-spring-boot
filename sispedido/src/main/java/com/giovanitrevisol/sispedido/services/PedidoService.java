package com.giovanitrevisol.sispedido.services;

import com.giovanitrevisol.sispedido.domain.ItemPedido;
import com.giovanitrevisol.sispedido.domain.PagamentoComBoleto;
import com.giovanitrevisol.sispedido.domain.Pedido;
import com.giovanitrevisol.sispedido.domain.Produto;
import com.giovanitrevisol.sispedido.domain.enums.EstadoPagamento;
import com.giovanitrevisol.sispedido.repositories.ItemPedidoRepository;
import com.giovanitrevisol.sispedido.repositories.PagamentoRepository;
import com.giovanitrevisol.sispedido.repositories.PedidoRepository;
import com.giovanitrevisol.sispedido.repositories.ProdutoRepository;
import com.giovanitrevisol.sispedido.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    public Pedido buscar(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado!!! - Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagto = (PagamentoComBoleto)  obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for(ItemPedido ip : obj.getItens()){
            ip.setDesconto(0.0);
            ip.setPreco(produtoRepository.findById(ip.getProduto().getId()).get().getPreco());
            ip.setPedido(obj);
            }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
        }
}
