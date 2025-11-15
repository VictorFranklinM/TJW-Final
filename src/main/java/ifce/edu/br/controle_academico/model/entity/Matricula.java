package ifce.edu.br.controle_academico.model.entity;

import ifce.edu.br.controle_academico.model.enums.SituacaoMatricula;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Aluno aluno;
    @ManyToOne
    private Disciplina disciplina;
    private LocalDate dataMatricula;
    @Enumerated(EnumType.STRING)
    private SituacaoMatricula situacao;
    private Double notaFinal;
}
