package br.edu.ifpb.tcc.web.rest;

import br.edu.ifpb.tcc.domain.Categoria;
import br.edu.ifpb.tcc.service.CategoriaServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoriaResource {

    private final CategoriaServiceImpl categoriaServiceImpl;

    public CategoriaResource(CategoriaServiceImpl categoriaServiceImpl) {
        this.categoriaServiceImpl = categoriaServiceImpl;
    }

    @PostMapping("categorias")
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok().body(categoriaServiceImpl.save(categoria));
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> getCategoria(@PathVariable String id) {
        return ResponseEntity.ok().body(categoriaServiceImpl.findOne(id));
    }

    @GetMapping("/categorias/relatedwords/{text}")
    public ResponseEntity<Page<Categoria>> getCategoriaByRelatedWords(@PathVariable String text) {
        return ResponseEntity.ok().body(categoriaServiceImpl.findCategoriasRelated(text));
    }

    @GetMapping("/categorias/todos")
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        return ResponseEntity.ok().body(categoriaServiceImpl.findAll());
    }

    @DeleteMapping("/categorias/todos")
    public ResponseEntity<Iterable<Categoria>> deleteAll() {
        categoriaServiceImpl.deleteAll();
        return ResponseEntity.ok().body(categoriaServiceImpl.findAll());
    }

    @GetMapping("/categorias/palavrasrelacionadas/{text}")
    public ResponseEntity<Iterable<Categoria>> getCategoriasByPalavrasRelacionadas(@PathVariable String text) {
        return ResponseEntity.ok().body(categoriaServiceImpl.getCategoriaByPalavrasRelacionadas(text));
    }

}
