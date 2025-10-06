package com.kelvin.api_contatos.repositories;

import com.kelvin.api_contatos.entities.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatosRepository extends JpaRepository<Contato, String> {
}
