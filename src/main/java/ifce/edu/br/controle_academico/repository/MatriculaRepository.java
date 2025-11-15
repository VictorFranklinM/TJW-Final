package ifce.edu.br.controle_academico.repository;

import ifce.edu.br.controle_academico.model.entity.Aluno;
import ifce.edu.br.controle_academico.model.entity.Disciplina;
import ifce.edu.br.controle_academico.model.entity.Matricula;
import ifce.edu.br.controle_academico.model.enums.SituacaoMatricula;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByAluno(Aluno aluno);
    List<Matricula> findByDisciplina(Disciplina disciplina);
    List<Matricula> findBySituacao(SituacaoMatricula situacao);
    Optional<Matricula> findByAlunoAndDisciplina(Aluno aluno, Disciplina disciplina);
    boolean existsByAlunoAndDisciplinaAndSituacaoIn(Aluno aluno, Disciplina disciplina, Collection<SituacaoMatricula> situacoes);
    @EntityGraph(attributePaths = {"aluno", "disciplina"})
    Optional<Matricula> findById(Long id);
    List<Matricula> findByAlunoOrderByDataMatriculaDesc(Aluno aluno);
}