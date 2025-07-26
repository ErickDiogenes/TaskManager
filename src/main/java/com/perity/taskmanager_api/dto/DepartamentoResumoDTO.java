package com.perity.taskmanager_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartamentoResumoDTO {
    private String departamento;
    private long totalPessoas;
    private long totalTarefas;
}
