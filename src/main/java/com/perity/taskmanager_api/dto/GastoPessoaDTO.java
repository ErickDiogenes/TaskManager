package com.perity.taskmanager_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GastoPessoaDTO {
    private String nome;
    private String departamento;
    private double mediaHoras;
}

