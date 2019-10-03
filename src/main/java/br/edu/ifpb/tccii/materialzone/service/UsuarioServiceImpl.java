package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.RoleService;
import br.edu.ifpb.tccii.materialzone.abstration.UsuarioService;
import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import br.edu.ifpb.tccii.materialzone.domain.Role;
import br.edu.ifpb.tccii.materialzone.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private RoleService roleService;

    public UsuarioServiceImpl() { }

    public Aluno save(Aluno usuario) {
        String plainPassword = usuario.getSenha();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setSenha(passwordEncoder.encode(plainPassword));

        Optional<Role> roleOptional = roleService.findByRole("ROLE_PROFESSOR");
        List<Role> roles = new ArrayList<>();
        roles.add(roleOptional.get());
        usuario.setRoles(roles);
        log.debug("Request to save Usuario : {}", usuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Aluno> findAll() {
        log.debug("Request to get all Usuarios");
        return toList(usuarioRepository.findAll());
    }

    private static <Aluno> List<Aluno> toList(final Iterable<Aluno> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Aluno> findOne(String id) {
        log.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findById(id);
    }

    public void delete(String id) {
        log.debug("Request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
    }

}
