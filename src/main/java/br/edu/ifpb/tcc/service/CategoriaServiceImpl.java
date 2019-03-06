package br.edu.ifpb.tcc.service;

import br.edu.ifpb.tcc.abstration.CategoriaService;
import br.edu.ifpb.tcc.domain.Categoria;
import br.edu.ifpb.tcc.repository.CategoriaRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria save(Categoria c) {
        return categoriaRepository.save(c);
    }

    public Categoria update(Categoria categoria){
        return categoriaRepository.save(categoria);
    }

    public Categoria findOne(String id) {
        Optional<Categoria> categoriaSearch = categoriaRepository.findById(id);
        if (categoriaSearch.isPresent()) return categoriaSearch.get();
        return null;
    }

    public Iterable<Categoria> findByText(String text){
        QueryBuilder searchQuery = new MatchQueryBuilder("palavrasRelacionadas", text);
        return categoriaRepository.search(searchQuery);
    }

    public Iterable<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public void deleteAll(){
        categoriaRepository.deleteAll();
    }

    public void delete(String id){
        categoriaRepository.deleteById(id);
    }

//    public Page<Categoria> findByFullText(StringBuilder stringBuilder){
//        return categoriaRepository.findFirst10ByPalavrasRelacionadas(stringBuilder.toString(), PageRequest.of(0, 10));
//    }

//    public Page<Categoria> findCategoriasRelated(String text){
//        BoolQueryBuilder qb = QueryBuilders.boolQuery();
//        qb.should(QueryBuilders.matchQuery("palavrasRelacionadas", text));
//
//
//        float maxScore = 0;
//        try {
//            maxScore = maximumScoreSearch(qb);
//        } catch (UnknownHostException e) {
//            log.debug(e.getMessage());
//        }
//
//        float minScore = maxScore * 0.4F; //set minScore = 70%
//
//        log.info("\nMax Score: "+maxScore);
//        log.info("Min Score: "+minScore+"\n");
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withMinScore(maxScore)
//                .withIndices("categorias")
//                .withTypes("categoria")
//                .withQuery(qb)
//                .build();
//        return categoriaRepository.search(searchQuery);
//    }

//    private float maximumScoreSearch(BoolQueryBuilder queryBuilder) throws UnknownHostException {
//        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
//        TransportClient client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
//
//        //busca "minúscula" para descobrir o maxScore
//        SearchResponse responseMaxScore = client.prepareSearch("categorias")
//                .setTypes("categoria")
//                .setQuery(queryBuilder)
//                .setSize(1)
//                .execute()
//                .actionGet();
//        return responseMaxScore.getHits().getMaxScore();
//    }

}
