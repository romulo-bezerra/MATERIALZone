package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.UsuarioService;
import br.edu.ifpb.tccii.materialzone.domain.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioResource {

    private UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok().body(usuarioService.save(usuario));
    }

//    @GetMapping("/materiais/{id}")
//    @ApiOperation(value = "Recupera um material pelo ID")
//    public ResponseEntity<Material> getMaterial(@PathVariable String id) {
//        return ResponseEntity.ok().body(materialService.findOne(id));
//    }

//    @GetMapping("/materiais/fulltextsearch/{text}")
//    @ApiOperation(value = "Recupera uma lista de materiais (Iterable<Material>) por texto")
//    public ResponseEntity<Iterable<Material>> getMaterialByText(@PathVariable String text) {
//        return ResponseEntity.ok().body(materialService.findByText(text));
//    }

    @GetMapping("/usuarios")
    public ResponseEntity<Iterable<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

//    @GetMapping("/materiais/bycategoria/{idCategoria}")
//    @ApiOperation(value = "Recupera uma lista de materiais (Iterable<Material>) por categoria")
//    public ResponseEntity<Iterable<Material>> getMateriaisByCategoria(@PathVariable String idCategoria) {
//        return ResponseEntity.ok().body(materialService.getMateriaisByCategoria(idCategoria));
//    }

//    @DeleteMapping("/materiais/todos")
//    @ApiOperation(value = "Deleta todos os materiais")
//    public ResponseEntity<Void> deleteAll() {
//        materialService.deleteAll();
//        return ResponseEntity.ok().build();
//    }

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

}
