package ifce.edu.br.controle_academico.service;

import ifce.edu.br.controle_academico.exception.DisciplinaException;
import ifce.edu.br.controle_academico.model.entity.Disciplina;
import ifce.edu.br.controle_academico.repository.DisciplinaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina criar(Disciplina d) {

        if (disciplinaRepository.existsByCodigo(d.getCodigo())) {
            throw new DisciplinaException("Já existe uma disciplina com esse código.");
        }

        return disciplinaRepository.save(d);
    }

    public Disciplina buscar(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
    }

    public Page<Disciplina> buscarPorNome(String nome, Pageable pageable) {
        return disciplinaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Disciplina atualizar(Long id, Disciplina novo) {
        Disciplina atual = buscar(id);

        if (!atual.getCodigo().equals(novo.getCodigo())
                && disciplinaRepository.existsByCodigo(novo.getCodigo())) {
            throw new DisciplinaException("Código já está em uso.");
        }

        atual.setNome(novo.getNome());
        atual.setCodigo(novo.getCodigo());
        atual.setSemestre(novo.getSemestre());
        atual.setCargaHoraria(novo.getCargaHoraria());

        return disciplinaRepository.save(atual);
    }

    public void excluir(Long id) {
        buscar(id);
        disciplinaRepository.deleteById(id);
    }
}