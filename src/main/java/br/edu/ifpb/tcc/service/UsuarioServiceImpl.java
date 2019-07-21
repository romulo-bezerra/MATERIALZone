package br.edu.ifpb.tcc.service;

import br.edu.ifpb.tcc.abstration.UsuarioService;
import br.edu.ifpb.tcc.domain.Categoria;
import br.edu.ifpb.tcc.domain.Material;
import br.edu.ifpb.tcc.domain.Usuario;
import br.edu.ifpb.tcc.repository.CategoriaRepository;
import br.edu.ifpb.tcc.repository.MaterialRepository;
import br.edu.ifpb.tcc.repository.UsuarioRepository;
import br.edu.ifpb.tcc.util.FileUtil;
import br.edu.ifpb.tcc.util.GitUtil;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario save(Usuario m) {
        return usuarioRepository.save(m);
    }

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

    /**
     * Busca uma lista de materiais dada as palavras relacionadas de uma categoria
     *
//     * @param palavrasRelacionadas, lista de palavras da categoria em questão
     * @return Iterable<Material>, lista de iterables de materiais
     */
//    private Iterable<Material> findByPalavrasRelacionadas(List<String> palavrasRelacionadas) {
//        QueryBuilder queryBuilder = new MatchQueryBuilder("linhasArquivoRepo", palavrasRelacionadas);
//        return materialRepository.search(queryBuilder);
//    }

//    public Material findOne(String id) {
//        Optional<Material> materialSearch = materialRepository.findById(id);
//        if (materialSearch.isPresent()) return materialSearch.get();
//        return null;
//    }

    public Iterable<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

//    public Iterable<Material> findByText(String text) {
//        QueryBuilder queryBuilder = new MatchQueryBuilder("linhasArquivoRepo", text);
//        return materialRepository.search(queryBuilder);
//    }

//    public void delete(String id){
//        materialRepository.deleteById(id);
//    }

//    public void deleteAll(){
//        materialRepository.deleteAll();
//    }

}
