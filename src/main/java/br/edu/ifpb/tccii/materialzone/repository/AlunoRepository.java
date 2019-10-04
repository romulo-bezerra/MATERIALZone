package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AlunoRepository extends ElasticsearchRepository<Aluno, String> { }
