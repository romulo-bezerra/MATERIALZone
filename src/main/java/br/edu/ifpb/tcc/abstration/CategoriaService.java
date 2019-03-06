package br.edu.ifpb.tcc.abstration;

import br.edu.ifpb.tcc.domain.Categoria;

public interface CategoriaService {

    public Categoria save(Categoria c);

    public Categoria update(Categoria categoria);

    public Categoria findOne(String id);

    public Iterable<Categoria> findAll();

    public Iterable<Categoria> findByText(String text);

    public void deleteAll();

    public void delete(String id);

}
