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
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Api(value = "MaterialResource Controller", description = "Serviços pertinentes à materiais")
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
    @ApiOperation(value = "Cria um novo material")
    public ResponseEntity<Material> createMaterial(@Valid @RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to save Material : {}", material);

        Material result = materialService.save(material); //salva para extracao de conteudo

        final String pooCategoryName = "Programação Orientada a Objeto";
        final String lmCategoryName = "Linguagem de Marcação";
        final String bdCategoryName = "Banco de Dados";
        final String lsCategoryName = "Linguagem de Script";

        ResultClassifier resultClassifier = classifierResultService.getResultClassification(result.getArquivosRepositorio());
        final float pooCategoryPunctuationRanking = resultClassifier.getProgramacaoOrientadaObjetoRanking();
        final float lmCategoryPunctuationRanking = resultClassifier.getLinguagemMarcacaoRanking();
        final float bdCategoryPunctuationRanking = resultClassifier.getBancoDadosRanking();
        final float lsCategoryPunctuationRanking = resultClassifier.getLinguagemScriptRanking();

        final float minimumThreshold = 0.7F; //pontuação mínima de consideração de uma categoria

        Categoria pooCategoria = insertCategoryIntoMaterial(pooCategoryName, pooCategoryPunctuationRanking);
        Categoria lmCategoria = insertCategoryIntoMaterial(lmCategoryName, lmCategoryPunctuationRanking);
        Categoria bdCategoria = insertCategoryIntoMaterial(bdCategoryName, bdCategoryPunctuationRanking);
        Categoria lsCategoria = insertCategoryIntoMaterial(lsCategoryName, lsCategoryPunctuationRanking);

        if (pooCategoria.getPontuacaoFinalClassificacao() >= minimumThreshold) material.addCategoria(pooCategoria);
        if (lmCategoria.getPontuacaoFinalClassificacao() >= minimumThreshold) material.addCategoria(lmCategoria);
        if (bdCategoria.getPontuacaoFinalClassificacao() >= minimumThreshold) material.addCategoria(bdCategoria);
        if (lsCategoria.getPontuacaoFinalClassificacao() >= minimumThreshold) material.addCategoria(lsCategoria);

        materialService.save(result); //atualiza a adição de categorias no material

        return ResponseEntity.created(new URI("/api/materiais/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId())).body(result);
    }

    @PutMapping("/materiais")
    @ApiOperation(value = "Atualiza um material existente")
    public ResponseEntity<Material> updateMaterial(@Valid @RequestBody Material material) throws URISyntaxException {
        log.debug("REST request to update Material : {}", material);
        if (material.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        Material result = materialService.save(material);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, material.getId())).body(result);
    }

    @DeleteMapping("/materiais/{id}")
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

    @GetMapping("/materiais")
    @ApiOperation(value = "Recupera todos os materias")
    public ResponseEntity<List<Material>> findAllWithPagination(@RequestParam("pag") int pag) {
        log.debug("REST request to get all Materiais");
        PageRequest pageRequest = PageRequest.of(pag, 10);
        Page<Material> materialsPag = materialService.findAll(pageRequest);
        return ResponseEntity.ok().body(materialsPag.getContent());
    }

    @GetMapping("/materiais/categoria/{nome}")
    @ApiOperation(value = "Recupera todos os materiais dado o nome da categoria")
    public ResponseEntity<List<Material>> findMaterialsByNameCategories(@PathVariable final String nome, @RequestParam("pag") int pag){
        log.debug("REST request to get all Materiais by name category");
        PageRequest pageRequest = PageRequest.of(pag, 10);
        String nomeProcessed = removerAcentos(nome);
        Page<Material> materialsPag = materialService.findMaterialsByNameCategories(nomeProcessed, pageRequest);
        return ResponseEntity.ok().body(materialsPag.getContent());
    }

    @GetMapping("/materiais/textsearch/{pieceTitleOrDescription}")
    @ApiOperation(value = "Recupera todos os materias dado o parte do título ou descrição")
    public ResponseEntity<List<Material>> findAllByTituloOrDescricao(@PathVariable String pieceTitleOrDescription, @RequestParam("pag") int pag) {
        log.debug("REST request to get all Materiais by title or description");
        PageRequest pageRequest = PageRequest.of(pag, 10);
        Page<Material> materialsPag = materialService.findAllMaterialsByTitleOrDescription(pieceTitleOrDescription, pageRequest);
        return ResponseEntity.ok().body(materialsPag.getContent());
    }

    @GetMapping("/materiais/{id}")
    @ApiOperation(value = "Recupera um material dado seu ID")
    public ResponseEntity<Material> getMaterial(@PathVariable String id) {
        log.debug("REST request to get Material : {}", id);
        Optional<Material> material = materialService.findOne(id);
        if (material.isPresent()) return ResponseEntity.ok().body(material.get());
        return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, id, String.format("Material id %d inexists", id))).build();
    }

    final private Categoria insertCategoryIntoMaterial(String nameCategory, float punctuationRankingCategory){
        Categoria categoria = null;
        categoria = new Categoria();
        categoria.setNome(nameCategory);
        categoria.setPontuacaoFinalClassificacao(punctuationRankingCategory);
        return categoriaService.save(categoria);
    }

    final private String removerAcentos(String valorAcentuado){
        return Normalizer.normalize(valorAcentuado, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}
