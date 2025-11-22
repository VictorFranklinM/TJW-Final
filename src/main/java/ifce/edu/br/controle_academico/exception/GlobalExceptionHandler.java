package ifce.edu.br.controle_academico.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DisciplinaException.class)
    public String handleDisciplina(DisciplinaException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("erro", ex.getMessage());
        return "redirect:/disciplinas";
    }

    @ExceptionHandler(UsuarioException.class)
    public String handleUsuario(UsuarioException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("erro", ex.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(AlunoException.class)
    public String handleAluno(AlunoException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("erro", ex.getMessage());
        return "redirect:/alunos";
    }

    @ExceptionHandler(MatriculaException.class)
    public String handleMatricula(MatriculaException ex, RedirectAttributes ra) {
        ra.addFlashAttribute("erro", ex.getMessage());
        return "redirect:/matriculas";
    }
}