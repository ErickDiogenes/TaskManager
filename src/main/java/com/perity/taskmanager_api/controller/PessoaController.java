package com.perity.taskmanager_api.controller;

import com.perity.taskmanager_api.dto.DepartamentoResumoDTO;
import com.perity.taskmanager_api.dto.GastoPessoaDTO;
import com.perity.taskmanager_api.dto.PessoaResumoDTO;
import com.perity.taskmanager_api.model.Pessoa;
import com.perity.taskmanager_api.service.PessoaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/resumo")
    public ResponseEntity<List<PessoaResumoDTO>> listarResumo() {
        return ResponseEntity.ok(pessoaService.listarResumo());
    }

    @GetMapping("/gastos")
    public ResponseEntity<List<GastoPessoaDTO>> buscarGastos(
            @RequestParam String nome,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        return ResponseEntity.ok(pessoaService.buscarGastos(nome, inicio, fim));
    }

    @GetMapping("/departamentos")
    public ResponseEntity<List<DepartamentoResumoDTO>> listarDepartamentos() {
        return ResponseEntity.ok(pessoaService.listarDepartamentos());
    }


}
