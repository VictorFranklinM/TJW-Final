package ifce.edu.br.controle_academico;

import ifce.edu.br.controle_academico.model.entity.Disciplina;
import ifce.edu.br.controle_academico.repository.DisciplinaRepository;
import ifce.edu.br.controle_academico.service.DisciplinaService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    private Disciplina disciplina;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        disciplina = new Disciplina();
        disciplina.setId(1L);
        disciplina.setCodigo("IF0032");
        disciplina.setNome("POO");
        disciplina.setSemestre("2024.1");
    }

    @Test
    void deveCriarDisciplina() {
        when(disciplinaRepository.existsByCodigo("IF0032")).thenReturn(false);
        when(disciplinaRepository.save(any())).thenReturn(disciplina);

        Disciplina criada = disciplinaService.criar(disciplina);

        assertEquals("POO", criada.getNome());
    }

    @Test
    void naoDeveCriarDisciplinaComCodigoDuplicado() {
        when(disciplinaRepository.existsByCodigo("IF0032")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> disciplinaService.criar(disciplina));
    }

    @Test
    void deveBuscarDisciplina() {
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        Disciplina encontrada = disciplinaService.buscar(1L);

        assertEquals("POO", encontrada.getNome());
    }

    @Test
    void deveFalharAoBuscarDisciplinaInexistente() {
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> disciplinaService.buscar(1L));
    }

    @Test
    void deveBuscarPorNome() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Disciplina> page = new PageImpl<>(List.of(disciplina));
        when(disciplinaRepository.findByNomeContainingIgnoreCase("poo", pageable))
                .thenReturn(page);

        Page<Disciplina> resultado = disciplinaService.buscarPorNome("poo", pageable);

        assertEquals(1, resultado.getContent().size());
    }

    @Test
    void deveAtualizarDisciplina() {
        Disciplina novo = new Disciplina();
        novo.setCodigo("IF0033");
        novo.setNome("Estruturas");
        novo.setSemestre("2024.2");

        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(disciplinaRepository.existsByCodigo("IF0033")).thenReturn(false);
        when(disciplinaRepository.save(any())).thenReturn(novo);

        Disciplina atualizada = disciplinaService.atualizar(1L, novo);

        assertEquals("Estruturas", atualizada.getNome());
    }

    @Test
    void deveExcluirDisciplina() {
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        disciplinaService.excluir(1L);

        verify(disciplinaRepository).deleteById(1L);
    }
}