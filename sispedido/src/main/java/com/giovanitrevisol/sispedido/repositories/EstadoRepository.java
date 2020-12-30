package com.giovanitrevisol.sispedido.repositories;


import com.giovanitrevisol.sispedido.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    @Transactional(readOnly=true)
    public List<Estado> findAllByOrderByNome();
}
