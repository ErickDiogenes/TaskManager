package com.perity.taskmanager_api.service;

import com.perity.taskmanager_api.model.Pessoa;
import com.perity.taskmanager_api.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

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
}
