package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Usuario save(Usuario usuario);

    Iterable<Usuario> findAll();

    Optional<Usuario> findOne(String id);

    void delete(String id);

}
