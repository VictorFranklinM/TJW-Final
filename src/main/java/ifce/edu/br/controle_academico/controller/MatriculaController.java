package ifce.edu.br.controle_academico.controller;

import ifce.edu.br.controle_academico.model.entity.*;
import ifce.edu.br.controle_academico.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;

    public MatriculaController(MatriculaService matriculaService,
                               AlunoService alunoService,
                               DisciplinaService disciplinaService) {
        this.matriculaService = matriculaService;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("matriculas", matriculaService.historico(null));
        return "matriculas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("alunos", alunoService.buscarPorNome("", null).getContent());
        model.addAttribute("disciplinas", disciplinaService.buscarPorNome("", null).getContent());
        return "matriculas/form";
    }

    @PostMapping
    public String salvar(@RequestParam Long alunoId,
                         @RequestParam Long disciplinaId) {

        Aluno a = alunoService.buscar(alunoId);
        Disciplina d = disciplinaService.buscar(disciplinaId);

        matriculaService.matricular(a, d);

        return "redirect:/matriculas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        matriculaService.excluir(id);
        return "redirect:/matriculas";
    }
}