package ifce.edu.br.controle_academico;

import ifce.edu.br.controle_academico.model.entity.*;
import ifce.edu.br.controle_academico.model.enums.SituacaoMatricula;
import ifce.edu.br.controle_academico.repository.MatriculaRepository;
import ifce.edu.br.controle_academico.service.MatriculaService;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @InjectMocks
    private MatriculaService matriculaService;

    private Aluno aluno;
    private Disciplina disciplina;
    private Matricula matricula;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        aluno = new Aluno();
        aluno.setId(1L);

        disciplina = new Disciplina();
        disciplina.setId(1L);

        matricula = new Matricula();
        matricula.setId(1L);
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
    }

    @Test
    void deveCriarMatricula() {
        Aluno aluno = new Aluno();
        Disciplina disciplina = new Disciplina();

        when(matriculaRepository.existsByAlunoAndDisciplinaAndSituacaoIn(
                eq(aluno), eq(disciplina), anyCollection())
        ).thenReturn(false);

        when(matriculaRepository.save(any(Matricula.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Matricula m = matriculaService.matricular(aluno, disciplina);

        assertEquals(SituacaoMatricula.CURSANDO, m.getSituacao());
        assertEquals(aluno, m.getAluno());
        assertEquals(disciplina, m.getDisciplina());
    }

    @Test
    void naoDeveCriarMatriculaSeJaExisteAtiva() {
        when(matriculaRepository.existsByAlunoAndDisciplinaAndSituacaoIn(any(), any(), any()))
                .thenReturn(true);

        assertThrows(RuntimeException.class,
                () -> matriculaService.matricular(aluno, disciplina));
    }

    @Test
    void deveBuscarMatricula() {
        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(matricula));

        Matricula encontrada = matriculaService.buscar(1L);

        assertEquals(1L, encontrada.getId());
    }

    @Test
    void deveFalharAoBuscarInexistente() {
        when(matriculaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> matriculaService.buscar(1L));
    }

    @Test
    void deveRetornarHistorico() {
        when(matriculaRepository.findByAlunoOrderByDataMatriculaDesc(aluno))
                .thenReturn(List.of(matricula));

        List<Matricula> lista = matriculaService.historico(aluno);

        assertEquals(1, lista.size());
    }

    @Test
    void deveAtualizarSituacao() {
        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(matricula));
        when(matriculaRepository.save(any())).thenReturn(matricula);

        Matricula atualizada = matriculaService.atualizarSituacao(1L, SituacaoMatricula.APROVADO);

        assertEquals(SituacaoMatricula.APROVADO, atualizada.getSituacao());
    }

    @Test
    void deveExcluir() {
        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(matricula));

        matriculaService.excluir(1L);

        verify(matriculaRepository).deleteById(1L);
    }
}