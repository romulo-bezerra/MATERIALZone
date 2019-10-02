package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.UsuarioService;
import br.edu.ifpb.tccii.materialzone.domain.Usuario;
import br.edu.ifpb.tccii.materialzone.web.errors.BadRequestAlertException;
import br.edu.ifpb.tccii.materialzone.web.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Api(value = "UsuarioResource Controller", description = "Serviços pertinentes à usuários")
public class UsuarioResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ENTITY_NAME = "Usuario";

    private UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuarios")
    @ApiOperation(value = "Cria um novo usuário")
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) throws URISyntaxException {
        log.debug("REST request to save Usuario : {}", usuario);
        Usuario result = usuarioService.save(usuario);
        return ResponseEntity.created(new URI("/api/usuarios/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    @PutMapping("/usuarios")
    @ApiOperation(value = "Atualiza os daddos de usuário")
    public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario) throws URISyntaxException {
        log.debug("REST request to update Usuario : {}", usuario);
        if (usuario.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        Usuario result = usuarioService.save(usuario);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usuario.getId())).body(result);
    }

    @GetMapping("/usuarios")
    @ApiOperation(value = "Recupera todos os usuários")
    public ResponseEntity<Iterable<Usuario>> getAllUsuarios() {
        log.debug("REST request to get all Usuarios");
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @GetMapping("/usuarios/{id}")
    @ApiOperation(value = "Recupera um usuário dado o ID")
    public ResponseEntity<Usuario> getUsuario(@PathVariable String id) {
        log.debug("REST request to get Usuario : {}", id);
        Optional<Usuario> usuario = usuarioService.findOne(id);
        if (usuario.isPresent()) return ResponseEntity.ok().body(usuario.get());
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Usuario id %d inexists", id))).build();
    }

    @DeleteMapping("/usuarios/{id}")
    @ApiOperation(value = "Deleta um usuário dado o ID")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
        log.debug("REST request to delete Usuario : {}", id);
        Optional<Usuario> usuario = usuarioService.findOne(id);
        if (usuario.isPresent()) {
            usuarioService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
        }
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Usuario id %d inexists", id))).build();
    }

}
