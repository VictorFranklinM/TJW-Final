package ifce.edu.br.controle_academico;

import ifce.edu.br.controle_academico.model.entity.Aluno;
import ifce.edu.br.controle_academico.model.enums.StatusAluno;
import ifce.edu.br.controle_academico.repository.AlunoRepository;
import ifce.edu.br.controle_academico.service.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("João");
        aluno.setMatricula("2023001");
        aluno.setStatus(StatusAluno.ATIVO);
    }

    @Test
    void deveCriarAluno() {
        when(alunoRepository.findByMatricula("2023001")).thenReturn(Optional.empty());
        when(alunoRepository.save(any())).thenReturn(aluno);

        Aluno criado = alunoService.criar(aluno);

        assertEquals(StatusAluno.ATIVO, criado.getStatus());
        verify(alunoRepository).save(aluno);
    }

    @Test
    void naoDeveCriarAlunoComMatriculaDuplicada() {
        when(alunoRepository.findByMatricula("2023001")).thenReturn(Optional.of(aluno));

        assertThrows(RuntimeException.class, () -> alunoService.criar(aluno));
    }

    @Test
    void deveBuscarAluno() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        Aluno encontrado = alunoService.buscar(1L);

        assertEquals("João", encontrado.getNome());
    }

    @Test
    void deveFalharAoBuscarAlunoInexistente() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> alunoService.buscar(1L));
    }

    @Test
    void deveBuscarPorNome() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Aluno> page = new PageImpl<>(List.of(aluno));
        when(alunoRepository.findByNomeContainingIgnoreCase("jo", pageable)).thenReturn(page);

        Page<Aluno> resultado = alunoService.buscarPorNome("jo", pageable);

        assertEquals(1, resultado.getContent().size());
    }

    @Test
    void deveAtualizarAluno() {
        Aluno novo = new Aluno();
        novo.setNome("João Silva");
        novo.setMatricula("2023001");
        novo.setStatus(StatusAluno.INATIVO);

        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any())).thenReturn(novo);

        Aluno atualizado = alunoService.atualizar(1L, novo);

        assertEquals("João Silva", atualizado.getNome());
        assertEquals(StatusAluno.INATIVO, atualizado.getStatus());
    }

    @Test
    void deveAlterarStatus() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(alunoRepository.save(any())).thenReturn(aluno);

        Aluno atualizado = alunoService.alterarStatus(1L, StatusAluno.INATIVO);

        assertEquals(StatusAluno.INATIVO, atualizado.getStatus());
    }

    @Test
    void deveExcluirAluno() {
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

        alunoService.excluir(1L);

        verify(alunoRepository).deleteById(1L);
    }
}