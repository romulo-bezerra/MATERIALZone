package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Material;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MaterialService {

    Material save(Material material) throws GitAPIException;
    Optional<Material> findOne(String id);
    Page<Material> findAllMaterialsByTitleOrDescription(String text, Pageable pageable);
    List<Material> findMaterialsByNameCategories(String nameCategory, Pageable pageable);
    Page<Material> findAll(Pageable pageable);
    void delete(String id);
    Page<Material> findByEmailProfessor(String emailProfessor, Pageable pageable);
    Material update(Material material) throws GitAPIException;

}
