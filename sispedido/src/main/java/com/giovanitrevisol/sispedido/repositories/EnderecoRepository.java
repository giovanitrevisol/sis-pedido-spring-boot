package com.giovanitrevisol.sispedido.repositories;

import com.giovanitrevisol.sispedido.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
