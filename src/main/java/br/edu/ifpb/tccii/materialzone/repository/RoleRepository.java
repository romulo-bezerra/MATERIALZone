package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Role;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RoleRepository extends ElasticsearchRepository<Role, String> {

}
