package br.edu.ifpb.tcc.service;

import br.edu.ifpb.tcc.abstration.CategoriaService;
import br.edu.ifpb.tcc.domain.Categoria;
import br.edu.ifpb.tcc.repository.CategoriaRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria save(Categoria c) {
        return categoriaRepository.save(c);
    }

    public Categoria findOne(String id) {
        Optional<Categoria> categoriaSearch = categoriaRepository.findById(id);
        if (categoriaSearch.isPresent()) return categoriaSearch.get();
        return null;
    }

    public List<Categoria> findAll() {
        return toList(categoriaRepository.findAll());
    }

    public void deleteAll(){
        categoriaRepository.deleteAll();
    }

    public Page<Categoria> findByFullText(StringBuilder stringBuilder){
        return categoriaRepository.findFirst10ByPalavrasRelacionadas(stringBuilder.toString(), PageRequest.of(0, 10));
    }

    public Page<Categoria> findCategoriasRelated(String text){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.should(QueryBuilders.matchQuery("palavrasRelacionadas", text));


        float maxScore = 0;
        try {
            maxScore = maximumScoreSearch(qb);
        } catch (UnknownHostException e) {
            log.debug(e.getMessage());
        }

        float minScore = maxScore * 0.4F; //set minScore = 70%

        log.info("\nMax Score: "+maxScore);
        log.info("Min Score: "+minScore+"\n");

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withMinScore(maxScore)
                .withIndices("categorias")
                .withTypes("categoria")
                .withQuery(qb)
                .build();
        return categoriaRepository.search(searchQuery);
    }

    public Iterable<Categoria> getCategoriaByPalavrasRelacionadas(String palavrasRelacionadas){
        return categoriaRepository.findFirst10ByPalavrasRelacionadas(palavrasRelacionadas, PageRequest.of(0, 10));
    }

    private float maximumScoreSearch(BoolQueryBuilder queryBuilder) throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        //busca "minúscula" para descobrir o maxScore
        SearchResponse responseMaxScore = client.prepareSearch("categorias")
                .setTypes("categoria")
                .setQuery(queryBuilder)
                .setSize(1)
                .execute()
                .actionGet();
        return responseMaxScore.getHits().getMaxScore();
    }

    private static <Categoria> List<Categoria> toList(final Iterable<Categoria> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Page<Categoria> getCategorias(String text){
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        qb.should(QueryBuilders.matchQuery("palavrasRelacionadas", text));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("categorias")
                .withTypes("categoria")
                .withQuery(qb)
                .build();
        return categoriaRepository.search(searchQuery);
    }

}
