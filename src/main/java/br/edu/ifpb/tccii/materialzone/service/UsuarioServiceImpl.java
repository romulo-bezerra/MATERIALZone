//package br.edu.ifpb.tccii.materialzone.service;
//
//import br.edu.ifpb.tccii.materialzone.abstration.UsuarioService;
//import br.edu.ifpb.tccii.materialzone.domain.Usuario;
//import br.edu.ifpb.tccii.materialzone.repository.UsuarioRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//public class UsuarioServiceImpl implements UsuarioService {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    @Autowired private UsuarioRepository usuarioRepository;
//
//    public UsuarioServiceImpl() {
//    }
//
//    public Usuario save(Usuario usuario) {
//        log.debug("Request to save Usuario : {}", usuario);
//        return usuarioRepository.save(usuario);
//    }
//
//    @Transactional(readOnly = true)
//    public Page<Usuario> findAll(Pageable pageable) {
//        log.debug("Request to get all Usuarios");
//        return usuarioRepository.findAll(pageable);
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Usuario> findByUsername(String username) {
//        log.debug("Request to get Usuario : {}", username);
//        return usuarioRepository.findByUsername(username);
//    }
//
//    public void delete(String id) {
//        log.debug("Request to delete Usuario : {}", id);
//        usuarioRepository.deleteById(id);
//    }
//
//}
