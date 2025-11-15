package ifce.edu.br.controle_academico.model.entity;

import ifce.edu.br.controle_academico.model.enums.StatusAluno;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true, nullable = false)
    private String matricula;
    private String email;
    private LocalDate dataNascimento;
    @Enumerated(EnumType.STRING)
    private StatusAluno status;
}
