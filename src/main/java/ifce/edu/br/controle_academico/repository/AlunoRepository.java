package ifce.edu.br.controle_academico.repository;

import ifce.edu.br.controle_academico.model.entity.Aluno;
import ifce.edu.br.controle_academico.model.enums.StatusAluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByMatricula(String matricula);
    Page<Aluno> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    List<Aluno> findByStatus(StatusAluno status);
}
