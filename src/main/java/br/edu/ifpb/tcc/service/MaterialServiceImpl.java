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

import javax.validation.constraints.Max;
import java.io.File;
import java.util.*;
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

        List<String> aggregateLines = new ArrayList<>();
        for (File scannedFile : fileUtil.getFiles(gitUtil.doClone())){
            for (String scannedLine : fileUtil.readContentFileAsList(scannedFile)){
                aggregateLines.add(scannedLine);
            }
        }
        m.setLinhasArquivoRepo(aggregateLines);

        Material material = materialRepository.save(m);

        System.out.println("\n\nCategoria encontrada: \n\n" + getCategoriasByMaterial(material));

        return material;
    }

    public Iterable<Material> getMaterialByRelatedWordsCategoria(){
        String id = "NbbWTmkBv5BUgff6Xyf6";
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

    public Set<String> getCategoriasByMaterial(Material material) {
        Set<String> retorno = new HashSet<>();
        Iterable<Categoria> categorias = toList(categoriaRepository.findAll());

        Iterable<Material> materiais;
        for (Categoria categoria : categorias){
            materiais = findByTextTest(categoria.getPalavrasRelacionadas());
            for (Material m : materiais){
                if (m.getId().equals(material.getId())){
                    retorno.add(categoria.getSubcategoria());
                    break;
                }
            }
        }
        return retorno;
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
