package com.perity.taskmanager_api.service;

import com.perity.taskmanager_api.dto.TarefaDTO;
import com.perity.taskmanager_api.model.Pessoa;
import com.perity.taskmanager_api.model.Tarefa;
import com.perity.taskmanager_api.repository.PessoaRepository;
import com.perity.taskmanager_api.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class TarefaServiceTest {

    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    private TarefaDTO dto;
    private Tarefa tarefa;

    @BeforeEach
    void setup() {
        dto = new TarefaDTO();
        dto.setTitulo("Relatório");
        dto.setDescricao("Gerar relatório");
        dto.setPrazo(LocalDate.now().plusDays(3));
        dto.setDepartamento("Financeiro");
        dto.setDuracao(4);

        tarefa = Tarefa.builder()
                .id(1L)
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .prazo(dto.getPrazo())
                .departamento(dto.getDepartamento())
                .duracao(dto.getDuracao())
                .finalizada(false)
                .build();
    }

    @Test
    void deveSalvarTarefaComSucesso() {
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        Tarefa salva = tarefaService.salvar(dto);

        assertThat(salva.getTitulo()).isEqualTo("Relatório");
        assertThat(salva.isFinalizada()).isFalse();
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void deveAlocarPessoaNaTarefa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Maria");
        pessoa.setDepartamento("Financeiro");

        tarefa.setPessoa(null); // tarefa ainda não alocada

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(pessoaRepository.findByDepartamento("Financeiro")).thenReturn(List.of(pessoa));
        when(tarefaRepository.save(any())).thenReturn(tarefa);

        Tarefa resultado = tarefaService.alocarPessoa(1L);

        assertThat(resultado.getPessoa()).isEqualTo(pessoa);
    }

    @Test
    void deveLancarExcecao_QuandoTarefaNaoEncontrada_ParaAlocacao() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> tarefaService.alocarPessoa(1L));
        assertThat(ex.getMessage()).isEqualTo("Tarefa não encontrada");
    }

    @Test
    void deveLancarExcecao_QuandoTarefaJaAlocada() {
        Pessoa alocada = new Pessoa();
        alocada.setNome("João");
        tarefa.setPessoa(alocada);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        Exception ex = assertThrows(RuntimeException.class, () -> tarefaService.alocarPessoa(1L));
        assertThat(ex.getMessage()).isEqualTo("Tarefa já está alocada");
    }

    @Test
    void deveFinalizarTarefaComSucesso() {
        tarefa.setFinalizada(false);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        Tarefa finalizada = tarefaService.finalizarTarefa(1L);

        assertThat(finalizada.isFinalizada()).isTrue();
    }

    @Test
    void deveLancarExcecao_SeTarefaNaoEncontrada_AoFinalizar() {
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> tarefaService.finalizarTarefa(99L));
        assertThat(ex.getMessage()).isEqualTo("Tarefa não encontrada");
    }
}
