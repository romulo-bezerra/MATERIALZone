package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MaterialService {

    Material save(Material material);
    Optional<Material> findOne(String id);
    Page<Material> findAllMaterialsByTitleOrDescription(String text, Pageable pageable);
    Page<Material> findMaterialsByNameCategories(String nameCategory, Pageable pageable);
    Page<Material> findAll(Pageable pageable);
    void delete(String id);
    Page<Material> findByEmailProfessor(String emailProfessor, Pageable pageable);

}
