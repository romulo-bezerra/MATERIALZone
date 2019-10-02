package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MaterialService {

    Material save(Material material);
    Iterable<Material> findAll();
    Optional<Material> findOne(String id);
    void delete(String id);
    Iterable<Material> findAllMaterialsByTitleOrDescription(String text);
    Iterable<Material> findMaterialsByNameCategories(String nameCategory);
    Page<Material> findAll(Pageable pageable);

}
