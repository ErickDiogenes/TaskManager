package com.perity.taskmanager_api.repository;

import com.perity.taskmanager_api.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByPessoaIsNullOrderByPrazoAsc();
}