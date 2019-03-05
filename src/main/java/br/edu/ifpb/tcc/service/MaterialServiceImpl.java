package br.edu.ifpb.tcc.service;

import br.edu.ifpb.tcc.abstration.MaterialService;
import br.edu.ifpb.tcc.domain.Categoria;
import br.edu.ifpb.tcc.domain.Material;
import br.edu.ifpb.tcc.repository.CategoriaRepository;
import br.edu.ifpb.tcc.repository.MaterialRepository;
import br.edu.ifpb.tcc.util.FileUtil;
import br.edu.ifpb.tcc.util.GitUtil;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;
    private final CategoriaRepository categoriaRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository, CategoriaRepository categoriaRepository) {
        this.materialRepository = materialRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Material save(Material m) {
        GitUtil gitUtil = new GitUtil(m.getLinkRepositorio());
        FileUtil fileUtil = new FileUtil();
        File clonedFile = gitUtil.doClone();
        File[] filesRepo = fileUtil.getFiles(clonedFile);

        List<String> aggregateLinesFileRepo = new ArrayList<>();
        for (File scannedFile : filesRepo){
            List<String> l = fileUtil.readContentFileAsList(scannedFile);

            System.out.println("\n\n\nArquivo lido:\n\n\n" + l.toString());


        }
//        m.setLinhasArquivoRepo(aggregateLinesFileRepo);
        return materialRepository.save(m);
    }

    public Iterable<Material> getMaterialByRelatedWordsCategoria(){
        String id = "qdfPSmkBXeEsJL7uHSPh";
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        Categoria categoria = new Categoria();
        if (categoriaOptional.isPresent()){
            categoria = categoriaOptional.get();
        }
        return findByTextTest(categoria.getPalavrasRelacionadas());
    }

    public Iterable<Material> findByTextTest(List<String> palavrasRelacionadas) {
        QueryBuilder queryBuilder = new MatchQueryBuilder("linhasArquivoRepo", palavrasRelacionadas);
        return materialRepository.search(queryBuilder);
    }

    public Material findOne(String id) {
        Optional<Material> materialSearch = materialRepository.findById(id);
        if (materialSearch.isPresent()) return materialSearch.get();
        return null;
    }

    public Iterable<Material> findAll() {
        return materialRepository.findAll();
    }

    public Iterable<Material> findByText(String text) {
        QueryBuilder queryBuilder = new MatchQueryBuilder("linhasArquivoRepo", text);
        return materialRepository.search(queryBuilder);
    }

    public void deleteAll(){
        materialRepository.deleteAll();
    }

    private static <Categoria> List<Categoria> toList(final Iterable<Categoria> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

}
