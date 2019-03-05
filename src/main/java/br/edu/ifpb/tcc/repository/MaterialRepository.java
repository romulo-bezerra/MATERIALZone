package br.edu.ifpb.tcc.repository;

import br.edu.ifpb.tcc.domain.Material;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MaterialRepository extends ElasticsearchRepository<Material, String> {

    List<Material> findByLinhasArquivoRepo (String s);

}
