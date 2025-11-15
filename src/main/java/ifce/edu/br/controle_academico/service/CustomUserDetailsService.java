package ifce.edu.br.controle_academico.service;

import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(u.getRole().name()));
        return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getSenha(), authorities);
    }
}