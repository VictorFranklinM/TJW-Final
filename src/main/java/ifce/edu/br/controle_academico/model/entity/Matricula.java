package ifce.edu.br.controle_academico.model.entity;

import ifce.edu.br.controle_academico.model.enums.SituacaoMatricula;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"aluno_id", "disciplina_id"})
)
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

    public Long getId() { return id; }
    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }
    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }
    public LocalDate getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }
    public SituacaoMatricula getSituacao() { return situacao; }
    public void setSituacao(SituacaoMatricula situacao) { this.situacao = situacao; }
    public Double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(Double notaFinal) { this.notaFinal = notaFinal; }
}
