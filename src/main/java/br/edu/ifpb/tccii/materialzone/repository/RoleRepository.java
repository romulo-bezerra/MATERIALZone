package br.edu.ifpb.tccii.materialzone.repository;

import br.edu.ifpb.tccii.materialzone.domain.Role;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface RoleRepository extends ElasticsearchRepository<Role, String> {

    Optional<Role> findByRole(String role);
}
