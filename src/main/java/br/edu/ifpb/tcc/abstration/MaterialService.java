package br.edu.ifpb.tcc.abstration;

import br.edu.ifpb.tcc.domain.Categoria;
import br.edu.ifpb.tcc.domain.Material;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;


public interface MaterialService {

    public Material save(Material m);

    public Material findOne(String id);

    public Iterable<Material> findAll();

    public Iterable<Material> findByText(String text);

    public Iterable<Material> getMaterialByRelatedWordsCategoria();

    public Set<String> getCategoriasByMaterial(Material material);

    public void deleteAll();

}
