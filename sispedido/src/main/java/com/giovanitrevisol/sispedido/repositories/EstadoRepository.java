package com.giovanitrevisol.sispedido.repositories;


import com.giovanitrevisol.sispedido.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
