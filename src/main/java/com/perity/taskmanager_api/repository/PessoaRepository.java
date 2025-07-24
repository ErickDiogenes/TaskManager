package com.perity.taskmanager_api.repository;

import com.perity.taskmanager_api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);
    List<Pessoa> findByDepartamento(String departamento);
}