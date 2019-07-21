package br.edu.ifpb.tcc.abstration;

import br.edu.ifpb.tcc.domain.Usuario;

import java.util.Set;

public interface UsuarioService {

    public Usuario save(Usuario m);

//    public Material update(Material material);

//    public Iterable<Material> getMateriaisByCategoria(String idCategoria);

//    public Set<String> getCategoriasByMaterial(Material material);

//    public Material findOne(String id);

    public Iterable<Usuario> findAll();

//    public Iterable<Material> findByText(String text);

//    public void delete(String id);

//    public void deleteAll();

}
