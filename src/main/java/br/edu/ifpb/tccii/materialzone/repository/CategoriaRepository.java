package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Categoria;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface CategoriaRepository extends ElasticsearchRepository<Categoria, String> {

    Optional<Categoria> findByNome(String nome);

}
