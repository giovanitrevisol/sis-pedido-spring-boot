package com.giovanitrevisol.sispedido.repositories;

import com.giovanitrevisol.sispedido.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    ///somento com isso o Spring ja intende que é para fazer uma busca por email
    ///sempre usar findBy + nome do campo no domain
    @Transactional(readOnly = true)
    Cliente findByEmail(String email);
}
