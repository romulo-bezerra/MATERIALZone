package br.edu.ifpb.tccii.materialzone.web.rest;

import br.edu.ifpb.tccii.materialzone.abstration.CategoriaService;
import br.edu.ifpb.tccii.materialzone.abstration.MaterialService;
import br.edu.ifpb.tccii.materialzone.domain.Categoria;
import br.edu.ifpb.tccii.materialzone.domain.Material;
import br.edu.ifpb.tccii.materialzone.integration.dto.ResultClassifier;
import br.edu.ifpb.tccii.materialzone.integration.service.ClassifierResultService;
import br.edu.ifpb.tccii.materialzone.web.errors.BadRequestAlertException;
import br.edu.ifpb.tccii.materialzone.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MaterialResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ENTITY_NAME = "Material";
    private MaterialService materialService;
    private ClassifierResultService classifierResultService;
    @Autowired private CategoriaService categoriaService;

    public MaterialResource(MaterialService materialService, ClassifierResultService classifierResultService) {
        this.materialService = materialService;
        this.classifierResultService = classifierResultService;
    }

    @PostMapping("/materiais")
    public ResponseEntity<Material> createMaterial(@Valid @RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to save Material : {}", material);
        Material result = materialService.save(material);

        ResultClassifier resultClassifier = classifierResultService.getResultClassification(result.getArquivosRepositorio());
        Categoria bdCategoria = insertBancoDadosCategory(resultClassifier.getBancoDadosRanking());
        Categoria pooCategoria = insertProgramacaoOrientadaObjetoCategory(resultClassifier.getProgramacaoOrientadaObjetoRanking());
        Categoria lmCategoria = insertLinguagemMarcacaoCategory(resultClassifier.getLinguagemMarcacaoRanking());
        Categoria tsCategoria = insertTesteSoftwareCategory(resultClassifier.getTesteSoftwareRanking());
        Categoria lsCategoria = insertLinguagemScriptCategory(resultClassifier.getLinguagemScriptRanking());

        // valor minimo obrigatório para a classificação ser válida
        float minimumTrashold = 0.7F;
        if (bdCategoria.getPontuacaoFinalClassificacao() >= minimumTrashold) material.addCategoria(bdCategoria);
        if (pooCategoria.getPontuacaoFinalClassificacao() >= minimumTrashold) material.addCategoria(pooCategoria);
        if (lmCategoria.getPontuacaoFinalClassificacao() >= minimumTrashold) material.addCategoria(lmCategoria);
        if (tsCategoria.getPontuacaoFinalClassificacao() >= minimumTrashold) material.addCategoria(tsCategoria);
        if (lsCategoria.getPontuacaoFinalClassificacao() >= minimumTrashold) material.addCategoria(lsCategoria);
        materialService.save(result);

        return ResponseEntity.created(new URI("/api/materiais/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

//    private ResultClassifier getRankingCategories(List<String> fileRepositories){
//        ResultClassifier resultClassifier = new ResultClassifier();
//        for (String s : fileRepositories){
//            resultClassifier = classifierResultService.getResultClassification(s);
//        }
//        return resultClassifier;
//    }

//    @PutMapping("/materiais/{materialId}/categoria/{categoriaId}")
//    public ResponseEntity<Material> addCategoria(@PathVariable final String materialId, @PathVariable final String categoriaId) throws URISyntaxException {
//        log.debug("REST request to add categoria to Material : {}");
//        Material result = materialService.addCategory(materialId, categoriaId);
//        if (!result.getId().isEmpty()){
//            return ResponseEntity.created(new URI("/api/materiais/" + result.getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
//        }
//        return ResponseEntity.notFound().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, String.format("Material not exists"))).build();
//    }

//    @GetMapping("/materiais/{materialId}/categoria/{categoriaId}")
//    public Material getMaterialByIdAndCategoriasIdsExists(@PathVariable final String materialId, @PathVariable final String categoriaId){
//        return materialService.existsByIdAndAndCategoriasIds(materialId, categoriaId);
//    }

    @GetMapping("/materiais/categoria/{nome}")
    public ResponseEntity<Iterable<Material>> findMaterialsByNameCategories(@PathVariable final String nome){
        log.debug("REST request to get all Materiais by name category");
        return ResponseEntity.ok().body(materialService.findMaterialsByNameCategories(nome));
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

    @GetMapping("/materiais/textsearch/{text}")
    public ResponseEntity<Iterable<Material>> findAllByTituloOrDescricao(@PathVariable String text) {
        log.debug("REST request to get all Materiais by title or description");
        return ResponseEntity.ok().body(materialService.findAllMaterialsByTitleOrDescription(text));
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

    private Categoria insertBancoDadosCategory(float punctuationRankingCategory){
        final String nameCategory = "Banco de Dados";
        Categoria bdCategoria = new Categoria();
        bdCategoria.setNome(nameCategory);
        bdCategoria.setPontuacaoFinalClassificacao(punctuationRankingCategory);
        return categoriaService.save(bdCategoria);
    }

    private Categoria insertProgramacaoOrientadaObjetoCategory(float punctuationRankingCategory){
        final String nameCategory = "Programação Orientada a Objeto";
        Categoria pooCategoria = new Categoria();
        pooCategoria.setNome(nameCategory);
        pooCategoria.setPontuacaoFinalClassificacao(punctuationRankingCategory);
        return categoriaService.save(pooCategoria);
    }

    private Categoria insertLinguagemMarcacaoCategory(float punctuationRankingCategory){
        final String nameCategory = "Linguagem de Marcação";
        Categoria lmCategoria = new Categoria();
        lmCategoria.setNome(nameCategory);
        lmCategoria.setPontuacaoFinalClassificacao(punctuationRankingCategory);
        return categoriaService.save(lmCategoria);

    }

    private Categoria insertTesteSoftwareCategory(float punctuationRankingCategory){
        final String nameCategory = "Teste de Software";
        Categoria tsCategoria = new Categoria();
        tsCategoria.setNome(nameCategory);
        tsCategoria.setPontuacaoFinalClassificacao(punctuationRankingCategory);
        return categoriaService.save(tsCategoria);
    }

    private Categoria insertLinguagemScriptCategory(float punctuationRankingCategory){
        final String nameCategory = "Linguagem de Script";
        Categoria lsCategoria = new Categoria();
        lsCategoria.setNome(nameCategory);
        lsCategoria.setPontuacaoFinalClassificacao(punctuationRankingCategory);
        return categoriaService.save(lsCategoria);
    }

}
