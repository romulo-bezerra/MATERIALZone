package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.CategoriaService;
import br.edu.ifpb.tccii.materialzone.abstration.MaterialService;
import br.edu.ifpb.tccii.materialzone.domain.Categoria;
import br.edu.ifpb.tccii.materialzone.domain.Material;
import br.edu.ifpb.tccii.materialzone.integration.dto.ResultClassifier;
import br.edu.ifpb.tccii.materialzone.integration.service.ClassifierResultService;
import br.edu.ifpb.tccii.materialzone.web.errors.BadRequestAlertException;
import br.edu.ifpb.tccii.materialzone.web.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.eclipse.jgit.api.errors.GitAPIException;
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
@RequestMapping("/api/material")
@Api(value = "MaterialResource Controller", description = "Serviços pertinentes à materiais")
public class MaterialResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ENTITY_NAME = "Material";
    @Autowired private MaterialService materialService;
    @Autowired private CategoriaService categoriaService;
    private ClassifierResultService classifierResultService;

    public MaterialResource(ClassifierResultService classifierResultService) {
        this.classifierResultService = classifierResultService;
    }

    @PostMapping("")
    @ApiOperation(value = "Cria um novo material")
    public ResponseEntity<Material> createMaterial(@Valid @RequestBody Material material) throws URISyntaxException, GitAPIException {
        log.debug("REST request to save Material : {}", material);

        Material result = materialService.save(material); //salva para extracao de conteudo

        final String pooCategoryName = "Programação Orientada a Objeto";
        final String lmCategoryName = "Linguagem de Marcação";
        final String bdCategoryName = "Banco de Dados";
        final String lsCategoryName = "Linguagem de Script";

        List<String> arquivosRepo = result.getArquivosRepositorio();

        ResultClassifier resultClassifier = classifierResultService.getResultClassification(arquivosRepo);
        final float pooCategoryPunctuationRanking = resultClassifier.getProgramacaoOrientadaObjetoRanking();
        final float lmCategoryPunctuationRanking = resultClassifier.getLinguagemMarcacaoRanking();
        final float bdCategoryPunctuationRanking = resultClassifier.getBancoDadosRanking();
        final float lsCategoryPunctuationRanking = resultClassifier.getLinguagemScriptRanking();

        final float minimumThreshold = 0.7F; //pontuação mínima de consideração de uma categoria

        Categoria pooCategoria = insertCategoryIntoMaterial(pooCategoryName, result.getId(), pooCategoryPunctuationRanking);
        Categoria lmCategoria = insertCategoryIntoMaterial(lmCategoryName, result.getId(), lmCategoryPunctuationRanking);
        Categoria bdCategoria = insertCategoryIntoMaterial(bdCategoryName, result.getId(), bdCategoryPunctuationRanking);
        Categoria lsCategoria = insertCategoryIntoMaterial(lsCategoryName, result.getId(), lsCategoryPunctuationRanking);

        if (pooCategoria.getPontuacaoFinalClassificacao() >= minimumThreshold) material.addCategoria(pooCategoria);
        if (lmCategoria.getPontuacaoFinalClassificacao() >= minimumThreshold) material.addCategoria(lmCategoria);
        if (bdCategoria.getPontuacaoFinalClassificacao() >= minimumThreshold) material.addCategoria(bdCategoria);
        if (lsCategoria.getPontuacaoFinalClassificacao() >= minimumThreshold) material.addCategoria(lsCategoria);

        materialService.save(result); //atualiza a adição de categorias no material

        return ResponseEntity.created(new URI("/api/materiais/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    @PutMapping("")
    @ApiOperation(value = "Atualiza um material existente")
    public ResponseEntity<Material> updateMaterial(@Valid @RequestBody Material material) throws URISyntaxException, GitAPIException {
        log.debug("REST request to update Material : {}", material);
        if (material.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        Material result = materialService.save(material);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, material.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um material dado seu ID")
    public ResponseEntity<Void> deleteMaterial(@PathVariable String id) {
        log.debug("REST request to delete Material : {}", id);
        Optional<Material> material = materialService.findOne(id);
        if (material.isPresent()) {
            materialService.delete(id);
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
        }
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Materias id %d inexists", id))).build();
    }

    @GetMapping("")
    @ApiOperation(value = "Recupera todos os materias")
    public ResponseEntity<List<Material>> findAllWithPagination(@RequestParam("pagina") int pagina) {
        log.debug("REST request to get all Materiais");
        PageRequest pageRequest = PageRequest.of(pagina, 10);
        Page<Material> materialsPag = materialService.findAll(pageRequest);
        return ResponseEntity.ok().body(materialsPag.getContent());
    }

    @GetMapping("/{email}")
    @ApiOperation(value = "Recupera todos os materias por um email de professor")
    public ResponseEntity<List<Material>> findAllByEmailProfessorWithPagination(@PathVariable final String email, @RequestParam("pagina") int pagina) {
        log.debug("REST request to get all Materiais of Professor by email");
        PageRequest pageRequest = PageRequest.of(pagina, 10);
        Page<Material> materialsPag = materialService.findByEmailProfessor(email, pageRequest);
        return ResponseEntity.ok().body(materialsPag.getContent());
    }

    @GetMapping("/categoria/{nome}")
    @ApiOperation(value = "Recupera todos os materiais dado o nome da categoria")
    public ResponseEntity<List<Material>> findMaterialsByNameCategoriesWithPagination(@PathVariable final String nome, @RequestParam("pagina") int pagina){
        log.debug("REST request to get all Materiais by name category");
        PageRequest pageRequest = PageRequest.of(pagina, 10);
        List<Material> materials = materialService.findMaterialsByNameCategories(nome, pageRequest);
        return ResponseEntity.ok().body(materials);
    }

    @GetMapping("/textsearch/{titleOrDescription}")
    @ApiOperation(value = "Recupera todos os materias dado o parte do título ou descrição")
    public ResponseEntity<List<Material>> findAllByTitleOrDescriptionWithPagination(@PathVariable String titleOrDescription, @RequestParam("pagina") int pagina) {
        log.debug("REST request to get all Materiais by title or description");
        PageRequest pageRequest = PageRequest.of(pagina, 10);
        Page<Material> materialsPag = materialService.findAllMaterialsByTitleOrDescription(titleOrDescription, pageRequest);
        return ResponseEntity.ok().body(materialsPag.getContent());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Recupera um material dado seu ID")
    public ResponseEntity<Material> getMaterial(@PathVariable String id) {
        log.debug("REST request to get Material : {}", id);
        Optional<Material> material = materialService.findOne(id);
        if (material.isPresent()) return ResponseEntity.ok().body(material.get());
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Material id %d inexists", id))).build();
    }

    final private Categoria insertCategoryIntoMaterial(String nameCategory, String materialId, float punctuationRankingCategory){
        final float minimumThreshold = 0.7F;
        Categoria categoria = new Categoria();
        if (punctuationRankingCategory >= minimumThreshold) {
            categoria.setMaterialId(materialId);
            categoria.setNome(nameCategory);
            categoria.setPontuacaoFinalClassificacao(punctuationRankingCategory);
            return categoriaService.save(categoria);
        }
        return categoria;
    }

//    final private List<String> removeKeySlices(List<String> arquivosRepo) {
//        List<String> result = new ArrayList<>();
//        String keySlice = "#-?_keySlice?_-#";
//        for (String s : arquivosRepo) {
//            result.add(s.replace(keySlice, ""));
//        }
//        return result;
//    }

}
