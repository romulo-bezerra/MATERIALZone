package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Aluno save(Aluno usuario);
    List<Aluno> findAll();
    Optional<Aluno> findOne(String id);
    void delete(String id);

}
