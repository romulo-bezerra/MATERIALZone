//package br.edu.ifpb.tccii.materialzone.web.rest;
//
//import br.edu.ifpb.tccii.materialzone.abstration.UsuarioService;
//import br.edu.ifpb.tccii.materialzone.domain.Usuario;
//import br.edu.ifpb.tccii.materialzone.web.util.HeaderUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/usuario")
//@Api(value = "UsuarioResource Controller", description = "Serviços pertinentes à usuarios")
//public class UsuarioResource {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    private static final String ENTITY_NAME = "Usuario";
//
//    @Autowired private UsuarioService usuarioService;
//
//    public UsuarioResource() { }
//
//    @GetMapping("")
//    @ApiOperation(value = "Recupera todos os usuarios")
//    public ResponseEntity<List<Usuario>> getAllUsuarios(@RequestParam("pag") int pag) {
//        log.debug("REST request to get all Usuario");
//        PageRequest pageRequest = PageRequest.of(pag, 10);
//        Page<Usuario> usuariosPag = usuarioService.findAll(pageRequest);
//        return ResponseEntity.ok().body(usuariosPag.getContent());
//    }
//
//    @GetMapping("/{id}")
//    @ApiOperation(value = "Recupera um usuario dado o username")
//    public ResponseEntity<Usuario> getUsuario(@PathVariable String username) {
//        log.debug("REST request to get Usuario : {}", username);
//        Optional<Usuario> usuario = usuarioService.findByUsername(username);
//        if (usuario.isPresent()) return ResponseEntity.ok().body(usuario.get());
//        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, username, String.format("Usuario id %d inexists", username))).build();
//    }
//
//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Deleta um usuario dado o ID")
//    public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
//        log.debug("REST request to delete Usuario : {}", id);
//        Optional<Usuario> usuario = usuarioService.findByUsername(id);
//        if (usuario.isPresent()) {
//            usuarioService.delete(id);
//            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
//        }
//        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Usuario id %d inexists", id))).build();
//    }
//
//}
