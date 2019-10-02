package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MaterialRepository extends ElasticsearchRepository<Material, String> {

    @Override
    Page<Material> findAll(Pageable pageable);

}
