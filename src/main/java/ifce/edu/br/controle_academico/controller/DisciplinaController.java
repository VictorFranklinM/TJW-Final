package ifce.edu.br.controle_academico.controller;

import ifce.edu.br.controle_academico.model.entity.Disciplina;
import ifce.edu.br.controle_academico.service.DisciplinaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("disciplinas", disciplinaService.buscarPorNome("", null).getContent());
        return "disciplinas/lista";
    }

    @GetMapping("/novo")
    @PreAuthorize("hasRole('ADMIN')")
    public String novo(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplinas/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String salvar(@ModelAttribute Disciplina d) {

        if (d.getId() == null)
            disciplinaService.criar(d);
        else
            disciplinaService.atualizar(d.getId(), d);

        return "redirect:/disciplinas";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("disciplina", disciplinaService.buscar(id));
        return "disciplinas/form";
    }

    @GetMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluir(@PathVariable Long id) {
        disciplinaService.excluir(id);
        return "redirect:/disciplinas";
    }
}