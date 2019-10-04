package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.GitRepositoryContentExtractor;
import br.edu.ifpb.tccii.materialzone.abstration.MaterialService;
import br.edu.ifpb.tccii.materialzone.domain.Material;
import br.edu.ifpb.tccii.materialzone.repository.MaterialRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Material> findAllMaterialsByTitleOrDescription(String text, Pageable pageable) {
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

        return materialRepository.search(query, pageable);
    }

    @Override
    public Page<Material> findMaterialsByNameCategories(String nameCategory, Pageable pageable) {
        log.debug("Request to get all Materiais by category");
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.queryStringQuery(nameCategory)
                        .lenient(true)
                        .field("categorias.nome"))
                .should(QueryBuilders.queryStringQuery("*" + nameCategory + "*")
                        .lenient(true)
                        .field("categorias.nome"));

        return materialRepository.search(query, pageable);
    }

    @Override
    public Page<Material> findAll(Pageable pageable) {
        return materialRepository.findAll(pageable);
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
