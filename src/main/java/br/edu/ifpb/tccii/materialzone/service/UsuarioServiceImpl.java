package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.UsuarioService;
import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import br.edu.ifpb.tccii.materialzone.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl() { }

    public Aluno save(Aluno usuario) {
        String plainPassword = usuario.getSenha();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        usuario.setSenha(passwordEncoder.encode(plainPassword));
        log.debug("Request to save Usuario : {}", usuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Page<Aluno> findAll(Pageable pageable) {
        log.debug("Request to get all Usuarios");
        return usuarioRepository.findAll(pageable);
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
