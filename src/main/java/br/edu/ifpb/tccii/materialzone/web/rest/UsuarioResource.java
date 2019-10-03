package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.UsuarioService;
import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import br.edu.ifpb.tccii.materialzone.web.errors.BadRequestAlertException;
import br.edu.ifpb.tccii.materialzone.web.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@Api(value = "UsuarioResource Controller", description = "Serviços pertinentes à usuários")
public class UsuarioResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ENTITY_NAME = "Usuario";

    private UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("")
    @ApiOperation(value = "Cria um novo usuário")
    public ResponseEntity<Aluno> createUsuario(@Valid @RequestBody Aluno usuario) throws URISyntaxException {
        log.debug("REST request to save Usuario : {}", usuario);
        Aluno result = usuarioService.save(usuario);
        return ResponseEntity.created(new URI("/api/usuarios/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    @PutMapping("")
    @ApiOperation(value = "Atualiza os daddos de usuário")
    @PreAuthorize("hasAnyRole('ALUNO', 'PROFESSOR')")
    public ResponseEntity<Aluno> updateUsuario(@Valid @RequestBody Aluno usuario) throws URISyntaxException {
        log.debug("REST request to update Usuario : {}", usuario);
        if (usuario.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        Aluno result = usuarioService.save(usuario);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usuario.getId())).body(result);
    }

    @GetMapping("")
    @ApiOperation(value = "Recupera todos os usuários")
    public ResponseEntity<List<Aluno>> getAllUsuarios() {
        log.debug("REST request to get all Usuarios");
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Recupera um usuário dado o ID")
    public ResponseEntity<Aluno> getUsuario(@PathVariable String id) {
        log.debug("REST request to get Usuario : {}", id);
        Optional<Aluno> usuario = usuarioService.findOne(id);
        if (usuario.isPresent()) return ResponseEntity.ok().body(usuario.get());
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Usuario id %d inexists", id))).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um usuário dado o ID")
    @PreAuthorize("hasAnyRole('ALUNO', 'PROFESSOR')")
    public ResponseEntity<Void> deleteUsuario(@PathVariable String id) {
        log.debug("REST request to delete Usuario : {}", id);
        Optional<Aluno> usuario = usuarioService.findOne(id);
        if (usuario.isPresent()) {
            usuarioService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
        }
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Usuario id %d inexists", id))).build();
    }

}
