package com.perity.taskmanager_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TarefaDTO {
    private String titulo;
    private String descricao;
    private LocalDate prazo;
    private String departamento;
    private int duracao; // em horas
}