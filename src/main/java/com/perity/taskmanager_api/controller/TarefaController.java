package com.perity.taskmanager_api.controller;

import com.perity.taskmanager_api.dto.TarefaDTO;
import com.perity.taskmanager_api.model.Tarefa;
import com.perity.taskmanager_api.service.TarefaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody TarefaDTO dto) {
        return ResponseEntity.ok(tarefaService.salvar(dto));
    }

    @PutMapping("/alocar/{id}")
    public ResponseEntity<Tarefa> alocarPessoa(@PathVariable Long id) {
        Tarefa tarefaAlocada = tarefaService.alocarPessoa(id);
        return ResponseEntity.ok(tarefaAlocada);
    }


}

