package br.edu.ifpb.tccii.materialzone.abstration;

import br.edu.ifpb.tccii.materialzone.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService {

    Role save(Role role);
    Page<Role> findAll(Pageable pageable);
    Optional<Role> findByRole(String role);

}
