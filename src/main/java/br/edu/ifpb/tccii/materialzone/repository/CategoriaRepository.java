package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Categoria;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CategoriaRepository extends ElasticsearchRepository<Categoria, String> {



}
