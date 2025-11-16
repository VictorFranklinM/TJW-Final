package ifce.edu.br.controle_academico.service;

import ifce.edu.br.controle_academico.model.entity.Aluno;
import ifce.edu.br.controle_academico.model.entity.Disciplina;
import ifce.edu.br.controle_academico.model.entity.Matricula;
import ifce.edu.br.controle_academico.model.enums.SituacaoMatricula;
import ifce.edu.br.controle_academico.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;

    private static final EnumSet<SituacaoMatricula> ATIVAS =
            EnumSet.of(SituacaoMatricula.CURSANDO, SituacaoMatricula.TRANCADO);

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    @Transactional
    public Matricula matricular(Aluno aluno, Disciplina disciplina) {

        boolean jaExiste = matriculaRepository
                .existsByAlunoAndDisciplinaAndSituacaoIn(aluno, disciplina, ATIVAS);

        if (jaExiste) {
            throw new RuntimeException("Aluno já possui matrícula ativa nesta disciplina.");
        }

        Matricula m = new Matricula();
        m.setAluno(aluno);
        m.setDisciplina(disciplina);
        m.setSituacao(SituacaoMatricula.CURSANDO);
        m.setDataMatricula(LocalDate.now());

        return matriculaRepository.save(m);
    }
    public List<Matricula> listarTodas() {
        return matriculaRepository.findAll();
    }

    public Matricula buscar(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada."));
    }

    public List<Matricula> historico(Aluno aluno) {
        return matriculaRepository.findByAlunoOrderByDataMatriculaDesc(aluno);
    }

    public Matricula atualizarSituacao(Long id, SituacaoMatricula situacao) {
        Matricula m = buscar(id);
        m.setSituacao(situacao);
        return matriculaRepository.save(m);
    }

    public Matricula atualizarNota(Long id, Double nota) {
        Matricula m = buscar(id);
        m.setNotaFinal(nota);
        return matriculaRepository.save(m);
    }

    public void excluir(Long id) {
        buscar(id);
        matriculaRepository.deleteById(id);
    }
}