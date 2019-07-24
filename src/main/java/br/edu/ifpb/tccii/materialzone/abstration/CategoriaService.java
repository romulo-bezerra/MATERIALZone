package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Categoria;

import java.util.Optional;

public interface CategoriaService {

    Categoria save(Categoria c);
    Iterable<Categoria> findAll();
    Optional<Categoria> findOne(String id);
    void delete(String id);

}
