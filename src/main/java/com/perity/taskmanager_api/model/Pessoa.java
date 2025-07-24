package com.perity.taskmanager_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pessoa")
@Data // Gera getter, setter, equals, hashCode e toString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String departamento;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    @ToString.Exclude // Evita loop infinito no toString()
    @EqualsAndHashCode.Exclude // Evita problemas em comparações
    private List<Tarefa> tarefas = new ArrayList<>();
}
