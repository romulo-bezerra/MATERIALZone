package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Professor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProfessorRepository extends ElasticsearchRepository<Professor, String> { }
