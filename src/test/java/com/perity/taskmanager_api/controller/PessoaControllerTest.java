package com.perity.taskmanager_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perity.taskmanager_api.model.Pessoa;
import com.perity.taskmanager_api.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PessoaController.class)
@Import(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PessoaService pessoaService = Mockito.mock(PessoaService.class);
    private PessoaController pessoaController;

    @BeforeEach
    void setup() {
        pessoaController = new PessoaController(pessoaService);
    }

    @Test
    void deveRetornarListaDePessoas() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("Maria");
        pessoa.setDepartamento("TI");

        when(pessoaService.listarTodas()).thenReturn(List.of(pessoa));

        mockMvc.perform(get("/pessoas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome").value("Maria"));
    }
}
