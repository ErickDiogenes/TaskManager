package com.perity.taskmanager_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perity.taskmanager_api.dto.TarefaDTO;
import com.perity.taskmanager_api.model.Pessoa;
import com.perity.taskmanager_api.model.Tarefa;
import com.perity.taskmanager_api.service.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TarefaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Tarefa tarefa;
    private TarefaDTO dto;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(tarefaController).build();

        dto = new TarefaDTO();
        dto.setTitulo("Relatório");
        dto.setDescricao("Gerar relatório");
        dto.setDepartamento("TI");
        dto.setDuracao(5);
        dto.setPrazo(LocalDate.now().plusDays(2));

        tarefa = Tarefa.builder()
                .id(1L)
                .titulo(dto.getTitulo())
                .descricao(dto.getDescricao())
                .departamento(dto.getDepartamento())
                .duracao(dto.getDuracao())
                .prazo(dto.getPrazo())
                .finalizada(false)
                .build();
    }

    @Test
    void deveCriarTarefaComSucesso() throws Exception {
        when(tarefaService.salvar(dto)).thenReturn(tarefa);

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Relatório")))
                .andExpect(jsonPath("$.finalizada", is(false)));
    }

    @Test
    void deveAlocarPessoaNaTarefa() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Maria");
        pessoa.setDepartamento("TI");

        tarefa.setPessoa(pessoa);
        when(tarefaService.alocarPessoa(1L)).thenReturn(tarefa);

        mockMvc.perform(put("/tarefas/alocar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pessoa.nome", is("Maria")));
    }

    @Test
    void deveFinalizarTarefa() throws Exception {
        tarefa.setFinalizada(true);
        when(tarefaService.finalizarTarefa(1L)).thenReturn(tarefa);

        mockMvc.perform(put("/tarefas/finalizar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalizada", is(true)));
    }
}
