package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CategoriaRepository extends ElasticsearchRepository<Categoria, String> {

    Page<Categoria> findAllByNome(String nome, Pageable pageable);

}
