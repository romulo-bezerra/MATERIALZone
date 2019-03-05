package br.edu.ifpb.tcc.repository;

import br.edu.ifpb.tcc.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CategoriaRepository extends ElasticsearchRepository<Categoria, String> {

    Page<Categoria> findFirst10ByPalavrasRelacionadas(String palavrasRelacionadas, Pageable pageable);

}
