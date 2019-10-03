package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.RoleService;
import br.edu.ifpb.tccii.materialzone.domain.Role;
import br.edu.ifpb.tccii.materialzone.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired private RoleRepository roleRepository;

    public RoleServiceImpl() { }

    @Override
    public Role save(Role role) {
        log.debug("Request to save Role : {}", role);
        return roleRepository.save(role);
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Optional<Role> findByRole(String role){
        return roleRepository.findByRole(role);
    }

}
