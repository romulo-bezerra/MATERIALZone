package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Material;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MaterialRepository extends ElasticsearchRepository<Material, String> {

//    Material existsByIdAndAndCategoriasIds(String materialId, String categoriaId);
//    Iterable<Material> findMaterialsByCategoriasIds(String categoriaId);

}
