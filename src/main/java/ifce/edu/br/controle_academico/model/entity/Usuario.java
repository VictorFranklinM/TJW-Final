package ifce.edu.br.controle_academico.model.entity;

import ifce.edu.br.controle_academico.model.enums.Role;
import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Role role;
}
