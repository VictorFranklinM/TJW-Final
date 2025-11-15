package ifce.edu.br.controle_academico.model.entity;

import jakarta.persistence.*;

@Entity
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String codigo;
    private String nome;
    private Integer cargaHoraria;
    private String semestre;
}
