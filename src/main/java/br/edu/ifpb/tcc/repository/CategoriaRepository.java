package br.edu.ifpb.tcc.repository;

import br.edu.ifpb.tcc.domain.Categoria;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CategoriaRepository extends ElasticsearchRepository<Categoria, String> {

}
