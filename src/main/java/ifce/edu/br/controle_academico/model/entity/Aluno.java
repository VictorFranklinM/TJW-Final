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

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public StatusAluno getStatus() { return status; }
    public void setStatus(StatusAluno status) { this.status = status; }
}
