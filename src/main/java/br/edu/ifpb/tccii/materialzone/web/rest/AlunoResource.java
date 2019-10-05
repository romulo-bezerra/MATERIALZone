package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.AlunoService;
import br.edu.ifpb.tccii.materialzone.domain.Aluno;
import br.edu.ifpb.tccii.materialzone.web.errors.BadRequestAlertException;
import br.edu.ifpb.tccii.materialzone.web.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/aluno")
@Api(value = "AlunoResource Controller", description = "Serviços pertinentes à alunos")
public class AlunoResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ENTITY_NAME = "Aluno";

    @Autowired private AlunoService alunoService;

    public AlunoResource() { }

    @PostMapping("")
    @ApiOperation(value = "Cria um novo aluno")
    public ResponseEntity<Aluno> createAluno(@Valid @RequestBody Aluno aluno) throws URISyntaxException {
        log.debug("REST request to save Aluno : {}", aluno);
        Aluno result = alunoService.save(aluno);
        return ResponseEntity.created(new URI("/api/aluno/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    @PutMapping("")
    @ApiOperation(value = "Atualiza os dados de aluno")
    public ResponseEntity<Aluno> updateAluno(@Valid @RequestBody Aluno aluno) {
        log.debug("REST request to update Aluno : {}", aluno);
        if (aluno.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        Aluno result = alunoService.save(aluno);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, aluno.getId())).body(result);
    }

    @GetMapping("")
    @ApiOperation(value = "Recupera todos os alunos")
    public ResponseEntity<List<Aluno>> getAllAlunos(@RequestParam("pagina") int pagina) {
        log.debug("REST request to get all Alunos");
        PageRequest pageRequest = PageRequest.of(pagina, 10);
        Page<Aluno> alunosPag = alunoService.findAll(pageRequest);
        return ResponseEntity.ok().body(alunosPag.getContent());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Recupera um aluno dado o ID")
    public ResponseEntity<Aluno> getAluno(@PathVariable String id) {
        log.debug("REST request to get Aluno : {}", id);
        Optional<Aluno> aluno = alunoService.findOne(id);
        if (aluno.isPresent()) return ResponseEntity.ok().body(aluno.get());
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Aluno id %d inexists", id))).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um aluno dado o ID")
    public ResponseEntity<Void> deleteAluno(@PathVariable String id) {
        log.debug("REST request to delete Aluno : {}", id);
        Optional<Aluno> aluno = alunoService.findOne(id);
        if (aluno.isPresent()) {
            alunoService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
        }
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Aluno id %d inexists", id))).build();
    }

}
