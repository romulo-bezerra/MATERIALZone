//package br.edu.ifpb.tccii.materialzone.service;
//
//import br.edu.ifpb.tccii.materialzone.abstration.MaterialService;
//import br.edu.ifpb.tccii.materialzone.domain.Categoria;
//import br.edu.ifpb.tccii.materialzone.domain.Material;
//import br.edu.ifpb.tccii.materialzone.repository.CategoriaRepository;
//import br.edu.ifpb.tccii.materialzone.repository.MaterialRepository;
//import br.edu.ifpb.tccii.materialzone.util.FileUtil;
//import br.edu.ifpb.tccii.materialzone.util.GitUtil;
//import org.elasticsearch.index.query.MatchQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.util.*;
//
//@Service
//public class MaterialServiceImpl implements MaterialService {
//
//    private final MaterialRepository materialRepository;
//    private final CategoriaRepository categoriaRepository;
//
//    public MaterialServiceImpl(MaterialRepository materialRepository, CategoriaRepository categoriaRepository) {
//        this.materialRepository = materialRepository;
//        this.categoriaRepository = categoriaRepository;
//    }
//
//    public Material save(Material m) {
//        GitUtil gitUtil = new GitUtil(m.getLinkRepositorio());
//        FileUtil fileUtil = new FileUtil();
//
//        File clonedRepo = gitUtil.doClone();
//
//        List<String> aggregateLines = new ArrayList<>();
//        for (File scannedFile : fileUtil.getFiles(clonedRepo)){
//            for (String scannedLine : fileUtil.readContentFileAsList(scannedFile)){
//                aggregateLines.add(scannedLine);
//            }
//        }
//        m.setLinhasArquivoRepo(aggregateLines);
//
//        gitUtil.deleteDir(clonedRepo);
//
//        Material material = materialRepository.save(m);
//
//        return material;
//    }
//
//    public Material update(Material material){
//        return materialRepository.save(material);
//    }
//
//    public Iterable<Material> getMateriaisByCategoria(String idCategoria){
//        Optional<Categoria> categoriaOptional = categoriaRepository.findById(idCategoria);
//        Categoria categoria = new Categoria();
//        if (categoriaOptional.isPresent()){
//            categoria = categoriaOptional.get();
//        }
//        return findByPalavrasRelacionadas(categoria.getPalavrasRelacionadas());
//    }
//
//    public Set<String> getCategoriasByMaterial(Material material) {
//        Set<String> retorno = new HashSet<>();
//        Iterable<Categoria> categorias = categoriaRepository.findAll();
//
//        Iterable<Material> materiais;
//        for (Categoria categoria : categorias){
//            materiais = findByPalavrasRelacionadas(categoria.getPalavrasRelacionadas());
//            for (Material m : materiais){
//                if (m.getId().equals(material.getId())){
//                    retorno.add(categoria.getSubcategoria());
//                    break;
//                }
//            }
//        }
//        return retorno;
//    }
//
//    /**
//     * Busca uma lista de materiais dada as palavras relacionadas de uma categoria
//     *
//     * @param palavrasRelacionadas, lista de palavras da categoria em questão
//     * @return Iterable<Material>, lista de iterables de materiais
//     */
//    private Iterable<Material> findByPalavrasRelacionadas(List<String> palavrasRelacionadas) {
//        QueryBuilder queryBuilder = new MatchQueryBuilder("linhasArquivoRepo", palavrasRelacionadas);
//        return materialRepository.search(queryBuilder);
//    }
//
//    public Material findOne(String id) {
//        Optional<Material> materialSearch = materialRepository.findById(id);
//        if (materialSearch.isPresent()) return materialSearch.get();
//        return null;
//    }
//
//    public Iterable<Material> findAll() {
//        return materialRepository.findAll();
//    }
//
//    public Iterable<Material> findByText(String text) {
//        QueryBuilder queryBuilder = new MatchQueryBuilder("linhasArquivoRepo", text);
//        return materialRepository.search(queryBuilder);
//    }
//
//    public void delete(String id){
//        materialRepository.deleteById(id);
//    }
//
//    public void deleteAll(){
//        materialRepository.deleteAll();
//    }
//
//}
