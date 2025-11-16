package ifce.edu.br.controle_academico.service;

import ifce.edu.br.controle_academico.model.entity.Aluno;
import ifce.edu.br.controle_academico.model.enums.StatusAluno;
import ifce.edu.br.controle_academico.repository.AlunoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno criar(Aluno aluno) {

        alunoRepository.findByMatricula(aluno.getMatricula())
                .ifPresent(a -> {
                    throw new RuntimeException("Já existe um aluno com essa matrícula.");
                });

        aluno.setStatus(StatusAluno.ATIVO);

        return alunoRepository.save(aluno);
    }

    public Aluno buscar(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado."));
    }

    public Page<Aluno> buscarPorNome(String nome, Pageable pageable) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Aluno atualizar(Long id, Aluno novo) {
        Aluno atual = buscar(id);
        if (!atual.getMatricula().equals(novo.getMatricula())) {
            alunoRepository.findByMatricula(novo.getMatricula())
                    .ifPresent(a -> {
                        throw new RuntimeException("Já existe um aluno com essa matrícula.");
                    });
        }

        atual.setNome(novo.getNome());
        atual.setMatricula(novo.getMatricula());
        atual.setEmail(novo.getEmail());
        atual.setDataNascimento(novo.getDataNascimento());
        atual.setStatus(novo.getStatus());
        return alunoRepository.save(atual);
    }

    public Aluno alterarStatus(Long id, StatusAluno status) {
        Aluno aluno = buscar(id);
        aluno.setStatus(status);
        alunoRepository.save(aluno);
        return aluno;
    }

    public void excluir(Long id) {
        buscar(id);
        alunoRepository.deleteById(id);
    }
}