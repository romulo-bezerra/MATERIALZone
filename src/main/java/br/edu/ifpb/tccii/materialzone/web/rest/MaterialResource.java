//package br.edu.ifpb.tccii.materialzone.web.rest;
//
//import br.edu.ifpb.tccii.materialzone.abstration.MaterialService;
//import br.edu.ifpb.tccii.materialzone.domain.Material;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//@Api(value = "MaterialResource Controller", description = "Serviços pertinentes à materiais")
//public class MaterialResource {
//
//    private final MaterialService materialService;
//
//    public MaterialResource(MaterialService materialService) {
//        this.materialService = materialService;
//    }
//
//    @PostMapping("materiais")
//    @ApiOperation(value = "Cria um novo material ou atualiza se existente")
//    public ResponseEntity<Material> createMaterial(@RequestBody Material material) {
//        return ResponseEntity.ok().body(materialService.save(material));
//    }
//
//    @GetMapping("/materiais/{id}")
//    @ApiOperation(value = "Recupera um material pelo ID")
//    public ResponseEntity<Material> getMaterial(@PathVariable String id) {
//        return ResponseEntity.ok().body(materialService.findOne(id));
//    }
//
//    @GetMapping("/materiais/fulltextsearch/{text}")
//    @ApiOperation(value = "Recupera uma lista de materiais (Iterable<Material>) por texto")
//    public ResponseEntity<Iterable<Material>> getMaterialByText(@PathVariable String text) {
//        return ResponseEntity.ok().body(materialService.findByText(text));
//    }
//
//    @GetMapping("/materiais/todos")
//    @ApiOperation(value = "Recupera todos os materiais")
//    public ResponseEntity<Iterable<Material>> getAllMateriais() {
//        return ResponseEntity.ok().body(materialService.findAll());
//    }
//
//    @GetMapping("/materiais/bycategoria/{idCategoria}")
//    @ApiOperation(value = "Recupera uma lista de materiais (Iterable<Material>) por categoria")
//    public ResponseEntity<Iterable<Material>> getMateriaisByCategoria(@PathVariable String idCategoria) {
//        return ResponseEntity.ok().body(materialService.getMateriaisByCategoria(idCategoria));
//    }
//
//    @DeleteMapping("/materiais/todos")
//    @ApiOperation(value = "Deleta todos os materiais")
//    public ResponseEntity<Void> deleteAll() {
//        materialService.deleteAll();
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/materiais/{id}")
//    @ApiOperation(value = "Recupera um materiai pelo ID")
//    public ResponseEntity<Void> deleteMaterial(@PathVariable String id) {
//        Material material = materialService.findOne(id);
//        if (material != null) {
//            materialService.delete(id);
//            return ResponseEntity.ok().build();
//        } else {
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Message", String.format("Material de id %d não existe", id));
//            return ResponseEntity.notFound().headers(headers).build();
//        }
//    }
//
//}
