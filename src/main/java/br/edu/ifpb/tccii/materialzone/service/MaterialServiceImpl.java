package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.CategoriaService;
import br.edu.ifpb.tccii.materialzone.abstration.GitRepositoryContentExtractor;
import br.edu.ifpb.tccii.materialzone.abstration.MaterialService;
import br.edu.ifpb.tccii.materialzone.domain.Categoria;
import br.edu.ifpb.tccii.materialzone.domain.Material;
import br.edu.ifpb.tccii.materialzone.repository.MaterialRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired private MaterialRepository materialRepository;
    @Autowired private GitRepositoryContentExtractor gitRepositoryContentExtractor;
    @Autowired private CategoriaService categoriaService;

    public MaterialServiceImpl() { }

    @Override
    public Material save(Material material) {
        //Extraindo conteúdo
        String linkRepositorio = material.getLinkRepositorio();
        List<String> arquivosExtraidos = gitRepositoryContentExtractor.extractContentRepository(linkRepositorio);
        material.setArquivosRepositorio(arquivosExtraidos);
        material.setTimestampCriacao(ZonedDateTime.now(ZoneId.systemDefault())); //setting atual time);

        log.debug("Request to save Material : {}", material);
        return materialRepository.save(material);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Material> findAll() {
        log.debug("Request to get all Materiais");
        return materialRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Material> findAllMaterialsByTitleOrDescription(String text) {
        log.debug("Request to get all Materiais");
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.queryStringQuery(text)
                        .lenient(true)
                        .field("titulo")
                        .field("descricao"))
                .should(QueryBuilders.queryStringQuery("*" + text + "*")
                        .lenient(true)
                        .field("titulo")
                        .field("descricao"));

        return materialRepository.search(query);
    }

    @Override
    public Material addCategory(String materialId, String categoriaId) {
        Optional<Categoria> categoriaOptional = categoriaService.findOne(categoriaId);
        Optional<Material> materialOptional = materialRepository.findById(materialId);
        if (materialOptional.isPresent()){
            Material material = materialOptional.get();
            if (categoriaOptional.isPresent()){
                if (existsByIdAndAndCategoriasIds(materialId, categoriaId) == null){
                    material.addCategoria(categoriaId);
                }
            }
            return materialRepository.save(material);
        }
        return new Material();
    }

    @Override
    public Iterable<Material> findMaterialsByCategoriasIds(String categoriaId) {
        return materialRepository.findMaterialsByCategoriasIds(categoriaId);
    }

    private Material existsByIdAndAndCategoriasIds(String materialId, String categoriaId) {
        return materialRepository.existsByIdAndAndCategoriasIds(materialId, categoriaId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Material> findOne(String id) {
        log.debug("Request to get Material : {}", id);
        return materialRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Material : {}", id);
        materialRepository.deleteById(id);
    }

}
