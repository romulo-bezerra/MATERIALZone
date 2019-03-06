package br.edu.ifpb.tcc.repository;

import br.edu.ifpb.tcc.domain.Material;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MaterialRepository extends ElasticsearchRepository<Material, String> {

}
