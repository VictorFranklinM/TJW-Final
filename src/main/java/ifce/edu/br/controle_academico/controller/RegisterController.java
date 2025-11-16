package ifce.edu.br.controle_academico.controller;

import ifce.edu.br.controle_academico.model.entity.Usuario;
import ifce.edu.br.controle_academico.model.enums.Role;
import ifce.edu.br.controle_academico.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registrar(@ModelAttribute Usuario usuario, Model model) {

        if (usuarioRepository.existsByLogin(usuario.getLogin())) {
            model.addAttribute("erro", "Login já existe!");
            return "register";
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRole(Role.ROLE_ADMIN);

        usuarioRepository.save(usuario);

        return "redirect:/login?registered";
    }
}