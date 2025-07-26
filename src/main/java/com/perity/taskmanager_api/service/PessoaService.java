package com.perity.taskmanager_api.service;

import com.perity.taskmanager_api.dto.DepartamentoResumoDTO;
import com.perity.taskmanager_api.dto.GastoPessoaDTO;
import com.perity.taskmanager_api.dto.PessoaResumoDTO;
import com.perity.taskmanager_api.model.Pessoa;
import com.perity.taskmanager_api.model.Tarefa;
import com.perity.taskmanager_api.repository.PessoaRepository;
import com.perity.taskmanager_api.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private TarefaRepository tarefaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> buscarPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }

    public List<PessoaResumoDTO> listarResumo() {
        return pessoaRepository.findAll().stream()
                .map(p -> {
                    int horas = p.getTarefas().stream()
                            .filter(Tarefa::isFinalizada)
                            .mapToInt(Tarefa::getDuracao)
                            .sum();
                    return new PessoaResumoDTO(p.getId(), p.getNome(), p.getDepartamento(), horas);
                })
                .collect(Collectors.toList());
    }

    public List<GastoPessoaDTO> buscarGastos(String nome, LocalDate inicio, LocalDate fim) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(p -> {
                    // Tarefas finalizadas no período
                    var tarefasFiltradas = p.getTarefas().stream()
                            .filter(t -> t.isFinalizada() &&
                                    !t.getPrazo().isBefore(inicio) &&
                                    !t.getPrazo().isAfter(fim))
                            .toList();

                    double media = tarefasFiltradas.isEmpty()
                            ? 0
                            : tarefasFiltradas.stream().mapToInt(t -> t.getDuracao()).average().orElse(0);

                    return new GastoPessoaDTO(p.getNome(), p.getDepartamento(), media);
                })
                .collect(Collectors.toList());
    }

    public List<DepartamentoResumoDTO> listarDepartamentos() {
        // Busca todas as pessoas e tarefas
        var pessoas = pessoaRepository.findAll();
        var tarefas = tarefaRepository.findAll();

        // Agrupa por departamento
        Set<String> departamentos = Stream.concat(
                pessoas.stream().map(Pessoa::getDepartamento),
                tarefas.stream().map(Tarefa::getDepartamento)
        ).collect(Collectors.toSet());

        return departamentos.stream()
                .map(dep -> {
                    long totalPessoas = pessoas.stream()
                            .filter(p -> p.getDepartamento().equals(dep))
                            .count();

                    long totalTarefas = tarefas.stream()
                            .filter(t -> t.getDepartamento().equals(dep))
                            .count();

                    return new DepartamentoResumoDTO(dep, totalPessoas, totalTarefas);
                })
                .collect(Collectors.toList());
    }




}
