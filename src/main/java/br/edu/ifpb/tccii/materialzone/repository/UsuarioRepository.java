package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Usuario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UsuarioRepository extends ElasticsearchRepository<Usuario, String> {

}
