package br.edu.ifpb.tcc.repository;

import br.edu.ifpb.tcc.domain.Usuario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UsuarioRepository extends ElasticsearchRepository<Usuario, String> {

}
