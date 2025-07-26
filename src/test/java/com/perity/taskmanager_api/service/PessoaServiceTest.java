package com.perity.taskmanager_api.service;

import com.perity.taskmanager_api.model.Pessoa;
import com.perity.taskmanager_api.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    private Pessoa mockPessoa;

    @BeforeEach
    void setUp() {
        mockPessoa = new Pessoa();
        mockPessoa.setId(1L);
        mockPessoa.setNome("Maria");
        mockPessoa.setDepartamento("Financeiro");
    }

    @Test
    void deveSalvarPessoaComSucesso() {
        when(pessoaRepository.save(mockPessoa)).thenReturn(mockPessoa);

        Pessoa salva = pessoaService.salvar(mockPessoa);

        assertThat(salva.getNome()).isEqualTo("Maria");
        assertThat(salva.getDepartamento()).isEqualTo("Financeiro");
        verify(pessoaRepository, times(1)).save(mockPessoa);
    }

    @Test
    void deveRetornarTodasAsPessoas() {
        when(pessoaRepository.findAll()).thenReturn(List.of(mockPessoa));

        List<Pessoa> pessoas = pessoaService.listarTodas();

        assertThat(pessoas).hasSize(1);
        assertThat(pessoas.get(0).getNome()).isEqualTo("Maria");
    }

    @Test
    void deveBuscarPessoaPorId() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(mockPessoa));

        Optional<Pessoa> result = pessoaService.buscarPorId(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getNome()).isEqualTo("Maria");
    }
}
