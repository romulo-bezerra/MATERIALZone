package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.CategoriaService;
import br.edu.ifpb.tccii.materialzone.domain.Categoria;
import br.edu.ifpb.tccii.materialzone.web.errors.BadRequestAlertException;
import br.edu.ifpb.tccii.materialzone.web.util.HeaderUtil;
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
public class CategoriaResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ENTITY_NAME = "Categoria";

    private CategoriaService categoriaService;

    public CategoriaResource(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> createCategoria(@Valid @RequestBody Categoria categoria) throws URISyntaxException {
        log.debug("REST request to save Categoria : {}", categoria);
        Categoria result = categoriaService.save(categoria);
        return ResponseEntity.created(new URI("/api/categorias/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    @PutMapping("/categorias")
    public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria) throws URISyntaxException {
        log.debug("REST request to update Categoria : {}", categoria);
        if (categoria.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        Categoria result = categoriaService.save(categoria);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoria.getId())).body(result);
    }

    @GetMapping("/categorias")
    public ResponseEntity<Iterable<Categoria>> getAllCategorias() {
        log.debug("REST request to get all Categorias");
        return ResponseEntity.ok().body(categoriaService.findAll());
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable String id) {
        log.debug("REST request to get Categoria : {}", id);
        Optional<Categoria> categoria = categoriaService.findOne(id);
        if (categoria.isPresent()) return ResponseEntity.ok().body(categoria.get());
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Categoria id %d inexists", id))).build();
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable String id) {
        log.debug("REST request to delete Categoria : {}", id);
        Optional<Categoria> categoria = categoriaService.findOne(id);
        if (categoria.isPresent()) {
            categoriaService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
        }
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Categoria id %d inexists", id))).build();
    }

}
