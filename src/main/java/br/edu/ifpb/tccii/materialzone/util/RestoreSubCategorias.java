//package br.edu.ifpb.tccii.materialzone.util;
//
//import br.edu.ifpb.tccii.materialzone.abstration.CategoriaService;
//import br.edu.ifpb.tccii.materialzone.domain.Categoria;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.util.List;
//
//@Service
//public class RestoreSubCategorias {
//
//    private final CategoriaService categoriaService;
//
//    private FileUtil fileUtil;
//
//    public RestoreSubCategorias(CategoriaService categoriaService){
//        this.categoriaService = categoriaService;
//        fileUtil = new FileUtil();
//    }
//
//    public void restoreSubcategoriaAndroid() {
//        File file = new File("src/main/resources/feed-subcategorias/android.txt");
//        List<String> androidPalavrasRelacionadas = fileUtil.readContentFileAsList(file);
//
//        Categoria categoria = new Categoria();
//        categoria.setNome("Programação para Dispositivos Móveis");
//        categoria.setSubcategoria("Android");
//        categoria.setPalavrasRelacionadas(androidPalavrasRelacionadas);
//
//        categoriaService.save(categoria);
//    }
//
//}
