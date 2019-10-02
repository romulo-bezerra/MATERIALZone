package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UsuarioService {

    Usuario save(Usuario usuario);
    Page<Usuario> findAll(Pageable pageable);
    Optional<Usuario> findOne(String id);
    void delete(String id);

}
