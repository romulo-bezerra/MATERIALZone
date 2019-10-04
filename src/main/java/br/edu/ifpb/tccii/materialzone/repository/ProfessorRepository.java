package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Professor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ProfessorRepository extends ElasticsearchRepository<Professor, String> {

    Optional<Professor> findByEmail(String email);

}
