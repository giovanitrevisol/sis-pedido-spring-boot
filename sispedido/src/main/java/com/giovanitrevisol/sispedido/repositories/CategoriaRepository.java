package com.giovanitrevisol.sispedido.repositories;

import com.giovanitrevisol.sispedido.domain.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
