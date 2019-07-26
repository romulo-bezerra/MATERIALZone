package br.edu.ifpb.tccii.materialzone.service;

import br.edu.ifpb.tccii.materialzone.abstration.GitRepositoryContentExtractor;
import br.edu.ifpb.tccii.materialzone.abstration.MaterialService;
import br.edu.ifpb.tccii.materialzone.domain.Material;
import br.edu.ifpb.tccii.materialzone.repository.MaterialRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final MaterialRepository materialRepository;
    private final GitRepositoryContentExtractor gitRepositoryContentExtractor;

    public MaterialServiceImpl(MaterialRepository materialRepository, GitRepositoryContentExtractor gitRepositoryContentExtractor) {
        this.materialRepository = materialRepository;
        this.gitRepositoryContentExtractor = gitRepositoryContentExtractor;
    }

    @Override
    public Material save(Material material) {
        //Extraindo conteúdo
        String linkRepositorio = material.getLinkRepositorio();
        List<String> arquivosExtraidos = gitRepositoryContentExtractor.extractContentRepository(linkRepositorio);
        material.setArquivosRepositorio(arquivosExtraidos);

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
