package com.perity.taskmanager_api.controller;

import com.perity.taskmanager_api.model.Pessoa;
import com.perity.taskmanager_api.service.PessoaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@RequestBody Pessoa pessoa) {
        return ResponseEntity.ok(pessoaService.salvar(pessoa));
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarPessoas() {
        return ResponseEntity.ok(pessoaService.listarTodas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
