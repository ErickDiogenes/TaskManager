package com.perity.taskmanager_api.service;

import com.perity.taskmanager_api.dto.TarefaDTO;
import com.perity.taskmanager_api.model.Pessoa;
import com.perity.taskmanager_api.model.Tarefa;
import com.perity.taskmanager_api.repository.PessoaRepository;
import com.perity.taskmanager_api.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final PessoaRepository pessoaRepository;

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository, PessoaRepository pessoaRepository) {
        this.tarefaRepository = tarefaRepository;
        this.pessoaRepository = pessoaRepository;
    }


    public Tarefa salvar(TarefaDTO dto) {
        Tarefa tarefa = Tarefa.builder()
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .prazo(dto.getPrazo())
                .departamento(dto.getDepartamento())
                .duracao(dto.getDuracao())
                .finalizada(false)
                .build();

        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa alocarPessoa(Long tarefaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (tarefa.getPessoa() != null) {
            throw new RuntimeException("Tarefa já alocada");
        }

        List<Pessoa> pessoas = pessoaRepository.findByDepartamento(tarefa.getDepartamento());

        if (pessoas.isEmpty()) {
            throw new RuntimeException("Nenhuma pessoa disponível para este departamento");
        }

        tarefa.setPessoa(pessoas.get(0)); // Aloca a primeira da lista
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa finalizarTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        if (tarefa.isFinalizada()) {
            throw new RuntimeException("Tarefa já está finalizada");
        }

        tarefa.setFinalizada(true);
        return tarefaRepository.save(tarefa);
    }

}