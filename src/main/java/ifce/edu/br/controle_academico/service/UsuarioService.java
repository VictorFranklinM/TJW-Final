package ifce.edu.br.controle_academico.service;

import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.model.enums.Role;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario criar(Usuario usuario) {

        if (usuarioRepository.existsByLogin(usuario.getLogin())) {
            throw new RuntimeException("Login já está em uso.");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public Usuario buscar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public List<Usuario> listarPorRole(Role role) {
        return usuarioRepository.findByRole(role);
    }

    public Usuario atualizar(Long id, Usuario novo) {
        Usuario atual = buscar(id);

        if (!atual.getLogin().equals(novo.getLogin())
                && usuarioRepository.existsByLogin(novo.getLogin())) {
            throw new RuntimeException("Login já está sendo usado.");
        }

        atual.setLogin(novo.getLogin());
        atual.setRole(novo.getRole());

        return usuarioRepository.save(atual);
    }

    public void excluir(Long id) {
        buscar(id);
        usuarioRepository.deleteById(id);
    }
}