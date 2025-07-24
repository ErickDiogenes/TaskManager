package com.perity.taskmanager_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tarefa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDate prazo;
    private String departamento;
    private int duracao; // duração em horas
    private boolean finalizada;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Pessoa pessoa;
}
