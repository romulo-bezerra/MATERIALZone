package br.edu.ifpb.tcc.web.rest;

import br.edu.ifpb.tcc.abstration.MaterialService;
import br.edu.ifpb.tcc.domain.Material;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MaterialResource {

    private final MaterialService materialService;

    public MaterialResource(MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping("material")
    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
        Material material1 = materialService.save(material);
        return ResponseEntity.ok().body(material);
    }

    @GetMapping("/material/{id}")
    public ResponseEntity<Material> getMaterial(@PathVariable String id) {
        return ResponseEntity.ok().body(materialService.findOne(id));
    }

    @GetMapping("/materiais/sourcecode/{text}")
    public ResponseEntity<Iterable<Material>> getMaterialByText(@PathVariable String text) {
        return ResponseEntity.ok().body(materialService.findByText(text));
    }

    @GetMapping("/materiais/todos")
    public ResponseEntity<Iterable<Material>> getAllMateriais() {
        return ResponseEntity.ok().body(materialService.findAll());
    }


    @GetMapping("/test")
    public ResponseEntity<Iterable<Material>> getMateriaisTest() {
        return ResponseEntity.ok().body(materialService.getMaterialByRelatedWordsCategoria());
    }

    @DeleteMapping("/materiais/todos")
    public ResponseEntity<Iterable<Material>> deleteAll() {
        materialService.deleteAll();
        return ResponseEntity.ok().body(materialService.findAll());
    }

}
