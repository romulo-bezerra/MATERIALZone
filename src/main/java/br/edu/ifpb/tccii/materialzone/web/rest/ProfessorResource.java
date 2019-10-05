package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.ProfessorService;
import br.edu.ifpb.tccii.materialzone.domain.Professor;
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
@RequestMapping("/api/professor")
@Api(value = "ProfessorResource Controller", description = "Serviços pertinentes à professores")
public class ProfessorResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ENTITY_NAME = "Professor";

    @Autowired private ProfessorService professorService;

    public ProfessorResource() { }

    @PostMapping("")
    @ApiOperation(value = "Cria um novo professor")
    public ResponseEntity<Professor> createProfessor(@Valid @RequestBody Professor professor) throws URISyntaxException {
        log.debug("REST request to save Professor : {}", professor);
        Professor result = professorService.save(professor);
        return ResponseEntity.created(new URI("/api/professor/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    @PutMapping("")
    @ApiOperation(value = "Atualiza os dados de professor")
    public ResponseEntity<Professor> updateProfessor(@Valid @RequestBody Professor professor) {
        log.debug("REST request to update Professor : {}", professor);
        if (professor.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        Professor result = professorService.save(professor);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, professor.getId())).body(result);
    }

    @GetMapping("")
    @ApiOperation(value = "Recupera todos os professores")
    public ResponseEntity<List<Professor>> getAllProfessores(@RequestParam("pag") int pag) {
        log.debug("REST request to get all Professores");
        PageRequest pageRequest = PageRequest.of(pag, 10);
        Page<Professor> professoresPag = professorService.findAll(pageRequest);
        return ResponseEntity.ok().body(professoresPag.getContent());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Recupera um professor dado o ID")
    public ResponseEntity<Professor> getProfessor(@PathVariable String id) {
        log.debug("REST request to get Professor : {}", id);
        Optional<Professor> professor = professorService.findOne(id);
        if (professor.isPresent()) return ResponseEntity.ok().body(professor.get());
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Professor id %d inexists", id))).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um professor dado o ID")
    public ResponseEntity<Void> deleteProfessor(@PathVariable String id) {
        log.debug("REST request to delete Professor : {}", id);
        Optional<Professor> professor = professorService.findOne(id);
        if (professor.isPresent()) {
            professorService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
        }
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Professor id %d inexists", id))).build();
    }

}
