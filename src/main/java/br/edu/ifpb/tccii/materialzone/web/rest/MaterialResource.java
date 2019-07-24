package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.MaterialService;
import br.edu.ifpb.tccii.materialzone.domain.Material;
import br.edu.ifpb.tccii.materialzone.web.errors.BadRequestAlertException;
import br.edu.ifpb.tccii.materialzone.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.*;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MaterialResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ENTITY_NAME = "Material";

    private MaterialService materialService;

    public MaterialResource(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("/materiais")
    public ResponseEntity<Material> createMaterial(@Valid @RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to save Material : {}", material);
        Material result = materialService.save(material);
        return ResponseEntity.created(new URI("/api/materiais/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    @PutMapping("/materiais")
    public ResponseEntity<Material> updateMaterial(@Valid @RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to update Material : {}", material);
        if (material.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        Material result = materialService.save(material);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, material.getId())).body(result);
    }

    @GetMapping("/materiais")
    public ResponseEntity<Iterable<Material>> getAllMateriais() {
        log.debug("REST request to get all Materiais");
        return ResponseEntity.ok().body(materialService.findAll());
    }

    @GetMapping("/materiais/{id}")
    public ResponseEntity<Material> getMaterial(@PathVariable String id) {
        log.debug("REST request to get Material : {}", id);
        Optional<Material> material = materialService.findOne(id);
        if (material.isPresent()) return ResponseEntity.ok().body(material.get());
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Material id %d inexists", id))).build();
    }

    @DeleteMapping("/materiais/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable String id) {
        log.debug("REST request to delete Material : {}", id);
        Optional<Material> material = materialService.findOne(id);
        if (material.isPresent()) {
            materialService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
        }
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Materias id %d inexists", id))).build();
    }

}
