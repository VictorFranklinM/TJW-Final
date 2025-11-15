package ifce.edu.br.controle_academico;

import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.model.enums.Role;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import ifce.edu.br.controle_academico.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("admin");
        usuario.setSenha("123");
        usuario.setRole(Role.ROLE_ADMIN);
    }

    @Test
    void deveCriarUsuario() {
        when(usuarioRepository.existsByLogin("admin")).thenReturn(false);
        when(passwordEncoder.encode("123")).thenReturn("ENCODED");
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario criado = usuarioService.criar(usuario);

        assertEquals("ENCODED", criado.getSenha());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void naoDeveCriarUsuarioComLoginDuplicado() {
        when(usuarioRepository.existsByLogin("admin")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> usuarioService.criar(usuario));
    }

    @Test
    void deveBuscarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario encontrado = usuarioService.buscar(1L);

        assertEquals("admin", encontrado.getLogin());
    }

    @Test
    void deveFalharAoBuscarInexistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.buscar(1L));
    }

    @Test
    void deveListarPorRole() {
        when(usuarioRepository.findByRole(Role.ROLE_ADMIN)).thenReturn(List.of(usuario));

        List<Usuario> lista = usuarioService.listarPorRole(Role.ROLE_ADMIN);

        assertEquals(1, lista.size());
    }

    @Test
    void deveAtualizarUsuario() {
        Usuario novo = new Usuario();
        novo.setLogin("novo_admin");
        novo.setRole(Role.ROLE_ADMIN);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByLogin("novo_admin")).thenReturn(false);
        when(usuarioRepository.save(any())).thenReturn(novo);

        Usuario atualizado = usuarioService.atualizar(1L, novo);

        assertEquals("novo_admin", atualizado.getLogin());
        assertEquals(Role.ROLE_ADMIN, atualizado.getRole());
    }

    @Test
    void deveExcluir() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.excluir(1L);

        verify(usuarioRepository).deleteById(1L);
    }
}