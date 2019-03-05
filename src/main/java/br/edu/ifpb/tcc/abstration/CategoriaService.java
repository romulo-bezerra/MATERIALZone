package br.edu.ifpb.tcc.abstration;

import br.edu.ifpb.tcc.domain.Categoria;

import java.util.List;

public interface CategoriaService {

    public Categoria save(Categoria c);

    public Categoria findOne(String id);

    public List<Categoria> findAll();

    public void deleteAll();

    public Iterable<Categoria> findCategoriasRelated(String text);

    public Iterable<Categoria> getCategoriaByPalavrasRelacionadas(String palavrasRelacionadas);

}
