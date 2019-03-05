package br.edu.ifpb.tcc.abstration;

import br.edu.ifpb.tcc.domain.Material;
import org.springframework.data.domain.Page;


public interface MaterialService {

    public Material save(Material m);

    public Material findOne(String id);

    public Iterable<Material> findAll();

    public Iterable<Material> findByText(String text);

    public Iterable<Material> getMaterialByRelatedWordsCategoria();

    public void deleteAll();

}
