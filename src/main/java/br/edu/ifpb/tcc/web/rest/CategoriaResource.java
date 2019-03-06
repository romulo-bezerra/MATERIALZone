package br.edu.ifpb.tcc.web.rest;

import br.edu.ifpb.tcc.abstration.CategoriaService;
import br.edu.ifpb.tcc.domain.Categoria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Api(value = "CategoriaResource Controller", description = "Serviços pertinentes às categorias de materiais")
public class CategoriaResource {

    private final CategoriaService categoriaService;

    public CategoriaResource(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping("categorias")
    @ApiOperation(value = "Cria uma nova categoria ou atualiza se existente")
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok().body(categoriaService.save(categoria));
    }

    @GetMapping("/categorias/{id}")
    @ApiOperation(value = "Recupera uma categoria pelo ID")
    public ResponseEntity<Categoria> getCategoria(@PathVariable String id) {
        return ResponseEntity.ok().body(categoriaService.findOne(id));
    }

    @GetMapping("/categorias/fulltextsearch/{text}")
    @ApiOperation(value = "Recupera uma lista de categorias (Iterable<Categoria>) por texto")
    public ResponseEntity<Iterable<Categoria>> getCategoriaByTextoRelacionado(@PathVariable String text) {
        return ResponseEntity.ok().body(categoriaService.findByText(text));
    }

    @GetMapping("/categorias/todas")
    @ApiOperation(value = "Recupera todas as categorias (Iterable<Categoria>)")
    public ResponseEntity<Iterable<Categoria>> getAllCategorias() {
        return ResponseEntity.ok().body(categoriaService.findAll());
    }

    @DeleteMapping("/categorias/todas")
    @ApiOperation(value = "Deleta todas as categorias")
    public ResponseEntity<Void> deleteAll() {
        categoriaService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/categorias/{id}")
    @ApiOperation(value = "Deleta uma categoria pelo ID")
    public ResponseEntity<Void> deleteCategoria(@PathVariable String id) {
        Categoria categoria = categoriaService.findOne(id);
        if (categoria != null) {
            categoriaService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", String.format("Categoria de id %d não existe", id));
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

}
