package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProfessorService {

    Professor save(Professor professor);
    Page<Professor> findAll(Pageable pageable);
    Optional<Professor> findOne(String id);
    void delete(String id);
    Professor update(Professor professor);

}
