package com.giovanitrevisol.sispedido.repositories;

import com.giovanitrevisol.sispedido.domain.Pagamento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
