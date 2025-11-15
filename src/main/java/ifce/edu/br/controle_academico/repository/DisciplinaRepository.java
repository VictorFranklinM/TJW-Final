package ifce.edu.br.controle_academico.repository;

import ifce.edu.br.controle_academico.model.entity.Disciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    Optional<Disciplina> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    Page<Disciplina> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Disciplina> findBySemestre(String semestre, Pageable pageable);
}