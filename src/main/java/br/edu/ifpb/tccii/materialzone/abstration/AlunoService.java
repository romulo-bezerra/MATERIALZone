package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AlunoService {

    Aluno save(Aluno aluno);
    Page<Aluno> findAll(Pageable pageable);
    Optional<Aluno> findOne(String id);
    void delete(String id);
    Optional<Aluno> findByEmail(String email);

}
