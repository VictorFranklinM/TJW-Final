package ifce.edu.br.controle_academico.controller;

import ifce.edu.br.controle_academico.model.entity.Aluno;
import ifce.edu.br.controle_academico.service.AlunoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alunos")
public class AlunoController {
    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alunos", alunoService.buscarPorNome("", null).getContent());
        return "alunos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "alunos/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute("aluno") Aluno aluno) {

        if (aluno.getId() == null) {
            alunoService.criar(aluno);
        } else {
            alunoService.atualizar(aluno.getId(), aluno);
        }

        return "redirect:/alunos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("aluno", alunoService.buscar(id));
        return "alunos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        alunoService.excluir(id);
        return "redirect:/alunos";
    }
}
